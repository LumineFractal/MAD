package facade;

import command.*;
import javafx.util.Duration;
import org.json.simple.parser.ParseException;
import player.Player;
import player.PlaylistManager;
import proxy.IPlaylist;
import proxy.Playlist;
import sources.Track;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Facade {
    private static PlaylistManager playlistManager;
    private static Player player;
    private Thread thread;

    public Facade() {
        player = Player.getInstance();
        playlistManager = PlaylistManager.getInstance();
        player.addObserver(playlistManager);
    }

    public void playTrack(int idxPlaylist, Track track, boolean buttonAndDoubleClick) {

        if (idxPlaylist == -1 && track == null) {

            if (player.getTrack() != null) {
                player.play(player.getActualPlaylist(), player.getTrack(), buttonAndDoubleClick);
            } else if (!playlistManager.getPlaylists().isEmpty() && !playlistManager.getPlaylist(player.getActualPlaylist()).getTracks().isEmpty()) {
                player.play(0, playlistManager.getPlaylist(0).getTrack(0), buttonAndDoubleClick);
            }
            playlistManager.setTrackInIterator(track);
        } else {
            player.play(idxPlaylist, track, buttonAndDoubleClick);
            playlistManager.setTrackInIterator(track);
        }
        if(thread==null || !thread.isAlive()){
                thread = new Thread(player);
                thread.setDaemon(true);
                thread.start();
        }
    }

    public void setVolume(double volume) {
        player.setVolume(volume / 100);
        player.getMediaPlayer().setVolume(volume / 100);
    }

    public void createPlaylist(String name) {
        playlistManager.addUndo(new CommandCreatePlaylist(name));
        playlistManager.createPlaylist(name);
        try {
            playlistManager.createJSON();
            playlistManager.createXML();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removePlaylist(String name) {
        for (int i = 0; i < playlistManager.getPlaylists().size(); i++) {
            if (name.equals(playlistManager.getPlaylist(i).getName())) {
                playlistManager.addUndo(new CommandRemovePlaylist(playlistManager.getPlaylist(i), i));
                playlistManager.removePlaylist(i);
                try {
                    playlistManager.createJSON();
                    playlistManager.createXML();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    public void copyPlaylist(String name) {
        // playlistManager.createPlaylist();
    }

    public void addTrack(int idx, Track track) {
        playlistManager.addUndo(new CommandAddTrack(playlistManager.getPlaylist(idx), track));
        playlistManager.getPlaylist(idx).addTrack(track);
        try {
            playlistManager.createJSON();
            playlistManager.createXML();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void editPlaylist(int idx, List<Track> tracks) {
        boolean isSameList = true;

        for (int i = 0; i < playlistManager.getPlaylist(idx).getTracks().size(); i++) {
            if (!tracks.get(i).equals(playlistManager.getPlaylist(idx).getTrack(i))) {
                isSameList = false;
                break;
            }
        }
        if (!isSameList) {
            playlistManager.addUndo(new CommandEditPlaylist(playlistManager.getPlaylist(idx), (ArrayList) tracks));
            playlistManager.getPlaylist(idx).setTracks(tracks);
        }
        try {
            playlistManager.createJSON();
            playlistManager.createXML();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removetrack(int idx, Track track) {
        playlistManager.addUndo(new CommandRemoveTrack(playlistManager.getPlaylist(idx), track));
        playlistManager.getPlaylist(idx).removeTrack(track);
        try {
            playlistManager.createJSON();
            playlistManager.createXML();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    
    public void setTrackInIterator(Track track){
        playlistManager.setTrackInIterator(track);
    }

    public void nextTrack() {
        if (player.getTrack() == null) {

        } else {
            int currentTrackIndex = playlistManager.getPlaylist(player.getActualPlaylist())
                    .getTracks().lastIndexOf(player.getTrack());
            if (playlistManager.getPlaylist(player.getActualPlaylist()).getTracks().size() - 1 == currentTrackIndex) {
                playTrack(player.getActualPlaylist(), playlistManager
                        .getPlaylist(player.getActualPlaylist()).getTracks().get(0), false);
            } else {
                playTrack(player.getActualPlaylist(), playlistManager
                        .getPlaylist(player.getActualPlaylist()).getTracks().get(currentTrackIndex + 1), false);
            }
        }

    }

    public void previousTrack() {
        if (player.getTrack() == null) {
        } else {
            int currentTrackIndex = playlistManager.getPlaylist(player.getActualPlaylist())
                    .getTracks().lastIndexOf(player.getTrack());
            if (0 == currentTrackIndex) {
                playTrack(player.getActualPlaylist(), playlistManager
                        .getPlaylist(player.getActualPlaylist()).getTracks().get(playlistManager.getPlaylist(player.getActualPlaylist())
                                .getTracks().size() - 1), false);
            } else {
                playTrack(player.getActualPlaylist(), playlistManager
                        .getPlaylist(player.getActualPlaylist()).getTracks().get(currentTrackIndex - 1), false);
            }
        }
    }

    public String namePlaylistUnique(String name) {
        int numberFix = 0;
        String nameFix = name;
        for (int i = 0; i < playlistManager.getPlaylists().size(); i++) {
            if (playlistManager.getPlaylists().get(i).getName().equals(nameFix)) {
                nameFix = name;
                nameFix = nameFix.concat(String.valueOf(numberFix));
                numberFix++;
                i = -1;
            }
        }

        return nameFix;
    }
    
    public String timeConverter(double timeMilis){
        Duration duration = new Duration(timeMilis);
        StringBuilder sb = new StringBuilder();
        int timeSeconds = (int) timeMilis/1000;
        if(timeSeconds>3600){
            timeSeconds-=(int)duration.toHours()*3600;
            sb.append((int)duration.toHours());
            duration = new Duration(timeSeconds*1000);
            sb.append(":");
        }
        if(timeSeconds>599){
            timeSeconds-=(int)duration.toMinutes()*60;
            sb.append((int)duration.toMinutes());
            duration = new Duration(timeSeconds*1000);
            sb.append(":");
        }else if(timeSeconds>59){
            timeSeconds-=(int)duration.toMinutes()*60;
            sb.append("0");
            sb.append((int)duration.toMinutes());
            duration = new Duration(timeSeconds*1000);
            sb.append(":");
        }
        else if(timeMilis>3599999){
            duration = new Duration(timeSeconds*1000);
            sb.append("00:");
        }
        else{
            duration = new Duration(timeSeconds*1000);
            sb.append("0:");
        }
        if(timeSeconds<10){
            sb.append("0");
        }
        sb.append((int)duration.toSeconds());
        
        return sb.toString();
    }

    public void undo() {
        if(playlistManager.isUndoAvailable()){
            playlistManager.undo();
            try {
                playlistManager.createJSON();
                playlistManager.createXML();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void redo() {
        if(playlistManager.isRedoAvailable()){
            playlistManager.redo();
            try {
                playlistManager.createJSON();
                playlistManager.createXML();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public int getIndexPlaylist(String name) {
        for (int i = 0; i < playlistManager.getPlaylists().size(); i++) {
            if (playlistManager.getPlaylist(i).getName().equals(name))
                return i;
        }
        return -1;
    }

    public IPlaylist getLastPlaylist() {
        return playlistManager.getPlaylist(playlistManager.getPlaylists().size() - 1);
    }

    public IPlaylist getPlaylist(String name) {
        int i = getIndexPlaylist(name);
        if (i == -1) {
            return null;
        }

        return playlistManager.getPlaylist(getIndexPlaylist(name));
    }

    public IPlaylist getPlaylist(int idx) {
        return playlistManager.getPlaylist(idx);
    }

    public boolean isPlaying() {
        return player.isPlaying();
    }

    public void setNameIterator(boolean isActive, boolean RandomOrRepeatable) {
        playlistManager.setNameIterator(isActive, RandomOrRepeatable);
    }

    public List<IPlaylist> getPlaylists() {
        return playlistManager.getPlaylists();
    }

    public boolean isUndoAvailable() {
        return playlistManager.isUndoAvailable();
    }

    public boolean isRedoAvailable() {
        return playlistManager.isRedoAvailable();
    }

    public void setNamePlaylist(String olderName, String newName) {
        String namePlaylist = olderName;
        if (getPlaylist(olderName) == null) {
            namePlaylist = olderName.substring(0, namePlaylist.length() - 5);
            Playlist playlist = (Playlist) getPlaylist(namePlaylist);
            playlistManager.addUndo(new CommandEditNamePlaylist(playlist.getProxyPlaylists().get(0), newName));
            playlist.getProxyPlaylists().get(0).setName(newName);
        }else{
            playlistManager.addUndo(new CommandEditNamePlaylist(getPlaylist(olderName), newName));
            getPlaylist(olderName).setName(newName);
        }
        try {
            playlistManager.createJSON();
            playlistManager.createXML();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void loadFromXML(){
        playlistManager.loadFromXML();
    }
    
    public void loadFromJSON() throws FileNotFoundException, ParseException{
        playlistManager.loadFromJSON();
    }
    
    public int getActualPlaylist(){
        return player.getActualPlaylist();
    }

    public int getIndexOfTrack(){
        for(int i = 0 ;i<playlistManager.getPlaylists().get(player.getActualPlaylist()).getTracks().size(); i++){
            if(playlistManager.getPlaylists().get(player.getActualPlaylist()).getTrack(i).getPath().equals(player.getTrack().getPath())){
                return i;
            }
        }
        return -1;
    }
    
    public void selectedTrack(){
        playlistManager.getPlaylist(player.getActualPlaylist()).getTrack(getIndexOfTrack()).setIsPlaying(" >");
    }
}
