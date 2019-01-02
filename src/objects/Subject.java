package objects;

import java.util.ArrayList;
import java.util.List;

public abstract class Subject {
    List<Observer> observers;

    public Subject(){
        observers=new ArrayList<>();
    }

    //Effects: add the observer to the list of observers
    public void addObserver(Observer observer){
        observers.add(observer);
    }

    //Effects: remove the observer from the list of observers
    public void removeObserver(Observer observer){
        observers.remove(observer);
    }

    //Effects: if triggered, notify all the observers in the list
    public abstract int notifyObservers();
}
