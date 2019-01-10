package facade;

import player.Player;
import player.PlaylistManager;
import sources.Track;

import java.util.List;

public class Facade {
    private static PlaylistManager playlistManager;
    private static Player player;

    public Facade() {
        player = Player.getInstance();
        playlistManager = PlaylistManager.getInstance();
    }

    public static PlaylistManager getPlaylistManager() {
        return playlistManager;
    }

    public static Player getPlayer() {
        return player;
    }

    public void playTrack(int idxPlaylist, Track track) {
        if (idxPlaylist == -1 && track == null) {
            if (player.getTrack() != null) {
                player.play(player.getActualPlaylist(), player.getTrack());
            } else if (playlistManager.getPlaylists().size() != 0 && playlistManager.getPlaylist(player.getActualPlaylist()).getTracks().size() != 0) {
                player.play(0, playlistManager.getPlaylist(0).getTrack(0));
            }
        } else {
            player.play(idxPlaylist, track);
        }
    }

    public void setVolume(double volume) {
        player.setVolume(volume / 100);
        player.getMediaPlayer().setVolume(volume / 100);
    }

    public void createPlaylist(String name) {
        playlistManager.createPlaylist(name);
    }

    public void removePlaylist(String name) {

    }

    public void addTrack(int idx, Track track) {
        playlistManager.getPlaylist(idx).addTrack(track);
    }

    public void editPlaylist(int idx, List<Track> tracks) {

    }

    public void removetrack(int idx, Track track) {

    }

    public void chooseIterator(int idx) {

    }

    public void nextTrack() {

    }

    public void previousTrack() {

    }

    public String namePlaylistUnique(String name) {
        for (int i = 0; i < playlistManager.getPlaylists().size(); i++) {
            if (playlistManager.getPlaylists().get(i).getName().equals(name)) {
                name = name.concat(String.valueOf(i));
                i = 0;
            }
        }

        return name;
    }

}
