package Employees;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import javafx.util.Pair;

public class ShiftsTracking implements Serializable {
    // the member
    private static final long serialVersionUID = 1L;
    private ArrayList<Date> startShiftsDates;
    private ArrayList<Date> endShiftsDates;
    
    public ShiftsTracking() {
        startShiftsDates = new ArrayList<Date>();
        endShiftsDates = new ArrayList<Date>();
    }
    
    /*
     * This function adds Date of the time the shift starts
     */
    public void addStartShift() {
        startShiftsDates.add(new Date());
    }
    
    /*
     * This function adds Date of the time the shift ends.
     */
    public void addEndShift() {
        endShiftsDates.add(new Date());
    }
    
    /*
     * This function returns the number of shifts.
     */
    public int getNumOfShifts() {
        return endShiftsDates.size();
    }
    
    /*
     * This function returns the start Date of shift in the index given.
     */
    public Date getStartDateShiftAtIndex(int index) {
        return startShiftsDates.get(index);
    }
    
    /*
     * This function returns the end Date of shift in the index given.
     */
    public Date getEndDateShiftAtIndex(int index) {
        return endShiftsDates.get(index);
    }
    
    /*
     * This function returns an array of all the pairs of Dates of the shifts between start to end. 
     */
    public ArrayList<Pair<Date, Date>> getDatesBetween(Date start, Date end) {
        ArrayList<Pair<Date, Date>> dates = new ArrayList<Pair<Date, Date>>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        for (int i = 0; i < getNumOfShifts(); i++) {
            Date date = startShiftsDates.get(i);
            if (dateFormat.format(date).compareTo(dateFormat.format(start)) >= 0
                && dateFormat.format(date).compareTo(dateFormat.format(end)) <= 0) {
                dates.add(new Pair<Date, Date>(date, endShiftsDates.get(i)));
            }
        }
        return dates;
    }
    
    /*
     * This function return the shift duration in HashMap for time units.
     */
    public HashMap<String, Long> getShiftDuration(Date start, Date end) {
        long diff = end.getTime() - start.getTime();
        HashMap<String, Long> shiftDurationUnits = new HashMap<String, Long>();
        shiftDurationUnits.put("seconds", (diff / 1000) % 60);
        shiftDurationUnits.put("minutes", (diff / (1000 * 60)) % 60);
        shiftDurationUnits.put("hours", (diff / (1000 * 60 * 60)) % 24);
        return shiftDurationUnits;
    }
}