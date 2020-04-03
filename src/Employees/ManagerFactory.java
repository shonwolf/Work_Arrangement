package Employees;

public class ManagerFactory implements EmployeeFactory {

    /*
     * This function creates Manager and returns him.
     */
    public Employee createEmployee(String id, String firstName, String lastName, int age) {
        return new Manager(id, firstName, lastName, age);
    }
}
