package iterator;

import sources.Track;

import java.util.ArrayList;

public class IteratorRepeatable implements TrackIterator<Track> {
    private ArrayList<Track> tracks;
    private int indexOfTrackInPlaylist = -1;

    public IteratorRepeatable(ArrayList tracks) {
        this.tracks = tracks;
    }

    @Override
    public void setIndexOfTrackInPlaylist(int indexOfTrackInPlaylist) {
        this.indexOfTrackInPlaylist = indexOfTrackInPlaylist;
        System.out.println(indexOfTrackInPlaylist);
    }
    
    @Override
    public boolean hasNext() {
        return true;
    }

    @Override
    public Track next() {
        indexOfTrackInPlaylist++;
        if (tracks.size() == indexOfTrackInPlaylist) {
            indexOfTrackInPlaylist = 0;
        }
        System.out.println(indexOfTrackInPlaylist);
        return (Track) tracks.get(indexOfTrackInPlaylist);
    }

    @Override
    public ArrayList<Track> getTracks() {
        return tracks;
    }
}
