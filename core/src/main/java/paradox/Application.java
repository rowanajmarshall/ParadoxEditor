package paradox;
import paradox.parser.ParadoxFileParser;
import paradox.ui.ParadoxEditorUI;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Optional;

public class Application {

    public static void main(String[] args) {

        ParadoxEditorUI ui = new ParadoxEditorUI();
        ui.begin();

        if(args.length == 2) {
            File file = new File(args[1]);
            ParadoxFileParser parser = new ParadoxFileParser();
            try {
                Optional gameOp = parser.parseFile(file);
                if(gameOp.isPresent()) {
                    SaveGame game = (SaveGame) gameOp.get();
                    ui.loadSaveGame(game);
                }
            }
            catch (FileNotFoundException e) {
                ui.fileNotFoundError(e.getMessage());
            }
        }
    }
}