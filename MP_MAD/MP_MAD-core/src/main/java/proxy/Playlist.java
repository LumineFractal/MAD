package proxy;

import iterator.*;
import sources.Track;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Acerek
 */
public class Playlist implements IPlaylist{
    private String name;
    private List<Track> tracks;
    private List<CopyPlaylist> proxyPlaylists;

    public Playlist() {
        //TODO
        this.name = "Playlista X";
        this.tracks = new ArrayList<>();
        this.proxyPlaylists = new ArrayList<>();
    }

    protected void disconnect(){
        //TODO
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public ArrayList<Track> getTracks() {
        return (ArrayList<Track>) tracks;
    }

    @Override
    public Track getTrack(int id) {
        return tracks.get(id);
    }

    @Override
    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }

    @Override
    public void addTrack(Track track) {
        tracks.add(track);
    }

    @Override
    public void removeTrack(Track track) {
        tracks.remove(track);
    }

    @Override
    public Iterator<Track> getIterator(EnumIterator.iterator iterator) {
        switch (iterator) {
            case DEFAULT: {
                return new IteratorDefault(getTracks());
            }
            case REPEATABLE: {
                return new IteratorRepeatable(getTracks());

            }
            case RANDOM: {
                return new IteratorRandom(getTracks());
            }
            case RANDOMREPEATABLE: {
                return new IteratorRandomRepeatable(getTracks());
            }
            default: {
                return new IteratorDefault(getTracks());
            }
        }
    }
    
}
