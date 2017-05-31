package parser;

import org.junit.Before;
import org.junit.Test;
import org.parboiled.Parboiled;
import org.parboiled.buffers.IndentDedentInputBuffer;
import org.parboiled.parserunners.ParseRunner;
import org.parboiled.parserunners.TracingParseRunner;
import org.parboiled.support.ParsingResult;

import static org.junit.Assert.assertFalse;
import static org.parboiled.errors.ErrorUtils.printParseErrors;

public class ParadoxParserTest {

    private ParseRunner<Object> parseRunner;

    @Before
    public void setup() {
        ParadoxParser paradoxParser = Parboiled.createParser(ParadoxParser.class);
        parseRunner = new TracingParseRunner<>(paradoxParser.SaveGame());
    }

    @Test
    public void shouldBeAbleToParseSimpleStringAttributes() {
        // given
        final String input = "identifier=\"string input\"";

        // when
        final ParsingResult<Object> result = parseRunner.run(input);

        // then
        assertNoParseErrors(result);
    }


    @Test
    public void shouldBeAbleToParseSimpleNumericalAttributes() {
        // given
        final String input = "identifier=\"01234\"";

        // when
        final ParsingResult<Object> result = parseRunner.run(input);

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
        final ParsingResult<Object> result = parseRunner.run(wrapWithBuffer(input));

        // then
        assertNoParseErrors(result);
    }

    private IndentDedentInputBuffer wrapWithBuffer(String input) {
        return new IndentDedentInputBuffer(input.toCharArray(), 4, null, true, true);
    }

    private void assertNoParseErrors(ParsingResult<Object> result) {
        assertFalse(printParseErrors(result), result.hasErrors());
    }

}
