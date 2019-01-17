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
    String getName();

    void setName(String name);

    ArrayList<Track> getTracks();

    Track getTrack(int id);

    void addTrack(Track track);

    IPlaylist copy();

    void setTracks(List<Track> tracks);

    void removeTrack(Track track);

    Iterator<Track> getIterator(EnumIterator.iterator iterator);

}
