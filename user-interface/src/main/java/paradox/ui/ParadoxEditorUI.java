package paradox.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import paradox.SaveGame;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;


public class ParadoxEditorUI extends Application {

    public void begin() {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Optional<Parent> parent = Optional.ofNullable(getClass().getClassLoader().getResource("view_res/main.fxml"))
                .flatMap(loc -> {
                    try {
                        return Optional.of(FXMLLoader.<Parent>load(loc));
                    } catch (IOException e) {
                        return Optional.empty();
                    }
                });

        if (!parent.isPresent())
            throw new FileNotFoundException("view_res/main.fxml");

        parent.ifPresent(root -> {
            final Scene scene = new Scene(root, 600, 400);

            primaryStage.setTitle("Paradox Editor");
            primaryStage.setScene(scene);

            primaryStage.show();
        });
    }

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


