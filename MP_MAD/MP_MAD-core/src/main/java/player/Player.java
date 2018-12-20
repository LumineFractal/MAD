package player;

import java.io.File;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.util.Duration;
import sources.Track;

public class Player {

    private static final Player instance = new Player();
    private MediaPlayer mediaPlayer;
    //TODO private Observer observer;
    private Track track;
    private double volume;
    private int actualPlaylist;

    private Player() {
        volume = 0.5;
    }

    public void play(int actualPlaylist, Track track) {

        //jezeli podana ta sama sciezka, co wczesniej
        if (this.track.getPath() == track.getPath() && this.actualPlaylist == actualPlaylist) {
            if (!isPlaying()) {
                mediaPlayer.play();
            } else if (hasEnded()) {
                mediaPlayer.seek(Duration.ZERO);
            } else {
                mediaPlayer.pause();
            }

        } //jezeli podana inna sciezka
        else {
            //jezeli inny utwor jest w trakcie odtwarzania
            if (mediaPlayer != null) {
                if (isPlaying()) {
                    mediaPlayer.stop();
                }
            }
            Media hit = new Media(new File(System.getProperty("user.home"), "Desktop" + track.getPath()).toURI().toString());
            mediaPlayer = new MediaPlayer(hit);
            mediaPlayer.setVolume(volume);
            if (!(isPlaying())) {
                mediaPlayer.play();
            }else {
                mediaPlayer.pause();
            }
        }
    }

    public Track getTrack(){
        return track;
    }

    public int getActualPlaylist() {
        return actualPlaylist;
    }

    public boolean isPlaying() {
        Status status = mediaPlayer.getStatus();
        return status == Status.PLAYING;
    }

    public boolean hasEnded() {
        return mediaPlayer.getCurrentTime().equals(mediaPlayer.getStopTime());
    }

    public static Player getInstance() {
        return instance;
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public double getCurrentTime() {
        return mediaPlayer.getCurrentTime().toMillis();
    }

    //TODO observer
    /*public void attach(Observer observer){

    }

    public void detach(Observer observer){

    }

    public void notify(){

    }*/

}
