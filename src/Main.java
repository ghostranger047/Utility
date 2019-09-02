import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.stage.Stage;

import javax.swing.text.Style;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
        primaryStage.setTitle("Utility");
        Scene scene = new Scene(root, 650, 500);
        scene.getStylesheets().add("Style.css");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

        //primaryStage.setResizable(false);
    }

    @Override
    public void stop() throws Exception
    {
        Controller.ping.interrupt();
        System.exit(0);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
