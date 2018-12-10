package player;

import java.io.File;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;

public class Player {

    private static final Player instance = new Player();
    public MediaPlayer mediaPlayer;
    public static String defaultPath = System.getProperty("user.home") + "/Desktop";
    private String currentTrack;

    private Player() {

    }

    public void play(String track) {
        //TODO randomowe sprawdzanie
        //System.out.print(mediaPlayer.getCurrentTime());
        //System.out.print(mediaPlayer.getStatus());
        //System.out.println(mediaPlayer.getStopTime());

        //jezeli podana ta sama sciezka, co wczesniej
        if (track.equals(currentTrack)) {
            Status status = mediaPlayer.getStatus();
            if (status != Status.PLAYING) {
                mediaPlayer.play();
            } else if (hasEnded()) {
                mediaPlayer.stop();
                mediaPlayer.play();
            } else {
                mediaPlayer.stop();
            }
        } //jezeli podana inna sciezka
        else {
            //jezeli inny utwor jest w trakcie odtwarzania
            if (mediaPlayer != null) {
                Status status = mediaPlayer.getStatus();
                if (status == Status.PLAYING) {
                    mediaPlayer.stop();
                }
            }
            Media hit = new Media(new File(System.getProperty("user.home"), "Desktop" + track).toURI().toString());
            mediaPlayer = new MediaPlayer(hit);
            mediaPlayer.setVolume(0.5);
            currentTrack = track;
            Status status = mediaPlayer.getStatus();
            if (status != Status.PLAYING) {
                mediaPlayer.play();
            } else if (hasEnded()) {
                mediaPlayer.stop();
                mediaPlayer.play();
            } else {
                mediaPlayer.stop();
            }
        }

    }

    public boolean hasEnded() {
        return mediaPlayer.getCurrentTime().equals(mediaPlayer.getStopTime());
    }

    public static Player getInstance() {
        return instance;
    }

    //TODO sciezki cos
    public void test() {
        //players.add(createPlayer("file:///" + (dir + "\\" + track).replace("\\", "/").replaceAll(" ", "%20")));
    }

}
