package Employees;

import java.util.ArrayList;

public class Manager extends Employee {
    // the members
    private static final long serialVersionUID = 1L;
    private ArrayList<Employee> directEmployees;
    
    public Manager(String givenId, String givenFirstName, String givenLastName, int givenAge) {
        super(givenId, givenFirstName, givenLastName, givenAge);
        directEmployees = new ArrayList<Employee>();
    }
    
    /*
     * This function returns the direct employees of this employee.
     * @see Employees.Employee#getDirectEmployees()
     */
    public ArrayList<Employee> getDirectEmployees() {
        return directEmployees;
    }
    
    /*
     * This function adds direct employee to this employee.
     */
    public void addDirectEmployed(Employee employee) {
        directEmployees.add(employee);
    }
    
    /*
     * This function removes direct employed id from this employee.
     */
    public void removeDirectEmployed(Employee employee) {
        directEmployees.remove(employee);
    }
}
