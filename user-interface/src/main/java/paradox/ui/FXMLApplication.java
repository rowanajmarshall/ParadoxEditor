package paradox.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FXMLApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("view_res/main.fxml"));
        Scene scene = new Scene(root, 600, 400);

        primaryStage.setTitle("Paradox Editor");
        primaryStage.setScene(scene);

        primaryStage.show();
    }
}


