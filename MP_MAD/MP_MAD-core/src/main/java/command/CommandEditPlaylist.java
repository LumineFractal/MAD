package command;

import proxy.IPlaylist;

public class CommandEditPlaylist implements Command {

    private final IPlaylist playlist;
    private final String previousName;
    private final String newName;

    public CommandEditPlaylist(IPlaylist playlist, String name) {
        this.playlist = playlist;
        this.previousName = playlist.getName();
        this.newName = name;
    }

    public void execute() {
        playlist.setName(newName);
    }

    @Override
    public void undo() {
        playlist.setName(previousName);
    }

    @Override
    public void redo() {
        execute();
    }
}
