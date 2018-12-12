package player;

import sources.Playlist;

import java.util.ArrayList;
import java.util.List;

public class PlaylistManager {
    private static final PlaylistManager instance = new PlaylistManager();
    private List<Playlist> playlistList;

    private PlaylistManager(){
        playlistList = new ArrayList<>();
    }

    public static PlaylistManager getInstance() {
        return instance;
    }

    public void addPlaylist(String name){
        Playlist playlist = new Playlist();
        playlist.setName(name);
        playlistList.add(playlist);
    }

    public List<Playlist> getPlaylistList() {
        return playlistList;
    }
}
