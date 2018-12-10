import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("/FXML/MainWindow.fxml"));

        VBox mainWindow = loader.load();
        Scene scene = new Scene(mainWindow);

        primaryStage.setTitle("Music player MAD");
        primaryStage.getIcons().add(new Image("file:Icon_MP_MAD.png"));
        primaryStage.setScene(scene);
        primaryStage.setMinHeight(600);
        primaryStage.setMinWidth(400);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
