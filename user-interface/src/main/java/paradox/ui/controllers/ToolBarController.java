package paradox.ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuBar;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import javax.inject.Inject;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Optional;

public class ToolBarController {

    private final FileProcessingController fileProcessor;

    @FXML
    protected MenuBar toolbar;

    @Inject
    public ToolBarController(final FileProcessingController fileProcessor) {
        this.fileProcessor = fileProcessor;
    }

    @FXML
    protected void loadFile() {
            showLoadFileDialogue().ifPresent(file -> {
                try {
                    fileProcessor.processFile(file);
                } catch (final FileNotFoundException e) {
                    showErrorDialogue(file);
                }
            });
    }

    private Optional<File> showLoadFileDialogue() {

        final FileChooser fileChooser = new FileChooser();
        final Window sourceWindow = toolbar.getScene().getWindow();

        fileChooser.setTitle("Load paradox save-game file...");

        return Optional.ofNullable(fileChooser.showOpenDialog(sourceWindow));
    }

    private void showErrorDialogue(final File file) {
        final Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("File could not be found...");
        alert.setHeaderText("File: " + file.getName() + " could not be found.");
        alert.setContentText("The file at " + file.getAbsolutePath() + " could not be" +
                " read and was either deleted or corrupt.");

        alert.showAndWait();
    }
}
