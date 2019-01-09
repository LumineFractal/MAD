package command;

import proxy.IPlaylist;

public class CommandRemoveTrack implements Command {

    private IPlaylist playlist;

    public CommandRemoveTrack(IPlaylist playlist) {
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
