package iterator;

import sources.Track;

import java.util.ArrayList;
import java.util.Iterator;

public class IteratorRepeatable implements Iterator<Track> {
    ArrayList<Track> tracks;
    int y = -1;

    public IteratorRepeatable(ArrayList tracks) {
        this.tracks = tracks;
    }

    @Override
    public boolean hasNext() {
        return true;
    }

    @Override
    public Track next() {
        y++;
        if (tracks.size() == y) {
            y = 0;
        }
        System.out.println(y);
        return (Track) tracks.get(y);
    }
}
