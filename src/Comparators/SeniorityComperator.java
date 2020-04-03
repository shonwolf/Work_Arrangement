package Comparators;

import java.util.Comparator;
import Employees.Employee;

public class SeniorityComperator implements Comparator<Employee>{
    
    /*
     * This function compares between two employees by there seniority, higher seniority comes first.
     */
    @Override
    public int compare(Employee e1, Employee e2) {
        return e1.getStartWorkDate().compareTo(e2.getStartWorkDate());
    }
}
