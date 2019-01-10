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

    JSONObject playlist = new JSONObject();
    JSONArray tracks = new JSONArray();
    String t;

    @Override
    public void addTitle(String title) {
        playlist.put("Playlist title", title);
    }

    @Override
    public void addTrack(Track track) {
        tracks.add(track.getPath());
    }

    public JSONObject getResult() {
        System.out.println("dupda daup " + playlist);
        playlist.put("Tracks", tracks);

        return playlist;
    }
}
