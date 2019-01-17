package memento;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import player.Player;
import sources.Track;

import java.io.File;

public class SaveInformation {
    private static Player player;
    private Element xmlMemento = new Element("Memento");
    private Document xml = new Document(xmlMemento);
    private String xmlString;

    public String getXmlString() {
        return xmlString;
    }

    public void setXmlString(String xmlString) {
        this.xmlString = xmlString;
    }

    public SaveInformation() {
        player = Player.getInstance();
    }

    public void createXML() {
        XMLOutputter xmlOutput = new XMLOutputter();
        xmlOutput.setFormat(Format.getPrettyFormat());

        Element actualPlaylist = new Element("Playlist");
        actualPlaylist.setText(String.valueOf(player.getActualPlaylist()));

        Element track = new Element("Track");
        if (player.getTrack() != null) {
            track.setText(player.getTrack().getPath());
        }

        Element volume = new Element("Volume");
        volume.setText(String.valueOf(player.getVolume()));

        xmlMemento.addContent(actualPlaylist);
        xmlMemento.addContent(track);
        xmlMemento.addContent(volume);

        xmlString = xmlOutput.outputString(xml);
    }

    public void load() {
        String[] elements = xmlString.split(" !%@-@%! ");
        if (elements.length != 0) {
            player.setActualPlaylist(Integer.parseInt(elements[0]));
            player.setVolume(Double.parseDouble(elements[1]));
            if (elements.length == 3) {
                player.setTrack(new Track(new File(elements[2])));
            }
        }
    }
}
