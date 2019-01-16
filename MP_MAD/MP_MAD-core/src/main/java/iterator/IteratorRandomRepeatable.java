package iterator;

import sources.Track;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class IteratorRandomRepeatable implements TrackIterator<Track> {

    private ArrayList<Track> tracks;
    private ArrayList<Boolean> wasPlayed;
    private Random generator = new Random();
    private int indexOfTrackInPlaylist = -1;
    // int e = 0;

    public IteratorRandomRepeatable(ArrayList tracks) {
        this.tracks = tracks;
        wasPlayed = new ArrayList<>();
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
    public void setIndexOfTrackInPlaylist(int indexOfTrackInPlaylist) {
        this.indexOfTrackInPlaylist = indexOfTrackInPlaylist;
        setList(indexOfTrackInPlaylist);
        System.out.println(indexOfTrackInPlaylist);
    }

    @Override
    public boolean hasNext() {
        return true;
    }

    @Override
    public Track next() {
        indexOfTrackInPlaylist = generator.nextInt(tracks.size());
        if (wasPlayed.size() != tracks.size()) {
            createList();
        }

        while (wasPlayed.get(indexOfTrackInPlaylist)) {
            indexOfTrackInPlaylist = generator.nextInt(tracks.size());

        }

        // System.out.println(wasPlayed.toString());
        setList(indexOfTrackInPlaylist);
        // System.out.println(wasPlayed.toString());
        // System.out.println(y + tracks.get(y).getPath());
        if (wasPlayed.stream().allMatch(val -> val == true))
            Collections.fill(wasPlayed, false);

        return (Track) tracks.get(indexOfTrackInPlaylist);
    }

    @Override
    public ArrayList<Track> getTracks() {
        return tracks;
    }
}
