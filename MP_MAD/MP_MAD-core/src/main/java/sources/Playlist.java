package sources;

import java.util.ArrayList;
import java.util.List;

//TODO to jeszcze nie przemyslane
public class Playlist {
    private String name;
    private List<Track> tracks;

    public Playlist(){
        tracks = new ArrayList<>();
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public void addTrack(Track track) {
        tracks.add(track);
    }
}
