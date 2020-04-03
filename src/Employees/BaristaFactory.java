package Employees;

public class BaristaFactory implements EmployeeFactory {

    /*
     * This function creates Barista and returns him.
     */
    public Employee createEmployee(String id, String firstName, String lastName, int age) {
        return new Barista(id, firstName, lastName, age);
    }
}
