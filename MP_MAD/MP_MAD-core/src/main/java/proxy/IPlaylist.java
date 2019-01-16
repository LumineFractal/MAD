package proxy;

import iterator.EnumIterator;
import sources.Track;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Acerek
 */
public interface IPlaylist {
    public String getName();
    public void setName(String name);

    public ArrayList<Track> getTracks();
    public Track getTrack(int id);
    public void addTrack(Track track);
    public IPlaylist copy();

    void setTracks(List<Track> tracks);
    public void removeTrack(Track track);

    public Iterator<Track> getIterator(EnumIterator.iterator iterator);

}
