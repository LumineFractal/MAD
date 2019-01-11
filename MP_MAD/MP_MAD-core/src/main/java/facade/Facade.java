package facade;

import command.CommandAddTrack;
import command.CommandEditPlaylist;
import command.CommandRemovePlaylist;
import command.CommandRemoveTrack;
import iterator.*;
import observer.PlayerObserver;
import player.Player;
import player.PlaylistManager;
import sources.Track;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Facade {
    private static PlaylistManager playlistManager;
    private static Player player;

    public Facade() {
        player = Player.getInstance();
        playlistManager = PlaylistManager.getInstance();
        PlayerObserver observer = new PlayerObserver();
        player.addObserver(observer);
    }

    public static PlaylistManager getPlaylistManager() {
        return playlistManager;
    }

    public static Player getPlayer() {
        return player;
    }

    public void playTrack(int idxPlaylist, Track track, boolean buttonAndDoubleClick) {
        if (idxPlaylist == -1 && track == null) {
            if (player.getTrack() != null) {
                player.play(player.getActualPlaylist(), player.getTrack(), buttonAndDoubleClick);
            } else if (!playlistManager.getPlaylists().isEmpty() && !playlistManager.getPlaylist(player.getActualPlaylist()).getTracks().isEmpty()) {
                player.play(0, playlistManager.getPlaylist(0).getTrack(0), buttonAndDoubleClick);
            }
        } else {
            player.play(idxPlaylist, track, buttonAndDoubleClick);
            new Thread(player).start();
        }
    }

    public void setVolume(double volume) {
        player.setVolume(volume / 100);
        player.getMediaPlayer().setVolume(volume / 100);
    }

    public void createPlaylist(String name) {
        playlistManager.createPlaylist(name);
    }

    public void removePlaylist(String name) {
        for (int i = 0; i < playlistManager.getPlaylists().size(); i++) {
            if (name == playlistManager.getPlaylist(i).getName()) {
                playlistManager.addUndo(new CommandRemovePlaylist(playlistManager.getPlaylist(i)));
                playlistManager.removePlaylist(i);
                break;
            }
        }
    }

    public void addTrack(int idx, Track track) {
        playlistManager.addUndo(new CommandAddTrack(playlistManager.getPlaylist(idx)));
        playlistManager.getPlaylist(idx).addTrack(track);
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
            playlistManager.addUndo(new CommandEditPlaylist(playlistManager.getPlaylist(idx)));
            playlistManager.getPlaylist(idx).setTracks(tracks);
        }
    }

    public void removetrack(int idx, Track track) {
        playlistManager.addUndo(new CommandRemoveTrack(playlistManager.getPlaylist(idx)));
        playlistManager.getPlaylist(idx).removeTrack(track);

    }

    public Iterator<Track> chooseIterator(EnumIterator.iterator iterator, ArrayList<Track> tracks) {
        switch (iterator) {
            case DEFAULT: {
                return new IteratorDefault(tracks);
            }
            case REPEATABLE: {
                return new IteratorRepeatable(tracks);
            }
            case RANDOM: {
                return new IteratorRandom(tracks);
            }
            case RANDOMREPEATABLE: {
                return new IteratorRandomRepeatable(tracks);
            }
            default: {
                return new IteratorDefault(tracks);
            }
        }

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

    public void undo() {

    }

    public void redo() {

    }

}
