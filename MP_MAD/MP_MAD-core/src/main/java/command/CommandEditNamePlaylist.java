package command;

import proxy.IPlaylist;

public class CommandEditNamePlaylist implements Command {

    private final IPlaylist playlist;
    private final String previousName;
    private final String newName;

    public CommandEditNamePlaylist(IPlaylist playlist, String name) {
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
