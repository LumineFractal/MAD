package controller;

import facade.Facade;
import javafx.beans.Observable;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import player.Player;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainController implements Initializable {

    private Facade facade = new Facade();

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
    private TabPane playlistContainer;

    @FXML
    private Slider progressBar;

    @FXML
    private Button playButton;

    @FXML
    private MenuItem menuAddList;

    public TabPane getPlaylistContainer() {
        return playlistContainer;
    }

    @FXML
    void playButtonActionListener(ActionEvent event) {
        if (playlistContainer.getTabs().size() != 0) {
            facade.playTrack(-1, null);
        }
    }

    @FXML
    void previousButtonActionListener(ActionEvent event) {

    }

    @FXML
    void nextButtonActionListener(ActionEvent event) {
        
    }

    @FXML
    void playmodeButtonActionListener(ActionEvent event) {

    }

    @FXML
    void addListActionListener(ActionEvent event) {
        facade.createPlaylist(facade.namePlaylistUnique("Playlist" + playlistContainer.getTabs().size()));
        Tab tab = new Tab(Facade.getPlaylistManager().getPlaylist(Facade.getPlaylistManager().getPlaylists().size() - 1).getName());

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/FXML/ListMusic.fxml"));

        TableView tableTrack = null;

        try {
            tableTrack = loader.load();
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        ListController listController = loader.getController();

        listController.setParent(this);
        listController.setFacade(facade);

        tab.setContent(tableTrack);
        playlistContainer.getTabs().add(tab);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        volume.setValue(0.5 * 100);
        volume.valueProperty().addListener((Observable observable) -> {
            facade.setVolume(volume.getValue());
        });

        progressBar.setOnMouseReleased((MouseEvent event) -> {
            setCurrentTime();
        });

        refreshProgressBar();
    }

    public void refreshProgressBar() {
        Task t2 = new Task<Void>() {
            @Override
            public Void call() {
                Player player = Player.getInstance();
                while (true) {
                    if ((player.getMediaPlayer() != null)) {
                        //chyba isTrackAssigned bedzie mozna wywalic
                        if (progressBar.isValueChanging() == false && player.isPlaying() && player.getTrack() != null) {
                            double c2 = player.getMediaPlayer().getCurrentTime().toMillis();
                            double s2 = (c2 / player.getMediaPlayer().getStopTime().toMillis() * 100);
                            progressBar.setValue(s2);
                        }
                    }
                    //jak ani razu nie uruchomimy piosenki
                    try {
                        Thread.sleep(400);
                    } catch (InterruptedException e) {
                        System.out.println("refreshSeek error");
                    }
                }
            }
        };

        Thread xx = new Thread(t2);
        xx.setDaemon(true);
        xx.start();
    }

    public void setCurrentTime() {
        Player player = Player.getInstance();
        double newSongTime = ((progressBar.getValue() / 100) * player.getMediaPlayer().getStopTime().toMillis());
        player.getMediaPlayer().seek(Duration.millis(newSongTime));
    }
}
