/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package builder;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import sources.Track;

/**
 * @author Daniel
 */
public class BuilderXML implements Builder {

    private Element xmlPlaylists = new Element("Playlists");
    private Document xml = new Document(xmlPlaylists);
    private Element playlist, tracks;

    @Override
    public void addTitle(String title) {
        if (playlist != null) xmlPlaylists.addContent(playlist);
        playlist = new Element("Playlist");
        playlist.setAttribute("name", title);
    }

    @Override
    public void addTrack(Track track) {
        tracks = new Element("Track");
        tracks.setText(track.getPath());
        playlist.addContent(tracks);
    }

    public String getResult() {
        XMLOutputter xmlOutput = new XMLOutputter();
        xmlOutput.setFormat(Format.getPrettyFormat());
        if (playlist != null)
            xmlPlaylists.addContent(playlist);
        return xmlOutput.outputString(xml);
    }

}
