package paradox.ui;

import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.xml.soap.Text;
import javafx.event.ActionEvent;
import java.io.File;

public class MainController {

    @FXML private Text fileTarget;

    @FXML private Stage stage;

    @FXML public void handleSaveAsFileButtonAction(ActionEvent actionEvent) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Select save file");
        File savedFile = chooser.showSaveDialog(stage);
    }

    @FXML public void handleOpenButtonAction(ActionEvent actionEvent) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Select save file");
        File openedFile = chooser.showOpenDialog(stage);
    }
}
