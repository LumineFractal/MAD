package proxy;

import java.util.Iterator;
import java.util.List;
import sources.Track;

/**
 *
 * @author Acerek
 */
public class CopyPlaylist {
    private Playlist origin;
    private Playlist copy;
    
    protected Playlist copy(){
        //TODO
        return copy;
    }
    
    public String getName(){
        //TODO
        return origin.getName();
    }
    
    public void setName(String name){
        //TODO
        copy.setName(name);
    }
    
    public List<Track> getTrack(){
        //TODO
        return copy.getTracks();
    }
    
    public void addTrack(int id){
        //TODO
        
    }
    
    public void removeTrack(Track track){
        //TODO
        
    }
    
    public Iterator getIterator(int id){
        //TODO
        Iterator iterator = null;
        return iterator;
    }
}
