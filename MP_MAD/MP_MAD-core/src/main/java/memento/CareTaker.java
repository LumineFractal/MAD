package memento;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CareTaker {
    private Memento memento;

    public CareTaker() {
        this.memento = new Memento();
    }

    public void setMemento(Memento memento) {
        this.memento = memento;
    }

    public Memento getMemento() {
        return memento;
    }

    public void save() throws IOException {
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        File f = new File(s + "/memento");
        if (!f.exists() || !f.isDirectory()) {
            new File("memento").mkdirs();
        }
        File ww = new File(s + "/memento", "memento.xml");
        ww.createNewFile();
        try (FileWriter writer = new FileWriter(ww)) {
            writer.write(memento.getState());
        }
    }

    public void get() throws ParserConfigurationException, IOException, SAXException {
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        File read = new File(s + "/memento/memento.xml");
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(read);
        doc.getDocumentElement().normalize();
        NodeList node = doc.getElementsByTagName("Memento");
        Node save = node.item(0);

        Element elm = (Element) save;

        String state = elm.getElementsByTagName("Playlist").item(0).getTextContent() + " " + elm.getElementsByTagName("Volume").item(0).getTextContent() + " " + elm.getElementsByTagName("Track").item(0).getTextContent();

        memento.setState(state);
    }
}
