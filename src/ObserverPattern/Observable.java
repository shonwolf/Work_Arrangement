package ObserverPattern;

import java.util.ArrayList;

public class Observable {
    // the members
    private ArrayList<Observer> observers;
    
    public Observable() {
        observers = new ArrayList<Observer>();
    }
    
    /*
     * This function adds observer to the observers array.
     */
    public void addObserver(Observer o) {
        observers.add(o);
    }
    
    /*
     * This function removes observer from the observers array.
     */
    public void removeObserver(Observer o) {
        observers.remove(o);
    }
    
    /*
     * This function clears all the observers in the array.
     */
    public void clearObservers() {
        observers.clear();
    }
    
    /*
     * This function notify all the observer on the change by the observer and message.
     */
    public void notifyObservers(String msg) {
        for (Observer o : observers) {
            o.update(this, msg);
        }
    }
}
