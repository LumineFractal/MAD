package memento;

public class Originator {
    private SaveInfromation state;

    public Originator() {
        state = new SaveInfromation();
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
