package command;

import proxy.IPlaylist;
import sources.Track;

import java.util.ArrayList;

public class CommandEditPlaylist implements Command {

    private final IPlaylist playlist;
    private final ArrayList<Track> previousList;
    private final ArrayList<Track> newList;

    public CommandEditPlaylist(IPlaylist playlist, ArrayList newList) {
        this.playlist = playlist;
        this.previousList = playlist.getTracks();
        this.newList = newList;
    }

    public void execute() {
        playlist.setTracks(newList);
    }

    @Override
    public void undo() {
        playlist.setTracks(previousList);
    }

    @Override
    public void redo() {
        execute();
    }
}