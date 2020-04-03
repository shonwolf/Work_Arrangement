package Employees;

import java.io.Serializable;
import java.util.HashMap;
import ObserverPattern.Observable;

public class WeeklyShifts extends Observable implements Serializable {
    private static final long serialVersionUID = 1L;
    // the members
    private HashMap<Shift, Boolean> allShifts;
    private String fullName;
    
    // those are all types the shifts that employee can submit
    public static enum Shift {
        SundayMorning, SundayEvening, MondayMorning, MondayEvening, TuesdayMorning, TuesdayEvening,
        WednesdayMorning, WednesdayEvening, ThursdayMorning, ThursdayEvening, FridayMorning, FridayEvening,
        SaturdayMorning, SaturdayEvening
    }
    
    public WeeklyShifts(String givenFullName) {
        super();
        allShifts = new HashMap<Shift, Boolean>();
        initialShiftsToDefault();
        fullName = givenFullName;
    }
    
    /* 
     * This function update the shift that she gets to true.
     */
    public void submitShift(Shift shift) {
        allShifts.replace(shift, true);
        notifyObservers("Employee " + fullName + " submitted the shift " + shift.name());
    }
    
    public String getAllShiftsInStringsRows() {
        StringBuilder shiftsInStringRows = new StringBuilder("");
        for (Shift key : allShifts.keySet()) {
            if (allShifts.get(key)) {
                shiftsInStringRows.append(key.name() + '\n');
            }
        }
        if (!shiftsInStringRows.toString().equals("")) {
            // erase the last '\n'
            shiftsInStringRows.deleteCharAt(shiftsInStringRows.length() - 1);
        }
        return shiftsInStringRows.toString();
    }
    
    /*
     * This function return true if the employee submitted the shift inputed and false if not.
     */
    public boolean isShiftSubmitted(Shift shift) {
        return allShifts.get(shift);
    }
    
    /*
     * This function initialize all the shifts to be not submitted.
     */
    public void initialShiftsToDefault() {
        // initialize all the shifts to be not submitted
        for (Shift shift : Shift.values()) {
            allShifts.put(shift, false);
        }
    }
}
