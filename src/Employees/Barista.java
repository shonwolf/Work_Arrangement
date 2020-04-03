package Employees;

import java.util.ArrayList;

public class Barista extends Employee {
    // the members
    private static final long serialVersionUID = 1L;

    public Barista(String givedId, String givenFirstName, String givenLastName, int givenAge) {
        super(givedId, givenFirstName, givenLastName, givenAge);
    }

    /*
     * This function returns the direct employees of this employee, and its an empty array because the barista does
     * not have employees under him.
     * @see Employees.Employee#getDirectEmployees()
     */
    public ArrayList<Employee> getDirectEmployees() {
        return new ArrayList<Employee>();
    }
}
