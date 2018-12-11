package player;

import java.io.File;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.util.Duration;

public class Player {

    private static final Player instance = new Player();
    public MediaPlayer mediaPlayer;
    public static String defaultPath = System.getProperty("user.home") + "/Desktop";
    private String currentTrack;
    private boolean trackAssigned;
    private double volume;

    private Player() {
        trackAssigned = false;
        volume = 0.5;
    }

    public void play(String track) {

        trackAssigned = true;

        //jezeli podana ta sama sciezka, co wczesniej
        if (track.equals(currentTrack)) {
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
            Media hit = new Media(new File(System.getProperty("user.home"), "Desktop" + track).toURI().toString());
            mediaPlayer = new MediaPlayer(hit);
            mediaPlayer.setVolume(volume);
            currentTrack = track;
            if (!(isPlaying())) {
                mediaPlayer.play();
            }else {
                mediaPlayer.pause();
            }
        }
        //TODO randomowe sprawdzanie
        //System.out.print(mediaPlayer.getCurrentTime());
        //System.out.print(mediaPlayer.getStatus());
        //System.out.println(mediaPlayer.getStopTime());
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

    public boolean isTrackAssigned() {
        return trackAssigned;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public double getCurrentTime() {
        return mediaPlayer.getCurrentTime().toMillis();
    }

    //TODO sciezki cos
    public void test() {
        //players.add(createPlayer("file:///" + (dir + "\\" + track).replace("\\", "/").replaceAll(" ", "%20")));
    }

}
