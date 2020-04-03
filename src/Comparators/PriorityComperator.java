package Comparators;

import java.util.Comparator;
import Employees.Employee;

public class PriorityComperator implements Comparator<Employee>{
    
    /*
     * This function compares between two employees by there seniority.
     */
    @Override
    public int compare(Employee e1, Employee e2) {
        return e1.getPriority() - e2.getPriority();
    }
}
