import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import memento.CareTaker;

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

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                CareTaker careTaker = new CareTaker();

            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}

