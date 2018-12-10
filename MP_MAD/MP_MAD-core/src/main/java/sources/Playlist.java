package sources;

import java.util.ArrayList;

//TODO to jeszcze nie przemyslane
public class Playlist {
    private String name;
    ArrayList tracks;

    public Playlist(){
        tracks = new ArrayList();
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList getTracks() {
        return tracks;
    }

    public void addTracks(String path) {
        tracks.add(path);
    }
}
