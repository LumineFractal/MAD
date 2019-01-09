package command;

import proxy.IPlaylist;

public class CommandEditPlaylist implements Command {

    private IPlaylist playlist;

    public CommandEditPlaylist(IPlaylist playlist) {
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
