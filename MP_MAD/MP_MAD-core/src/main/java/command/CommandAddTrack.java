package command;

import proxy.IPlaylist;

public class CommandAddTrack implements Command {

    private IPlaylist playlist;

    public CommandAddTrack(IPlaylist playlist) {
        this.playlist = playlist;
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
}
