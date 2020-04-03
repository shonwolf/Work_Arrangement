package Data;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import Data.EmployeesData;
import Employees.Employee;
import Employees.WeeklyShifts;
import Employees.WeeklyShifts.Shift;
import ObserverPattern.Observable;

public class WorkSchedule extends Observable {
    // the members
    private static volatile WorkSchedule instance;
    private volatile boolean ready;
    private String[][] workSchedule;
    
    // those are all types of jobs
    public static enum JobType {
        Waiter, Barista, Cook, Manager
    }
    
    private WorkSchedule() {
        ready = false;
        workSchedule = new String[4][7];
    }
    
    /*
     * This function returns the one instance of WorkScheduleCreator
     */
    public static WorkSchedule getInstance() {
        WorkSchedule result = instance;
        // double-check locking
        if (result == null) {
            synchronized (WorkSchedule.class) {
                result = instance;
                if (result == null) {
                    instance = result = new WorkSchedule();
                }
            }
        }
        return result;
    }
    
    /*
     * This function returns the work schedule.
     */
    public String[][] getWorkSchedule() {
        return workSchedule;
    }
    
    /*
     * This function creates the work schedule on different thread.
     */
    public void createWorkSchedule(Comparator<Employee> comparator, int numEmployeesInShift) {
        new Thread(() -> {
            HashMap<JobType, HashMap<Shift, ArrayList<Employee>>> map = employeesPerShiftPerJobType();
            // loop on all the arrays of employees and sort them by the comparator this function gets
            for (JobType jobType : map.keySet()) {
                for (Shift shift : map.get(jobType).keySet()) {
                    map.get(jobType).get(shift).sort(comparator);
                }
            }
            JobType[] jobTypes = JobType.values();
            Shift[] shifts = Shift.values();
            // fill the work schedule
            for (int i = 0; i < 4; i++) {
                for (int j = 0, k = 0; j < 14 && k < 7; j += 2, k++) {
                    StringBuilder employeesInShift = new StringBuilder("");
                    ArrayList<Employee> morningEmployees = map.get(jobTypes[i]).get(shifts[j]);
                    employeesInShift.append("Morning:" + '\n');
                    addEmployeesToShift(employeesInShift, morningEmployees, numEmployeesInShift);
                    ArrayList<Employee> eveningEmployees = map.get(jobTypes[i]).get(shifts[j + 1]);
                    employeesInShift.append('\n' + "Evening:" + '\n');
                    addEmployeesToShift(employeesInShift, eveningEmployees, numEmployeesInShift);
                    if (!employeesInShift.toString().equals("")) {
                        employeesInShift.deleteCharAt(employeesInShift.length() - 1);
                    }
                    workSchedule[i][k] = employeesInShift.toString();
                }
            }
            makeScheduleAvailable();
        }).start();
    }
    
    /*
     * This function adds the employees from the array to the string StringBuilder of them.
     */
    public void addEmployeesToShift(StringBuilder employeesInShift, ArrayList<Employee> employees,
                                    int numEmployeesInShift) {
        for (int k = 0; k < numEmployeesInShift; k++) {
            if (k < employees.size()) {
                // get employee k in the array of employees that submitted shift j and there jobType is i
                Employee employee = employees.get(k);
                employeesInShift.append(employee.getFirstName() + " " + employee.getLastName() + '\n');
            } else {
                employeesInShift.append("No employee" + '\n');
            }
        }
    }
    
    /*
     * This function initial the arrays of the employees by each job type.
     */
    private void initEmployeesArrays(ArrayList<Employee> waiters, ArrayList<Employee> baristas,
                                     ArrayList<Employee> cooks, ArrayList<Employee> managers) {
        for (Employee employee : EmployeesData.getInstance().getEmployees()) {
            String jobTypeStr = employee.getClass().getName().substring("Employees.".length());
            if (jobTypeStr.equals(JobType.Waiter.name())) {
                waiters.add(employee);
            } else if (jobTypeStr.equals(JobType.Barista.name())) {
                baristas.add(employee);
            } else if (jobTypeStr.equals(JobType.Cook.name())) {
                cooks.add(employee);
            } else if (jobTypeStr.equals(JobType.Manager.name())) {
                managers.add(employee);
            }
        }
    }

    /*
     * This function creates a map between every JobType to map between every Shift to array of employees who
     * submitted that shift and returns it.
     */
    private HashMap<JobType, HashMap<Shift, ArrayList<Employee>>> employeesPerShiftPerJobType() {
        HashMap<JobType, HashMap<Shift, ArrayList<Employee>>> map;
        map = new HashMap<JobType, HashMap<Shift, ArrayList<Employee>>>();
        for (JobType jobType : JobType.values()) {
            map.put(jobType, new HashMap<Shift, ArrayList<Employee>>());
        }
        EmployeesData employeesData = EmployeesData.getInstance();
        HashMap<String, WeeklyShifts> employeesShifts = employeesData.getEmployeesShifts();
        ArrayList<Employee> waiters = new ArrayList<Employee>();
        ArrayList<Employee> baristas = new ArrayList<Employee>();
        ArrayList<Employee> cooks = new ArrayList<Employee>();
        ArrayList<Employee> managers = new ArrayList<Employee>();
        initEmployeesArrays(waiters, baristas, cooks, managers);
        // loop on all the shifts
        for (Shift shift : Shift.values()) {
            ArrayList<Employee> submittedWaiters = new ArrayList<Employee>();
            // loop on all the waiters
            for (Employee employee : waiters) {
                // check if this employee submitted current shift
                if (employeesShifts.get(employee.getId()).isShiftSubmitted(shift)) {
                    submittedWaiters.add(employee);
                }
            }
            map.get(JobType.Waiter).put(shift, submittedWaiters);
            ArrayList<Employee> submittedBaristas = new ArrayList<Employee>();
            // loop on all the baristas
            for (Employee employee : baristas) {
                // check if this employee submitted current shift
                if (employeesShifts.get(employee.getId()).isShiftSubmitted(shift)) {
                    submittedBaristas.add(employee);
                }
            }
            map.get(JobType.Barista).put(shift, submittedBaristas);
            ArrayList<Employee> submittedCooks = new ArrayList<Employee>();
            // loop on all the cooks
            for (Employee employee : cooks) {
                // check if this employee submitted current shift
                if (employeesShifts.get(employee.getId()).isShiftSubmitted(shift)) {
                    submittedCooks.add(employee);
                }
            }
            map.get(JobType.Cook).put(shift, submittedCooks);
            ArrayList<Employee> submittedManagers = new ArrayList<Employee>();
            // loop on all the managers
            for (Employee employee : managers) {
                // check if this employee submitted current shift
                if (employeesShifts.get(employee.getId()).isShiftSubmitted(shift)) {
                    submittedManagers.add(employee);
                }
            }
            map.get(JobType.Manager).put(shift, submittedManagers);
        }
        return map;
    }
    
    /*
     * This function returns true if the work schedule is ready and false otherwise.
     */
    public boolean isWorkScheduleReady() {
        return ready;
    }
    
    /*
     * This function makes the schedule unavailable.
     */
    public void makeScheduleUnavailable() {
        ready = false;
        notifyObservers("");
    }
    
    /*
     * This function makes the schedule available
     */
    public void makeScheduleAvailable() {
        ready = true;
        notifyObservers("");
    }
}
