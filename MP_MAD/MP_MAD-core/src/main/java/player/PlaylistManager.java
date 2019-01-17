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
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class PlaylistManager implements Command, Observer {

    private static final PlaylistManager instance = new PlaylistManager();
    private Stack<Command> undoList = new Stack<>();
    private Stack<Command> redoList = new Stack<>();
    private List<IPlaylist> playlists;
    private EnumIterator.iterator nameIterator = EnumIterator.iterator.DEFAULT;
    private TrackIterator iterator;

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
        playlists.get(index).getIterator(nameIterator);
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

    public void createXML() throws IOException {
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
        }
        File ww = new File(s + "/playlists", "playlists.xml");
        ww.createNewFile();
        try (FileWriter writer = new FileWriter(ww)) {
            writer.write(builderXML.getResult());
        }

    }

    public void createJSON() throws IOException {
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
        }
        File ww = new File(s + "/playlists", "playlists.json");
        ww.createNewFile();
        try (FileWriter writer = new FileWriter(ww)) {
            writer.write(builderJSON.getResult());
        }

    }

    public void loadFromXML(){
        try {
            Path currentRelativePath = Paths.get("");
            String relativePath = currentRelativePath.toAbsolutePath().toString();
            File file = new File(relativePath + "/playlists", "playlists.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("Playlist");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);        

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    Playlist loadedPlaylist = new Playlist();
                    loadedPlaylist.setName(eElement.getAttribute("name"));
                    NodeList list = eElement.getElementsByTagName("Track");
                    for(int temp2 = 0; temp2 < list.getLength(); temp2++){
                        String trackPath = eElement.getElementsByTagName("Track").item(temp2).getTextContent();
                        Track track = new Track(new File(trackPath));
                        loadedPlaylist.addTrack(track);
                    }
                    playlists.add(loadedPlaylist);
                }
            }
        } catch (Exception e) {
           e.printStackTrace();
        }
    }
    
    public void loadFromJSON() throws FileNotFoundException, ParseException{
        Path currentRelativePath = Paths.get("");
        String relativePath = currentRelativePath.toAbsolutePath().toString();
        
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader(relativePath + "/playlists/playlists.json"));
            
            JSONObject jsonObject = (JSONObject) obj;
            
            
            
            JSONArray playlisty = (JSONArray) jsonObject.get("Playlists");
            Iterator<JSONObject> iteratorPlalists = playlisty.iterator();
            while(iteratorPlalists.hasNext()) {
                JSONObject jsonPlaylist = iteratorPlalists.next();
                Playlist playlist = new Playlist();
                playlist.setName((String) jsonPlaylist.get("Playlist title"));
                
                JSONArray trackList = (JSONArray) jsonPlaylist.get("Tracks");
                Iterator<String> iteratorTracks = trackList.iterator();
                while(iteratorTracks.hasNext()) {
                    String trackPath = iteratorTracks.next();
                    Track loadedTrack = new Track(new File(trackPath));
                    playlist.addTrack(loadedTrack);
                }
                playlists.add(playlist);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    
    public void setIterator() {
        if (iterator == null) {
            this.iterator = (TrackIterator) playlists.get(Player.getInstance().getActualPlaylist()).getIterator(nameIterator);
        }
    }

    public void setTrackInIterator(Track track) {
        setIterator();
        if(playlists.get(Player.getInstance().getActualPlaylist()).getTracks() != iterator.getTracks()){
            iterator = (TrackIterator)playlists.get(Player.getInstance().getActualPlaylist()).getIterator(nameIterator);
        }
        ArrayList<Track> tracks = playlists.get(Player.getInstance().getActualPlaylist()).getTracks();
        for (int i = 0; i < tracks.size(); i++) {
            if (tracks.get(i).equals(track)) {
                iterator.setIndexOfTrackInPlaylist(i);
                return;
            }
        }
        for (int i = 0; i < tracks.size(); i++) {
            if (tracks.get(i).getPath().equals(track.getPath())) {
                iterator.setIndexOfTrackInPlaylist(i);
                return;
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
