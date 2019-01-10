package observer;

import facade.Facade;
import java.util.Observable;
import java.util.Observer;

public class PlayerObserver implements Observer {
    

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("makumba");
    }

}
