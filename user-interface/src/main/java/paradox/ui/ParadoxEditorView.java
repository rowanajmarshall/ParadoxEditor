package paradox.ui;

import com.google.inject.Guice;
import com.google.inject.Injector;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;


public class ParadoxEditorView extends Application {

    private static final String UI_FILE = "view_res/main.fxml";
    private FXMLLoader fxmlLoader;

    @Override
    public void init() throws Exception {
        this.fxmlLoader = new FXMLLoader();
        final Injector injector = Guice.createInjector(new ApplicationModule());
        this.fxmlLoader.setControllerFactory(injector::getInstance);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Paradox Editor");

        primaryStage.setScene(Optional.ofNullable(captureResource())
                .flatMap(fileStream -> {
                    try {
                        final Parent root = fxmlLoader.load(fileStream);
                        return Optional.of(new Scene(root, 600, 400));

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    return Optional.empty();
                }).orElseThrow(() -> new FileNotFoundException("File not found lol")));

        primaryStage.show();
    }

    private InputStream captureResource() {
        return getClass().getClassLoader().getResourceAsStream(UI_FILE);
    }
}


