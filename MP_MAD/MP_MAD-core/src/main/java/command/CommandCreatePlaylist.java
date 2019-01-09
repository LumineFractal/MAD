package command;

import proxy.IPlaylist;

public class CommandCreatePlaylist implements Command {

    private IPlaylist playlist;

    public CommandCreatePlaylist(IPlaylist playlist) {
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
