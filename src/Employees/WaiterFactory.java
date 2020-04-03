package Employees;

public class WaiterFactory implements EmployeeFactory {

    /*
     * This function creates Waiter and returns him.
     */
    public Employee createEmployee(String id, String firstName, String lastName, int age) {
        return new Waiter(id, firstName, lastName, age);
    }
}
