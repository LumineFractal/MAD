/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package builder;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import sources.Track;

/**
 * @author Daniel
 */
public class BuilderJSON implements Builder {

    private JSONObject json = new JSONObject();
    private JSONArray jsonPlaylists = new JSONArray();
    private JSONObject playlist;
    private JSONArray tracks;

    @Override
    public void addTitle(String title) {
        if (playlist != null) jsonPlaylists.add(playlist);
        playlist = new JSONObject();
        tracks = new JSONArray();
        playlist.put("Playlist title", title);
    }

    @Override
    public void addTrack(Track track) {
        tracks.add(track.getPath());
        playlist.put("Tracks", tracks);
    }

    public String getResult() {
        jsonPlaylists.add(playlist);
        json.put("Playlists", jsonPlaylists);
        return json.toJSONString();
    }
}
