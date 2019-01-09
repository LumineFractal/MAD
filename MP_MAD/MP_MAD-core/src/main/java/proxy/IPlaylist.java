package proxy;

import java.util.Iterator;
import java.util.List;
import sources.Track;

/**
 *
 * @author Acerek
 */
public interface IPlaylist {
    public String getName();
    public void setName(String name);
    public List<Track> getTracks();
    public Track getTrack(int id);
    public void addTrack(Track track);
    public void removeTrack(Track track);
    public Iterator getIterator(int id); 
}
