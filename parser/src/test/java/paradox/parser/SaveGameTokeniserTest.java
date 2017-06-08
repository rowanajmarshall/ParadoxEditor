package paradox.parser;

import org.hamcrest.Matchers;
import org.hamcrest.collection.IsMapContaining;
import org.junit.Before;
import org.junit.Test;
import org.parboiled.Parboiled;
import org.parboiled.buffers.IndentDedentInputBuffer;
import org.parboiled.parserunners.ParseRunner;
import org.parboiled.parserunners.TracingParseRunner;
import org.parboiled.support.ParsingResult;
import paradox.representation.Attribute;
import paradox.representation.AttributeLike;
import paradox.representation.ObjectAttribute;
import paradox.representation.StringAttribute;

import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsMapContaining.hasEntry;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.parboiled.errors.ErrorUtils.printParseErrors;

public class SaveGameTokeniserTest {

    private static final String IDENTIFIER = "identifier";
    private ParseRunner<Map<String, Attribute>> parseRunner;

    @Before
    public void setup() {
        final SaveGameTokeniser paradoxParser = Parboiled.createParser(SaveGameTokeniser.class);
        parseRunner = new TracingParseRunner<>(paradoxParser.attributeGroup());
    }

    @Test
    public void shouldBeAbleToParseSimpleStringAttributes() {
        // given
        final String input = IDENTIFIER + "=\"string input\"";

        // when
        final ParsingResult<Map<String, Attribute>> result = parseRunner.run(input);

        // then
        assertNoParseErrors(result);
        assertThat(result.resultValue, hasEntry(is(IDENTIFIER), is(new StringAttribute("string input"))));
    }

    @Test
    public void shouldBeAbleToParseListAttribute() {
        // given
        final String input = IDENTIFIER + "={\n\t1234 5678\n}";

        // when
        final ParsingResult<Map<String, Attribute>> result = parseRunner.run(wrapWithBuffer(input));

        // then
        assertNoParseErrors(result);
        assertThat(result.resultValue, hasEntry(
                is(IDENTIFIER), hasProperty("value", containsInAnyOrder("1234", "5678")))
        );
    }

    @Test
    public void shouldBeAbleToParseInnerObjects() {
        // given
        final String input = IDENTIFIER + "={\n\tkey=\"string\"\n\tother_key=1234\n}";

        // when
        final ParsingResult<Map<String, Attribute>> result = parseRunner.run(wrapWithBuffer(input));

        // then
        assertNoParseErrors(result);
        assertThat(result.resultValue, hasEntry(is(IDENTIFIER), instanceOf(ObjectAttribute.class)));

        final Map<String, Attribute> innerObject = ((AttributeLike<Map<String, Attribute>>) result.resultValue.get(IDENTIFIER)).getValue();
        assertThat(innerObject, allOf(
                hasEntry("key", new StringAttribute("string")),
                hasEntry("other_key", new StringAttribute("1234")))
        );
    }

    private IndentDedentInputBuffer wrapWithBuffer(String input) {
        return new IndentDedentInputBuffer(input.toCharArray(), 4, null, true, true);
    }

    private void assertNoParseErrors(ParsingResult<Map<String, Attribute>> result) {
        assertFalse(printParseErrors(result), result.hasErrors());
    }
}
