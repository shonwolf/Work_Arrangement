package Announcers;

import javax.swing.JButton;
import Data.WorkSchedule;
import ObserverPattern.Observable;
import ObserverPattern.Observer;

public class ButtonAnnouncer implements Observer {
	// the members
    JButton button;
    
    public ButtonAnnouncer(JButton givenButton) {
        button = givenButton;
    }
    
    /*
     * This function deal with the update by the observable.
     * @see Data.Observer#update(Data.Observable, java.lang.String)
     */
    @Override
    public void update(Observable o, String eventArgs) {
        // this announcer observe only on WorkSchedularCreator
        WorkSchedule workSchedularCreator = (WorkSchedule)o;
        if (workSchedularCreator.isWorkScheduleReady()) {
            // make the button enable
            button.setEnabled(true);
        } else {
            // make the button disable
            button.setEnabled(false);
        }
    }
}
