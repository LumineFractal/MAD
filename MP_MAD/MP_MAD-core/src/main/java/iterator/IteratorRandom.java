package iterator;

import sources.Track;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class IteratorRandom implements Iterator<Track> {
    ArrayList<Track> tracks;
    ArrayList<Boolean> wasPlayed;
    Random generator = new Random();
    int y = -1;

    public IteratorRandom(ArrayList tracks) {
        this.tracks = tracks;
        wasPlayed = new ArrayList<Boolean>();
    }

    public void setList(int index) {
        wasPlayed.set(index, true);

    }

    public void createList() {
        for (int i = wasPlayed.size(); i < tracks.size(); i++) {
            wasPlayed.add(false);
        }
    }

    @Override
    public boolean hasNext() {
        if (wasPlayed.size() == tracks.size()) {
            if (wasPlayed.stream().allMatch(val -> val == true)) {
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    @Override
    public Track next() {
        // System.out.println(wasPlayed.toString());
        createList();
        // System.out.println(wasPlayed.toString());
        y = generator.nextInt(tracks.size());
        while (wasPlayed.get(y)) {
            y = generator.nextInt(tracks.size());

        }

        setList(y);
        System.out.println(y + tracks.get(y).getTitle());
        return (Track) tracks.get(y);
    }
}
