package player;

import builder.BuilderJSON;
import builder.BuilderXML;
import command.Command;
import iterator.EnumIterator;
import iterator.TrackIterator;
import proxy.IPlaylist;
import proxy.Playlist;
import sources.Track;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    public void createXML(IPlaylist playlist) throws IOException {
        BuilderXML builderXML = new BuilderXML();

        for (int i = 0; i < playlists.size(); i++) {
            builderXML.addTitle(playlists.get(i).getName());
            for (int j = 0; j < playlists.get(i).getTracks().size(); j++)
                builderXML.addTrack(playlists.get(i).getTracks().get(j));
        }

        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        File f = new File(s + "/playlists");
        if (!f.exists() || !f.isDirectory()) {
            new File("playlists").mkdirs();
            System.out.println("Tworzenie folderu");
        }
        File ww = new File(s + "/playlists", "playlists.xml");
        ww.createNewFile();
        try (FileWriter writer = new FileWriter(ww)) {
            writer.write(builderXML.getResult());
        }

    }

    public void createJSON(IPlaylist playlist) throws IOException {
        BuilderJSON builderJSON = new BuilderJSON();

        for (int i = 0; i < playlists.size(); i++) {
            builderJSON.addTitle(playlists.get(i).getName());
            for (int j = 0; j < playlists.get(i).getTracks().size(); j++)
                builderJSON.addTrack(playlists.get(i).getTracks().get(j));
        }

        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        File f = new File(s + "/playlists");
        if (!f.exists() || !f.isDirectory()) {
            new File("playlists").mkdirs();
            System.out.println("Tworzenie folderu");
        }
        File ww = new File(s + "/playlists", "playlists.json");
        ww.createNewFile();
        try (FileWriter writer = new FileWriter(ww)) {
            writer.write(builderJSON.getResult());
        }

    }

    private TrackIterator iterator;

    public void setIterator() {
        if (iterator == null) {
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
        if (iterator.hasNext()) {
            Player.getInstance().play(Player.getInstance().getActualPlaylist(), (Track) iterator.next(), true);
        }
    }
}
