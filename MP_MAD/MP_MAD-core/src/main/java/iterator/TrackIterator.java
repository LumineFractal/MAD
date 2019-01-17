package iterator;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Kaveri
 * @param <Track>
 */
public interface TrackIterator<Track> extends Iterator {

    void setIndexOfTrackInPlaylist(int indexOfTrackInPlaylist);

    ArrayList<Track> getTracks();
}
