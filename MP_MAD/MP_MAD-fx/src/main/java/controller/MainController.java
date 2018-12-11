package controller;

import java.io.File;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import player.Player;
import sources.Playlist;
import sources.Track;

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
    void previousButtonActionListener(ActionEvent event) throws CannotReadException, IOException, TagException, ReadOnlyFileException  {
        
        //TODO test tagów (wywalic pozniej powyzsze wyrzucenia wyjatkow)
        try {
            Track track = new Track(new File("Sunglow.mp3"));
            System.out.println(track.getArtist());
            System.out.println(track.getTitle());
            System.out.println(track.getPath());
            System.out.println(track.getGenre());
            System.out.println(track.getYear());
            System.out.println(track.getLength());
            System.out.println(track.getAlbum());
            
            Playlist playlist = new Playlist();
            playlist.addTrack(track);
            
            System.out.println(playlist.getTracks());
            
        } catch (InvalidAudioFrameException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
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
