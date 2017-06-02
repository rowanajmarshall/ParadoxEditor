package parser;

import org.parboiled.Parboiled;
import org.parboiled.buffers.IndentDedentInputBuffer;
import org.parboiled.buffers.InputBuffer;
import org.parboiled.parserunners.BasicParseRunner;
import org.parboiled.support.ParsingResult;
import representation.Attribute;
import representation.SaveGame;

import java.io.File;
import java.util.List;
import java.util.Optional;

import static org.parboiled.common.FileUtils.readAllChars;

/**
 * Parses a file which uses Paradox's proprietary
 * save-game file format.
 */
public class ParadoxFileParser {

    public static final int FOUR_SPACE_INDENT = 4;
    public static final String NO_COMMENT_START = null;
    public static final boolean STRICT = true;
    public static final boolean SKIP_EMPTY_LINES = true;

    private BasicParseRunner<List<Attribute>> parseRunner;

    public ParadoxFileParser() {
        final AttributeGroupParser paradoxParser = Parboiled.createParser(AttributeGroupParser.class);
        parseRunner = new BasicParseRunner<>(paradoxParser.attributeGroup());
    }

    /**
     * Parses a given file for a save-game.
     *
     * @param file the save-game file.
     * @return an {@link Optional} save-game object if successful, {@link Optional#empty()} if not.
     */
    public Optional<SaveGame> parseFile(File file) {
        final InputBuffer inputBuffer = new IndentDedentInputBuffer(
                readAllChars(file),
                FOUR_SPACE_INDENT,
                NO_COMMENT_START,
                STRICT,
                SKIP_EMPTY_LINES
        );

        return Optional.of(inputBuffer).flatMap(input -> {
            ParsingResult<List<Attribute>> result = parseRunner.run(input);

            if (result.hasErrors()) {
                // TODO: Report these errors.
                return Optional.empty();

            } else {
                // custom marshaller?
                final SaveGame saveGame = new SaveGame(result.resultValue);
                return Optional.of(saveGame);
            }
        });
    }
}
