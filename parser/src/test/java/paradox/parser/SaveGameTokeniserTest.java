package paradox.parser;

import com.google.common.collect.ImmutableList;
import org.junit.Before;
import org.junit.Test;
import org.parboiled.Parboiled;
import org.parboiled.buffers.IndentDedentInputBuffer;
import org.parboiled.parserunners.ParseRunner;
import org.parboiled.parserunners.TracingParseRunner;
import org.parboiled.support.ParsingResult;
import paradox.representation.Attribute;
import paradox.representation.ListAttribute;
import paradox.representation.StringAttribute;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.parboiled.errors.ErrorUtils.printParseErrors;

public class SaveGameTokeniserTest {

    private static final String IDENTIFIER = "identifier";
    private ParseRunner<List<Attribute<?>>> parseRunner;

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
        final ParsingResult<List<Attribute<?>>> result = parseRunner.run(input);

        // then
        assertNoParseErrors(result);
        assertThat(result.resultValue, contains(new StringAttribute(IDENTIFIER, "string input")));
    }

    @Test
    public void shouldBeAbleToParseListAttribute() {
        // given
        final String input = IDENTIFIER + "={\n\t1234 5678\n}";

        // when
        final ParsingResult<List<Attribute<?>>> result = parseRunner.run(wrapWithBuffer(input));

        // then
        assertNoParseErrors(result);
        assertThat(result.resultValue, contains(
                new ListAttribute(IDENTIFIER, ImmutableList.of("1234", "5678"))
        ));
    }

    @Test
    public void shouldBeAbleToParseInnerObjects() {
        // given
        final String input = IDENTIFIER + "={\n\tkey=\"string\"\n\tother_key=1234\n}";

        // when
        final ParsingResult<List<Attribute<?>>> result = parseRunner.run(wrapWithBuffer(input));

        // then
        assertNoParseErrors(result);
        assertThat(
                "Result object was " + result.resultValue.toString() + "\n",
                result.resultValue,
                contains(allOf(
                        hasProperty("identifier", is(IDENTIFIER)),
                        hasProperty("value", containsInAnyOrder(
                                new StringAttribute("key", "string"),
                                new StringAttribute("other_key", "1234")
                        ))))
        );
    }

    private IndentDedentInputBuffer wrapWithBuffer(final String input) {
        return new IndentDedentInputBuffer(input.toCharArray(), 4, null, true, true);
    }

    private void assertNoParseErrors(final ParsingResult<List<Attribute<?>>> result) {
        assertFalse(printParseErrors(result), result.hasErrors());
    }
}
