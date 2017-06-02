package parser;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.parboiled.Parboiled;
import org.parboiled.buffers.IndentDedentInputBuffer;
import org.parboiled.parserunners.ParseRunner;
import org.parboiled.parserunners.TracingParseRunner;
import org.parboiled.support.ParsingResult;
import representation.Attribute;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.parboiled.errors.ErrorUtils.printParseErrors;

public class AttributeGroupParserTest {

    private ParseRunner<List<Attribute>> parseRunner;

    @Before
    public void setup() {
        AttributeGroupParser paradoxParser = Parboiled.createParser(AttributeGroupParser.class);
        parseRunner = new TracingParseRunner<>(paradoxParser.attributeGroup());
    }

    @Test
    public void shouldBeAbleToParseSimpleStringAttributes() {
        // given
        final String input = "identifier=\"string input\"";

        // when
        final ParsingResult<List<Attribute>> result = parseRunner.run(input);

        // then
        assertNoParseErrors(result);
    }


    @Test
    public void shouldBeAbleToParseSimpleNumericalAttributes() {
        // given
        final String input = "identifier=\"01234\"";

        // when
        final ParsingResult<List<Attribute>> result = parseRunner.run(input);

        // then
        assertNoParseErrors(result);
    }

    @Test
    public void shouldBeAbleToParseObjectAttribute() {
        // given
        final String input = "identifier={\n" +
                "\tsubelem=\"value\"\n" +
                "\totherelem=123\n" +
                "}";

        // when
        final ParsingResult<List<Attribute>> result = parseRunner.run(wrapWithBuffer(input));

        // then
        assertNoParseErrors(result);
    }

    @Test
    @Ignore
    public void shouldFailOnContentWithImproperIndentation() {
        // given
        final String input = "identifier={\n" +
                "subelem=\"value\"\n" +
                "\totherelem=123\n" +
                "}";

        // when
        final ParsingResult<List<Attribute>> result = parseRunner.run(wrapWithBuffer(input));

        // then
    }

    @Test
    public void shouldBeAbleToParseDateAttributes() {
        // given
        final String input = "identifier=1444.11.11";

        // when
        final ParsingResult<List<Attribute>> result = parseRunner.run(input);

        // then
        assertNoParseErrors(result);
    }

    private IndentDedentInputBuffer wrapWithBuffer(String input) {
        return new IndentDedentInputBuffer(input.toCharArray(), 4, null, true, true);
    }

    private void assertNoParseErrors(ParsingResult<List<Attribute>> result) {
        assertFalse(printParseErrors(result), result.hasErrors());
    }

}
