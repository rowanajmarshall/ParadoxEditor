import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.xml.soap.Text;
import javafx.event.ActionEvent;

/**
 * Created by roan on 31/05/17.
 */
public class MainController {

    @FXML private Text fileTarget;

    @FXML private Stage stage;

    @FXML public void handleSaveAsFileButtonAction(ActionEvent actionEvent) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Select save file");
        chooser.showSaveDialog(stage);
    }
}
