package parser;

import org.parboiled.Parboiled;
import org.parboiled.parserunners.BasicParseRunner;
import org.parboiled.parserunners.ParseRunner;
import org.parboiled.support.ParsingResult;

import java.io.*;
import java.util.Optional;

import static org.parboiled.common.FileUtils.readAllChars;

/**
 * Parses a file which uses Paradox's proprietary
 * save-game file format.
 */
public class ParadoxFileParser {

    private ParseRunner<Object> parseRunner;

    public ParadoxFileParser() {
        ParadoxParser paradoxParser = Parboiled.createParser(ParadoxParser.class);
        parseRunner = new BasicParseRunner<>(paradoxParser.SaveGame());
    }

    public Optional<Object> parseFile(File file) {
        return Optional.of(readAllChars(file)).map(input -> {
            ParsingResult<Object> result = parseRunner.run(input);

            if (result.hasErrors()) {
                // TODO: Report these errors.
                return Optional.empty();

            } else {
                // custom marshaller?
                return Optional.of(result.parseTreeRoot.getValue());
            }
        });
    }
}
