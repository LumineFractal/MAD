package memento;

public class Originator {
    private SaveInfromation state;

    public Originator() {
        state = new SaveInfromation();
    }

    public void getStateToMemento(Memento memento) {
        //Element element = new Element();
        //element.getElementsByTagName("Playlist").item(0).getTextContent()
        //
        // state.load();
    }

    public Memento saveStateToMemento() {
        Memento memento = new Memento();
        memento.setState(state.createXML());
        return memento;
    }
}
