package proxy;

import java.util.Iterator;
import java.util.List;
import sources.Track;

/**
 *
 * @author Acerek
 */
public class Playlist implements IPlaylist{
    private String name;
    private List<Track> tracks;
    private List<CopyPlaylist> proxyPlaylists;
    
    protected void disconnect(){
        //TODO
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public List<Track> getTracks() {
        return tracks;
    }

    @Override
    public Track getTrack(int id) {
        return tracks.get(id);
    }

    @Override
    public void addTrack(Track track) {
        tracks.add(track);
    }

    @Override
    public void removeTrack(Track track) {
        tracks.remove(track);
    }

    @Override
    public Iterator getIterator(int id) {
        //TODO
        Iterator iterator = null;
        return iterator;
    }
    
}
