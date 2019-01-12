package player;

import command.Command;
import iterator.*;
import proxy.IPlaylist;
import proxy.Playlist;
import sources.Track;

import java.util.*;

public class PlaylistManager implements Command, Observer {

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
                    if (!isActive) {
                        nameIterator = EnumIterator.iterator.RANDOM;
                    }
                } else if (!isActive) {
                    nameIterator = EnumIterator.iterator.REPEATABLE;
                }
                break;
            }
            case REPEATABLE: {
                if (RandomOrRepeatable) {
                    if (!isActive) {
                        nameIterator = EnumIterator.iterator.RANDOMREPEATABLE;
                    }
                } else if (isActive) {
                    nameIterator = EnumIterator.iterator.DEFAULT;
                }
                break;
            }
            case RANDOM: {
                if (RandomOrRepeatable) {
                    if (isActive) {
                        nameIterator = EnumIterator.iterator.DEFAULT;
                    }
                } else if (!isActive) {
                    nameIterator = EnumIterator.iterator.RANDOMREPEATABLE;
                }
                break;
            }
            case RANDOMREPEATABLE: {
                if (RandomOrRepeatable) {
                    if (isActive) {
                        nameIterator = EnumIterator.iterator.REPEATABLE;
                    }
                } else if (isActive) {
                    nameIterator = EnumIterator.iterator.RANDOM;
                }
                break;
            }
        }
        iterator = (TrackIterator) playlists.get(Player.getInstance().getActualPlaylist()).getIterator(nameIterator);
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

    public Iterator<Track> chooseIterator(EnumIterator.iterator iterator, ArrayList<Track> tracks) {
        switch (iterator) {
            case DEFAULT: {
                return new IteratorDefault(tracks);
            }
            case REPEATABLE: {
                return new IteratorRepeatable(tracks);
            }
            case RANDOM: {
                return new IteratorRandom(tracks);
            }
            case RANDOMREPEATABLE: {
                return new IteratorRandomRepeatable(tracks);
            }
            default: {
                return new IteratorDefault(tracks);
            }
        }
    }

    private TrackIterator iterator;

    public void setIterator() {
        if (iterator == null ) {
            this.iterator = (TrackIterator) playlists.get(Player.getInstance().getActualPlaylist()).getIterator(nameIterator);
        }
    }

    public void setTrackInIterator(Track track) {
        setIterator();
        ArrayList tracks = playlists.get(Player.getInstance().getActualPlaylist()).getTracks();
        //TODO
        for (int i = 0; i < tracks.size(); i++) {
            if (tracks.get(i).equals(track)) {
                iterator.setIndexOfTrackInPlaylist(i);
            }
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        setIterator();
        if(iterator.hasNext()){
            Player.getInstance().play(Player.getInstance().getActualPlaylist(), (Track) iterator.next(), true);
        }
    }
}
