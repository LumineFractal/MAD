package command;

import proxy.IPlaylist;
import sources.Track;

public class CommandAddTrack implements Command {

    private final IPlaylist playlist;
    private final Track track;

    public CommandAddTrack(IPlaylist playlist, Track track) {
        this.playlist = playlist;
        this.track = track;
    }

    public void execute(){
        playlist.addTrack(track);
    }

    @Override
    public void undo() {
        playlist.removeTrack(track);
    }

    @Override
    public void redo() {
        execute();
    }
}
