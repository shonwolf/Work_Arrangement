package Commands;

import Data.EmployeesData;

public class ConfirmPasswordCommand implements Command {
    
    /*
     * This function will confirm password of the employee if it correct. The data will be in the format:
     * "id#password". Returns true if the password is correct and false otherwise.
     * @see Commands.Command#doCommand(java.lang.String)
     */
    @Override
    public String doCommand(String data) {
        String[] splittedData = data.split("#");
        String id = splittedData[0];
        String password = splittedData[1];
        boolean confirmation = EmployeesData.getInstance().confirmPassword(id, password);
        if (confirmation) {
            return "true";
        } else {
            return "false";
        }
    }
}
