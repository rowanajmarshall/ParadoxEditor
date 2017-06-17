package paradox.parser;

import org.parboiled.Parboiled;
import org.parboiled.buffers.IndentDedentInputBuffer;
import org.parboiled.buffers.InputBuffer;
import org.parboiled.parserunners.BasicParseRunner;
import org.parboiled.parserunners.ParseRunner;
import org.parboiled.parserunners.TracingParseRunner;
import org.parboiled.support.ParsingResult;
import paradox.SaveGame;
import paradox.representation.Attribute;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;

import static org.parboiled.common.FileUtils.readAllChars;

/**
 * Parses a file which uses Paradox's proprietary
 * save-game file format.
 */
public class ParadoxFileParser {

    private static final String NO_COMMENT_START = null;
    private static final boolean STRICT = true;
    private static final boolean SKIP_EMPTY_LINES = true;
    private static final int TAB_STOP = 1;

    private ParseRunner<List<Attribute<?>>> parseRunner;

    /**
     * Creates a new parser instance.
     *
     * @apiNote Parboiled documentation says that instantiating a parser is expensive,
     * but that it is expensive only once, subsequent parser instantiations should be
     * less heavy.
     */
    public ParadoxFileParser() {
        final SaveGameTokeniser paradoxParser = Parboiled.createParser(SaveGameTokeniser.class);
        parseRunner = new BasicParseRunner<>(paradoxParser.attributeGroup());
    }

    /**
     * For testing purposes, allows you to specify a flag which dictates the type of parser used.
     * With `displayOutput` on the parser will "show its working".
     *
     * @param displayOutput if logging from the parser should be displayed or not.
     */
    public ParadoxFileParser(final boolean displayOutput) {
        if (displayOutput) {
            final SaveGameTokeniser paradoxParser = Parboiled.createParser(SaveGameTokeniser.class);
            parseRunner = new TracingParseRunner<>(paradoxParser.attributeGroup());

        } else {
            new ParadoxFileParser();
        }
    }

    /**
     * Parses a given file for a save-game. File is assumed to exist. Is a slow process
     * and should be run in a separate thread if necessary.
     *
     * @param file the save-game file.
     * @return an {@link Optional} save-game object if successful, {@link Optional#empty()} if not.
     */
    public Optional<SaveGame> parseFile(final File file) throws FileNotFoundException {
        if (!file.exists()) {
            throw new FileNotFoundException();
        }

        return handleOutput(parseRunner.run(createInputBuffer(file)));
    }

    private Optional<SaveGame> handleOutput(final ParsingResult<List<Attribute<?>>> result) {
        if (result.hasErrors()) {
            return Optional.empty();

        } else {
            return Optional.ofNullable(result.resultValue).map(SaveGame::new);
        }
    }

    private InputBuffer createInputBuffer(final File file) throws FileNotFoundException {
        return new IndentDedentInputBuffer(
                readAllChars(file),
                TAB_STOP,
                NO_COMMENT_START,
                STRICT,
                SKIP_EMPTY_LINES
        );
    }
}
