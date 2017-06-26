package paradox.ui.views;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.MenuBar;
import paradox.ui.guice.FXMLLoaderWithGuice;
import paradox.utils.FileUtils;

import java.io.IOException;

public final class ToolBar extends MenuBar {

    private static final String FXML_FILE = "view_res/toolbar.fxml";

    public ToolBar() {
        try {
            final FXMLLoader fxmlLoader = new FXMLLoaderWithGuice(FileUtils.acquireFile(FXML_FILE));

            fxmlLoader.setRoot(this);

            fxmlLoader.load();

        } catch (final IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
