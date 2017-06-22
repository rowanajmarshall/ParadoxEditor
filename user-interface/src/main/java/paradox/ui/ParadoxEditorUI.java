package paradox.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import paradox.SaveGame;
import paradox.parser.ParadoxFileParser;

import java.io.FileNotFoundException;

public class FXMLApplication extends Application {

    public void begin() {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("view_res/main.fxml"));
        Scene scene = new Scene(root, 600, 400);

        primaryStage.setTitle("Paradox Editor");
        primaryStage.setScene(scene);

        primaryStage.show();
    }
d
    /**
     * Informs the UI that an error has occurred, and must be shown to the user
     * @param errorMsg
     */
    public void fileNotFoundError(String errorMsg) {
    }

    /**
     * Loads the given SaveGame into the UI, allowing it to be displayed
     * @param game
     */
    public void loadSaveGame(SaveGame game) {
    }
}


