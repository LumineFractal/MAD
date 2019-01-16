package memento;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import player.Player;

public class SaveInfromation {
    private static Player player;
    private Element xmlMemento = new Element("Memento");
    private Document xml = new Document(xmlMemento);

    public SaveInfromation() {
        player = Player.getInstance();
    }

    public String createXML() {
        XMLOutputter xmlOutput = new XMLOutputter();
        xmlOutput.setFormat(Format.getPrettyFormat());

        Element actualPlaylist = new Element("Playlist");
        actualPlaylist.setText(String.valueOf(player.getActualPlaylist()));

        Element track = new Element("Track");
        track.setText(player.getTrack().getPath());

        Element volume = new Element("Volume");
        volume.setText(String.valueOf(player.getVolume()));

        xmlMemento.addContent(actualPlaylist);
        xmlMemento.addContent(track);
        xmlMemento.addContent(volume);

        return xmlOutput.outputString(xml);
    }
}
