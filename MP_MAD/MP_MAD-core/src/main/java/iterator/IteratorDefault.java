package iterator;

import sources.Track;

import java.util.ArrayList;
import java.util.Iterator;

public class IteratorDefault implements Iterator<Track> {
    ArrayList<Track> tracks;
    int y = -1;

    public IteratorDefault(ArrayList tracks) {
        this.tracks = tracks;
    }

    @Override
    public boolean hasNext() {
        if (tracks.size() - 1 > y) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Track next() {
        y++;
        return (Track) tracks.get(y);
    }
}
