package iterator;

import sources.Track;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class IteratorRandom implements TrackIterator<Track> {
    private ArrayList<Track> tracks;
    private ArrayList<Boolean> wasPlayed;
    private Random generator = new Random();
    private int indexOfTrackInPlaylist = -1;

    public IteratorRandom(ArrayList tracks) {
        this.tracks = tracks;
        wasPlayed = new ArrayList<>(tracks);
        Collections.fill(wasPlayed, false);
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
        Collections.fill(wasPlayed, false);
        setList(indexOfTrackInPlaylist);
    }

    @Override
    public boolean hasNext() {
        if (wasPlayed.size() == tracks.size()) {
            return !wasPlayed.stream().allMatch(val -> val == true);
        } else {
            return true;
        }
    }

    @Override
    public Track next() {
        createList();
        indexOfTrackInPlaylist = generator.nextInt(tracks.size());
        while (wasPlayed.get(indexOfTrackInPlaylist)) {
            indexOfTrackInPlaylist = generator.nextInt(tracks.size());
        }
        setList(indexOfTrackInPlaylist);
        System.out.println(indexOfTrackInPlaylist + tracks.get(indexOfTrackInPlaylist).getTitle());
        return (Track) tracks.get(indexOfTrackInPlaylist);
    }

    @Override
    public ArrayList<Track> getTracks() {
       return tracks;
    }
}
