package player;

import command.Command;
import iterator.EnumIterator;
import proxy.IPlaylist;
import proxy.Playlist;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class PlaylistManager implements Command {
    private static final PlaylistManager instance = new PlaylistManager();
    private Stack<Command> undoList = new Stack<>();
    private Stack<Command> redoList = new Stack<>();
    private List<IPlaylist> playlists;
    private EnumIterator.iterator nameIterator = EnumIterator.iterator.DEFAULT;


    private PlaylistManager() {
        playlists = new ArrayList<>();
    }

    public static PlaylistManager getInstance() {
        return instance;
    }

    public void createPlaylist(String name) {
        IPlaylist playlist = new Playlist();
        playlist.setName(name);
        playlists.add(playlist);
    }

    public void addUndo(Command command) {
        undoList.push(command);
        redoList.clear();
    }

    public void addRedo(Command command) {
        redoList.push(command);
    }

    public void removePlaylist(int index) {
        playlists.remove(index);
    }

    public IPlaylist getPlaylist(int index) {
        return playlists.get(index);
    }

    public List<IPlaylist> getPlaylists() {
        return playlists;
    }

    public EnumIterator.iterator getNameIterator() {
        return nameIterator;
    }

    public void setNameIterator(boolean isActive, boolean RandomOrRepeatable) {
        switch (nameIterator) {
            case DEFAULT: {
                if (RandomOrRepeatable) {
                    if (!isActive)
                        nameIterator = EnumIterator.iterator.RANDOM;
                } else if (!isActive)
                    nameIterator = EnumIterator.iterator.REPEATABLE;
                break;
            }
            case REPEATABLE: {
                if (RandomOrRepeatable) {
                    if (isActive)
                        nameIterator = EnumIterator.iterator.RANDOMREPEATABLE;
                } else if (!isActive)
                    nameIterator = EnumIterator.iterator.DEFAULT;
                break;
            }
            case RANDOM: {
                if (RandomOrRepeatable) {
                    if (!isActive)
                        nameIterator = EnumIterator.iterator.DEFAULT;
                } else if (isActive)
                    nameIterator = EnumIterator.iterator.RANDOMREPEATABLE;
                break;
            }
            case RANDOMREPEATABLE: {
                if (RandomOrRepeatable) {
                    if (isActive)
                        nameIterator = EnumIterator.iterator.REPEATABLE;
                } else if (isActive)
                    nameIterator = EnumIterator.iterator.RANDOM;
                break;
            }
        }
    }

    @Override
    public void undo() {
        undoList.peek().undo();
        redoList.push(undoList.pop());
    }

    @Override
    public void redo() {
        redoList.peek().redo();
        undoList.push(redoList.pop());
    }

    public boolean isUndoAvailable() {
        return !undoList.empty();
    }

    public boolean isRedoAvailable() {
        return !redoList.empty();
    }

    public void createXML(IPlaylist playlist) {

    }

    public void createJSON(IPlaylist playlist) {

    }
}
