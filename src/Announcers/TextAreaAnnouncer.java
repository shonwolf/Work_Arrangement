package Announcers;

import javax.swing.JTextArea;
import ObserverPattern.Observable;
import ObserverPattern.Observer;

public class TextAreaAnnouncer implements Observer {
    // the members
    private JTextArea textArea;
    
    public void setJTextArea(JTextArea givenTextArea) {
        textArea = givenTextArea;
    }
    
    /*
     * This function deal with the update by the observable.
     * @see Data.Observer#update(Data.Observable, java.lang.String)
     */
    @Override
    public void update(Observable o, String eventArgs) {
        if (textArea != null) {
            textArea.append(eventArgs + "\n");
        }
    }
}
