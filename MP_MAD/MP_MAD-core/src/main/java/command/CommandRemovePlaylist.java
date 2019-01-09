package command;

import proxy.IPlaylist;

public class CommandRemovePlaylist implements Command {

    private IPlaylist playlist;

    public CommandRemovePlaylist(IPlaylist playlist) {
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
