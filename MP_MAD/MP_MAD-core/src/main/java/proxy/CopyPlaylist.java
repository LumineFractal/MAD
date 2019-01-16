package proxy;

import iterator.EnumIterator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import sources.Track;

/**
 *
 * @author Acerek
 */
public class CopyPlaylist implements IPlaylist{
    private Playlist origin;
    private Playlist copy;
    
    
    public CopyPlaylist(Playlist oryginal) {
        this.origin = oryginal;
    }
    
    protected void copy(){
        if(copy != null){
            return;
        }
        copy = new Playlist();
        for(int i = 0; i < origin.getTracks().size(); i++){
            copy.addTrack(origin.getTrack(i));
        }
        origin = null;
    }
    
    @Override
    public String getName(){
        if(copy == null){
            return origin.getName();
        }
        else{
            return copy.getName();
        }
    }
    
    @Override
    public void setName(String name){
        if(copy == null){
            origin.disconnect();
        }
        copy.setName(name);
    }
    
    @Override
    public ArrayList<Track> getTracks() {
        if(copy == null){
            return origin.getTracks();
        }
        else{
            return copy.getTracks();
        }
    }

    @Override
    public Track getTrack(int id) {
        if(copy == null){
            return origin.getTrack(id);
        }
        else{
            return copy.getTrack(id);
        }
    }

    @Override
    public void addTrack(Track track) {
         if(copy == null){
            origin.disconnect();
        }
        copy.addTrack(track);
    }

    @Override
    public void setTracks(List<Track> tracks) {
        if(copy == null){
            origin.disconnect();
        }
        copy.setTracks(tracks);
    }
    
    @Override
    public void removeTrack(Track track){
        if(copy == null){
            origin.disconnect();
        }
        copy.removeTrack(track);
    }

    @Override
    public Iterator<Track> getIterator(EnumIterator.iterator iterator) {
        if(copy == null){
            return origin.getIterator(iterator);
        }
        else{
            return copy.getIterator(iterator);
        }
    }
}
