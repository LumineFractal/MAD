package command;

import proxy.IPlaylist;
import sources.Track;

public class CommandRemoveTrack implements Command {

    private final IPlaylist playlist;
    private final Track track;

    public CommandRemoveTrack(IPlaylist playlist, Track track) {
        this.playlist = playlist;
        this.track = track;
    }

    public void execute() {
        playlist.removeTrack(track);
    }

    @Override
    public void undo() {
        playlist.addTrack(track);
    }

    @Override
    public void redo() {
        execute();
    }
}
