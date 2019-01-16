package proxy;

import iterator.*;
import sources.Track;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Acerek
 */
public class Playlist implements IPlaylist{
    private String name;
    private List<Track> tracks;
    private LinkedList<CopyPlaylist> proxyPlaylists;

    public Playlist() {
        //TODO
        this.name = "Playlista X";
        this.tracks = new ArrayList<>();
        this.proxyPlaylists = new LinkedList<>();
    }

    protected void disconnect(){
        for(CopyPlaylist copy : proxyPlaylists){
            copy.copy();
        }
        proxyPlaylists.clear();
    }
    
    public IPlaylist copy(){
        CopyPlaylist copy = new CopyPlaylist(this);
        proxyPlaylists.addLast(copy);
        return copy;
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
        if(!proxyPlaylists.isEmpty()){
            disconnect();
        }
    }

    @Override
    public void addTrack(Track track) {
        tracks.add(track);
        if(!proxyPlaylists.isEmpty()){
            disconnect();
        }
    }

    @Override
    public void removeTrack(Track track) {
        tracks.remove(track);
        if(!proxyPlaylists.isEmpty()){
            disconnect();
        }
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
