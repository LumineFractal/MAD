package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import player.Player;

public class MainController implements Initializable {
    
    @FXML
    private Button nextButton;
    
    @FXML
    private Slider volume;
    
    @FXML
    private ToggleButton loopmodeButton;
    
    @FXML
    private Button previousButton;
    
    @FXML
    private ToggleButton playmodeButton;
    
    @FXML
    private Slider progressBar;
    
    @FXML
    private Button playButton;
    
    @FXML
    void playButtonActionListener(ActionEvent event) {
        Player player = Player.getInstance();
        String track = "/Sonnetica.mp3";
        player.play(track);
    }
    
    @FXML
    void previousButtonActionListener(ActionEvent event) {
        
    }
    
    @FXML
    void nextButtonActionListener(ActionEvent event) {
        Player player = Player.getInstance();
        String track = "/Sunglow.mp3";
        player.play(track);
    }
    
    @FXML
    void playmodeButtonActionListener(ActionEvent event) {
        
    }
    
    public void initialize(URL location, ResourceBundle resources) {
        volume.setValue(0.5 * 100);
        volume.valueProperty().addListener(new InvalidationListener() {
            
            public void invalidated(Observable observable) {
                Player player = Player.getInstance();
                player.mediaPlayer.setVolume(volume.getValue() / 100);
            }
            
        });
    }
}
