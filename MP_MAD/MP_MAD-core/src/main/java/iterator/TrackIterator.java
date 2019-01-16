package iterator;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Kaveri
 * @param <Track>
 */
public interface TrackIterator<Track> extends Iterator {

    public void setIndexOfTrackInPlaylist(int indexOfTrackInPlaylist);
    public ArrayList<Track> getTracks();
}
