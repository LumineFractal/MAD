/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package builder;

import org.w3c.dom.Document;
import sources.Track;

/**
 * @author Daniel
 */
public class BuilderXML implements Builder {

    String title;
    Track track;
    Document document;
    //Element element;

    @Override
    public void addTitle(String title) {
        // element =
        document.appendChild(document.createElement("Tracks"));
    }

    @Override
    public void addTrack(Track track) {
        document.appendChild(document.createElement(track.getPath()));
    }

    public Document getResult() {
        System.out.println("dupda daup" + document);

        return document;
    }

}
