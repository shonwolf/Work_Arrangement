package Employees;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.ArrayList;

public abstract class Employee implements Serializable {
    // the members
    private static final long serialVersionUID = 1L;
    protected String id;
    protected String firstName;
    protected String lastName;
    protected int age;
    protected int priority;
    protected Date startWorkDate;
    protected double hourPayment;
    
    public Employee(String givedId, String givenFirstName, String givenLastName, int givenAge) {
        id = givedId;
        firstName = givenFirstName;
        lastName = givenLastName;
        age = givenAge;
        // priority of every employee starts from 5 and can get better by changing it, 1 is the best priority
        priority = 5;
        startWorkDate = new Date();
        hourPayment = 30.0;
    }
    
    /*
     * This function returns the id of the employee.
     */
    public String getId() {
        return id;
    }
    
    /*
     * This function returns the first name of the employee.
     */
    public String getFirstName() {
        return firstName;
    }
    
    /*
     * This function returns the last name of the employee.
     */
    public String getLastName() {
        return lastName;
    }
    
    /*
     * This function returns the age of the employee.
     */
    public int getAge() {
        return age;
    }
    
    /*
     * This function returns the direct employees of this employee.
     */
    public abstract ArrayList<Employee> getDirectEmployees();
    
    /*
     * This function returns the priority of this employee.
     */
    public int getPriority() {
        return priority;
    }
    
    /*
     * This function sets the priority of this employee.
     */
    public void setPriority(int newPriority) {
        priority = newPriority;
    }
    
    /*
     * This function returns the start work date of this employee.
     */
    public Date getStartWorkDate() {
        return startWorkDate;
    }
    
    /*
     * This function calculate the seniority of this employee from the day he started working until now.
     */
    public HashMap<String, Long> getSeniority() {
        Date currentDate = new Date();
        long diff = currentDate.getTime() - startWorkDate.getTime();
        HashMap<String, Long> employeeSeniorityUnits = new HashMap<String, Long>();
        employeeSeniorityUnits.put("days", (diff / (1000l * 60 * 60 * 24)) % 30);
        employeeSeniorityUnits.put("months", (diff / (1000l * 60 * 60 * 24 * 30)) % 12);
        employeeSeniorityUnits.put("years", (diff / (1000l * 60 * 60 * 24 * 365)));
        return employeeSeniorityUnits;
    }
    
    /*
     * This function returns the hour payment of this employee.
     */
    public double getHourPayment() {
        return hourPayment;
    }
    
    /*
     * This function sets the hour payment of this employee.
     */
    public void setHourPayment(double newHourPayment) {
        hourPayment = newHourPayment;
    }
    
    /*
     * This function compare between this employee and other employee.
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Employee)) {
            return false;
        }
        return id.equals(((Employee)other).id);
    }
}