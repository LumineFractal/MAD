import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("/FXML/MainWindow.fxml"));
        GridPane mainWindow = loader.load();
        Scene scene = new Scene(mainWindow);
        scene.getStylesheets().add("css/style.css");
        primaryStage.setTitle("Music player MAD");
        primaryStage.getIcons().add(new Image("file:image/Icon_MP_MAD.png"));
        primaryStage.setScene(scene);
        primaryStage.setMinHeight(600);
        primaryStage.setMinWidth(450);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

