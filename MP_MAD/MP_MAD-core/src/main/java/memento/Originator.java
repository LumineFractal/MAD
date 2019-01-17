package memento;

public class Originator {
    private SaveInformation state;

    public Originator() {
        state = new SaveInformation();
    }

    public void getStateToMemento(Memento memento) {
        state.setXmlString(memento.getState());
        state.load();
    }

    public Memento saveStateToMemento() {
        state.createXML();
        return new Memento(state.getXmlString());
    }
}
