package Commands;

import Data.EmployeesData;
import Employees.WeeklyShifts;
import Employees.WeeklyShifts.Shift;

public class SubmitShiftsCommand implements Command {
    
    /*
     * This function will get data on employee's shifts. The data will be in the format:
     * "id#1/0#1/0#1/0#1/0#1/0#1/0#1/0#1/0#1/0#1/0#1/0#1/0#1/0#1/0". The first shift is of
     * Sunday morning, second is for Sunday evening, third is for Monday morning, and so on.
     * Returns information if need.
     * @see Commands.Command#doCommand(java.lang.String)
     */
    @Override
    public String doCommand(String data) {
        String[] splitData = data.split("#");
        String id = splitData[0];
        if (!EmployeesData.getInstance().containEmployee(id)) {
            System.out.println("There is no employee with id" + " " + id);
            return null;
        }
        WeeklyShifts shifts = EmployeesData.getInstance().getThisEmployeesShifts(id);
        Shift[] shiftsArr = Shift.values();
        int len = shiftsArr.length;
        // move on all the shifts that submitted and update this worker shifts
        for (int i = 0; i < len; i++) {
            // if there is [1] in the indexed day than submit this shift for this employee
            if (splitData[i + 1].equals("1")) {
                shifts.submitShift(shiftsArr[i]);
            }
        }
        return null;
    }
}
