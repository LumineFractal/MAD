package command;

import player.PlaylistManager;
import proxy.IPlaylist;

public class CommandRemovePlaylist implements Command {

    private final IPlaylist playlist;
    private final int playlistId;

    public CommandRemovePlaylist(IPlaylist playlist, int id) {
        this.playlist = playlist;
        this.playlistId = id;
    }

    public void execute() {
        PlaylistManager.getInstance().removePlaylist(playlistId);
    }

    @Override
    public void undo() {
        PlaylistManager.getInstance().getPlaylists().add(playlist);
    }

    @Override
    public void redo() {
        execute();
    }
}
