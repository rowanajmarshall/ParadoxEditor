package paradox.ui.views;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import paradox.ui.guice.FXMLLoaderWithGuice;
import paradox.utils.FileUtils;

import java.io.InputStream;


public class ParadoxEditorView extends Application {

    private static final String FXML_FILE = "view_res/main.fxml";

    private FXMLLoader fxmlLoader;

    @Override
    public void init() throws Exception {
        this.fxmlLoader = new FXMLLoaderWithGuice();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Paradox Editor");

        try (final InputStream fxmlFileStream = FileUtils.acquireInputStreamFromFile(FXML_FILE)) {
            final Parent root = fxmlLoader.load(fxmlFileStream);
            primaryStage.setScene(new Scene(root));
            primaryStage.show();

        } catch (final Exception ex) {
            System.err.println("Unexpected exception occurred: " + ex.toString());
            // ensure that we do not start the application if it cannot be loaded.
            Platform.exit();
        }
    }
}


