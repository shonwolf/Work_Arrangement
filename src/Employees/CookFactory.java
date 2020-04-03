package Employees;

public class CookFactory implements EmployeeFactory{

    /*
     * This function creates Cook and returns him.
     */
    public Employee createEmployee(String id, String firstName, String lastName, int age) {
        return new Cook(id, firstName, lastName, age);
    }
}
