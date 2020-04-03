package Commands;

import java.util.Date;
import java.util.HashMap;
import Commands.Command;
import Data.EmployeesData;
import Employees.ShiftsTracking;

public class StartEndShiftCommand implements Command {
    
    /*
     * This function gets data on start or end shift of employee. She checks his shift time.
     * The data will be in the format: "id#1/0". 1 is to start shift and 0 is to end her.
     * @see Commands.Command#doCommand(java.lang.String)
     */
    @Override
    public String doCommand(String data) {
        String[] splittedData = data.split("#");
        String id = splittedData[0];
        String startEnd = splittedData[1];
        EmployeesData employeesData = EmployeesData.getInstance();
        if (startEnd.equals("1")) {
            employeesData.addStartShift(id);
            return null;
        } else {
            employeesData.addEndShift(id);
            ShiftsTracking shiftsTracking = employeesData.getShiftsTracking(id);
            String shiftDuration = "";
            if (shiftsTracking != null) {
                Date start = shiftsTracking.getStartDateShiftAtIndex(shiftsTracking.getNumOfShifts() - 1);
                Date end = shiftsTracking.getEndDateShiftAtIndex(shiftsTracking.getNumOfShifts() - 1);
                HashMap<String, Long> shiftDurationUnits = shiftsTracking.getShiftDuration(start, end);
                shiftDuration = Long.toString(shiftDurationUnits.get("hours")) + "h, "
                                + Long.toString(shiftDurationUnits.get("minutes")) + 'm';
            }
            return shiftDuration;
        }
    }
}
