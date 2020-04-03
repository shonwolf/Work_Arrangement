package ComboBoxes;

import java.util.HashMap;
import javax.swing.JComboBox;
import Data.EmployeesData;
import Data.WorkSchedule;
import Operations.Operation;
import Screens.ChangeEmployeesHourPaymentScreen;
import Screens.ChangeEmployeesPriorityScreen;
import Screens.ManageEmployeeManagerScreen;
import Screens.RemoveEmployeeScreen;

public class OperationsComboBox extends JComboBox<String>{
    // the members
    private static final long serialVersionUID = 1L;
    private HashMap<String, Operation> operationsByKey;
    
    public OperationsComboBox() {
        super();
        String[] operations = {"Select operation", "Reset shifts", "Change employee's priority",
                               "Change employee's hour payment", "Remove employee", "Manage employee manager"};
        for (String operation : operations) {
            addItem(operation);
        }
        operationsByKey = new HashMap<String, Operation>();
        // this anonymous class is created by the interpreter because I create the only method the interface has
        operationsByKey.put("Reset shifts", () -> {
            EmployeesData.getInstance().resetAllEmployeesShifts();
            WorkSchedule.getInstance().makeScheduleUnavailable();
        });
        operationsByKey.put("Change employee's priority", () -> {
            ChangeEmployeesPriorityScreen changeEmployeesPriorityScreen = new ChangeEmployeesPriorityScreen();
            changeEmployeesPriorityScreen.setVisible(true);
        });
        operationsByKey.put("Change employee's hour payment", () -> {
            ChangeEmployeesHourPaymentScreen changeEmployeesHourPaymentScreen = new ChangeEmployeesHourPaymentScreen();
            changeEmployeesHourPaymentScreen.setVisible(true);
        });
        operationsByKey.put("Remove employee", () -> {
            RemoveEmployeeScreen removeEmployeeScreen = new RemoveEmployeeScreen();
            removeEmployeeScreen.setVisible(true);
        });
        operationsByKey.put("Manage employee manager", () -> {
            ManageEmployeeManagerScreen manageEmployeeManagerScreen = new ManageEmployeeManagerScreen();
            manageEmployeeManagerScreen.setVisible(true);
        });
    }
    
    /*
     * This function get operation name and does the operation.
     */
    public void operate(String operationName) {
        Operation operation = operationsByKey.get(operationName);
        if (operation != null) {
            operation.operate();
        }
    }
}
