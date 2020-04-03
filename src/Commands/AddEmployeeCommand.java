package Commands;

import java.util.HashMap;
import Data.EmployeesData;
import Employees.BaristaFactory;
import Employees.CookFactory;
import Employees.Employee;
import Employees.EmployeeFactory;
import Employees.ManagerFactory;
import Employees.WaiterFactory;

public class AddEmployeeCommand implements Command {
    // the members
    private HashMap<String, EmployeeFactory> employeeByKey;
    
    public AddEmployeeCommand() {
        employeeByKey = new HashMap<String, EmployeeFactory>();
        employeeByKey.put("Waiter", new WaiterFactory());
        employeeByKey.put("Barista", new BaristaFactory());
        employeeByKey.put("Cook", new CookFactory());
        employeeByKey.put("Manager", new ManagerFactory());
    }
    
    /*
     * This function gets data on new employee to create. The data will be in the format:
     * "id#password#first name#last name#age#job type". Returns information if there is.
     * @see Commands.Command#doCommand(java.lang.String)
     */
    @Override
    public String doCommand(String data) {
        String[] splitData = data.split("#");
        String id = splitData[0];
        String password = splitData[1];
        String firstName = splitData[2];
        String lastName = splitData[3];
        int age = Integer.parseInt(splitData[4]);
        String jobType = splitData[5];
        EmployeeFactory employeeFactory = employeeByKey.get(jobType);
        // if there is no such job type than prints it
        if (employeeFactory == null) {
            System.out.println("There is no such job type:   " + jobType);
            return null;
        }
        Employee employee = employeeFactory.createEmployee(id, firstName, lastName, age);
        // save this employee
        EmployeesData.getInstance().addEmployee(employee, password);
        return null;
    }
}
