/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package builder;

import sources.Track;

/**
 * @author Daniel
 */
public interface Builder {
    void addTitle(String title);

    void addTrack(Track track);
}
