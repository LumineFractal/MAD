package facade;

import player.Player;
import player.PlaylistManager;
import sources.Track;

import java.util.List;

public class Facade {
    private static PlaylistManager playlistManager;
    private static Player player;

    public Facade(){
        player = Player.getInstance();
        playlistManager = PlaylistManager.getInstance();
    }

    public static PlaylistManager getPlaylistManager() {
        return playlistManager;
    }

    public static Player getPlayer() {
        return player;
    }

    public void playTrack(Track track){
        player.play(1, track);
    }

    public void setVolume(double volume){
        player.setVolume(volume / 100);
        player.getMediaPlayer().setVolume(volume / 100);
    }

    public void createPlaylist(String name){
        playlistManager.createPlaylist(name);
    }

    public void removePlaylist(String name){

    }

    public void addTrack(int idx, Track track) {
        playlistManager.getPlaylist(idx).addTrack(track);
    }

    public void editPlaylist(int idx, List<Track> tracks){

    }

    public void removetrack(int idx, Track track){

    }

    public void chooseIterator(int idx){

    }

    public void nextTrack(){

    }

    public void previousTrack(){

    }

}
