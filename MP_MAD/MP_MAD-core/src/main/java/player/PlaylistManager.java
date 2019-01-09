package player;

import command.Command;
import proxy.IPlaylist;
import proxy.Playlist;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class PlaylistManager implements Command {
    private static final PlaylistManager instance = new PlaylistManager();
    private Stack<Command> undoList = new Stack<>();
    private Stack<Command> redoList = new Stack<>();
    private List<IPlaylist> playlists;

    private PlaylistManager(){
        playlists = new ArrayList<>();
    }

    public static PlaylistManager getInstance() {
        return instance;
    }

    public void createPlaylist(String name) {
        IPlaylist playlist = new Playlist();
        playlist.setName(name);
        playlists.add(playlist);
    }

    public void removePlaylist(int index) {
        playlists.remove(index);
    }

    public IPlaylist getPlaylist(int index) {
        return playlists.get(index);
    }

    public List<IPlaylist> getPlaylists() {
        return playlists;
    }

    @Override
    public void execute() {

    }

    @Override
    public void undo() {

    }

    @Override
    public void redo() {

    }

    public boolean isUndoAvailable() {
        return !undoList.empty();
    }

    public boolean isRedoAvailable() {
        return !redoList.empty();
    }

    public void createXML(IPlaylist playlist) {

    }

    public void createJSON(IPlaylist playlist) {

    }
}
