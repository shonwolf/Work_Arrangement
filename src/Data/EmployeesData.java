package Data;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import javax.swing.JTextArea;
import Announcers.TextAreaAnnouncer;
import Employees.Employee;
import Employees.ShiftsTracking;
import Employees.WeeklyShifts;
import ObserverPattern.Observable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/*                  This class is singleton  - saves data on all the employees                  */
public class EmployeesData extends Observable {
    // the members
    private static volatile EmployeesData instance;
    private HashMap<String, Employee> employees;
    private HashMap<String, WeeklyShifts> employeesShifts;
    private HashMap<String, String> employeesPasswords;
    private HashMap<String, ShiftsTracking> employeesShiftsTracking;
    private TextAreaAnnouncer announcer;
    public static final String EMPLOYEES_MAP_FILE_NAME = "employees_map";
    public static final String EMPLOYEES_SHIFTS_MAP_FILE_NAME = "employees_shifts_map";
    public static final String EMPLOYEES_PASSWORDS_MAP_FILE_NAME = "employees_passwords_map";
    public static final String EMPLOYEES_SHIFTS_TRACKING_MAP_FILE_NAME = "employees_shifts_tracking_map";
    
    private EmployeesData() {
        super();
        employees = new HashMap<String, Employee>();
        employeesShifts = new HashMap<String, WeeklyShifts>();
        employeesPasswords = new HashMap<String, String>();
        employeesShiftsTracking = new HashMap<String, ShiftsTracking>();
        announcer = new TextAreaAnnouncer();
        addObserver(announcer);
    }
    
    /*
     * This function returns the one instance of EmployeesData.
     */
    public static EmployeesData getInstance() {
        EmployeesData result = instance;
        // double-check locking
        if (result == null) {
            synchronized (EmployeesData.class) {
                result = instance;
                if (result == null) {
                    result = new EmployeesData();
                    File employeesMapFile = new File(EMPLOYEES_MAP_FILE_NAME);
                    File employeesShiftsMapFile = new File(EMPLOYEES_SHIFTS_MAP_FILE_NAME);
                    File employeesPasswordsMapFile = new File(EMPLOYEES_PASSWORDS_MAP_FILE_NAME);
                    File employeesShiftsTrackingMapFile = new File(EMPLOYEES_SHIFTS_TRACKING_MAP_FILE_NAME);
                    if (employeesMapFile.exists() && employeesShiftsMapFile.exists()
                        && employeesPasswordsMapFile.exists() && employeesShiftsTrackingMapFile.exists()) {
                        result.load(employeesMapFile, employeesShiftsMapFile, employeesPasswordsMapFile,
                                    employeesShiftsTrackingMapFile);
                    }
                    instance = result;
                }
            }
        }
        return result;
    }
    
    /*
     * This function returns array of all the employees.
     */
    synchronized public ArrayList<Employee> getEmployees() {
        return new ArrayList<Employee>(employees.values());
    }
    
    /*
     * This function checks if there is employee with id given.
     */
    synchronized public boolean containEmployee(String id) {
        if (employees.containsKey(id)) {
            return true;
        }
        return false;
    }
    
    /*
     * This function returns the id of the employee with the name that she gets.
     */
    synchronized public String getEmployeesId(String firstName, String lastName) {
        for (Employee employee : getEmployees()) {
            if (employee.getFirstName().equals(firstName) && employee.getLastName().equals(lastName)) {
                return employee.getId();
            }
        }
        return null;
    }
    
    /*
     * This function returns the employee with the given id.
     */
    synchronized public Employee getEmployeeWithId(String id) {
            return employees.get(id);
    }
    
    /*
     * This function adds employee.
     */
    synchronized public void addEmployee(Employee employee, String password) {
        employees.put(employee.getId(), employee);
        WeeklyShifts weeklyShifts = new WeeklyShifts(employee.getFirstName() + " " + employee.getLastName());
        weeklyShifts.addObserver(announcer);
        employeesShifts.put(employee.getId(), weeklyShifts);
        employeesPasswords.put(employee.getId(), password);
        employeesShiftsTracking.put(employee.getId(), new ShiftsTracking());
        notifyObservers("Employee " + employee.getFirstName() + " " + employee.getLastName() + " joined");
    }
    
    /*
     * This function removes employee by his id.
     */
    synchronized public void removeEmployee(String id) {
        if (!employees.containsKey(id)) {
            return;
        }
        String fullName = employees.get(id).getFirstName() + " " + employees.get(id).getLastName();
        employees.remove(id);
        employeesShifts.remove(id);
        employeesPasswords.remove(id);
        employeesShiftsTracking.remove(id);
        notifyObservers("Employee " + fullName + " was being removed");
    }
    
    /*
     * This function returns the WeeklyShifts of the employee by his id.
     */
    synchronized public WeeklyShifts getThisEmployeesShifts(String id) {
        return employeesShifts.get(id);
    }
    
    /*
     * This function returns all the shifts.
     */
    synchronized public ArrayList<WeeklyShifts> getAllShifts() {
        return new ArrayList<WeeklyShifts>(employeesShifts.values());
    }
    
    /*
     * This function returns the map between the id's to the shifts submitted.
     */
    public HashMap<String, WeeklyShifts> getEmployeesShifts() {
        return employeesShifts;
    }
    
    /*
     * This function reset all the shifts for all the employees to be not submitted.
     */
    synchronized public void resetAllEmployeesShifts() {
        for (WeeklyShifts shifts : employeesShifts.values()) {
            shifts.initialShiftsToDefault();
        }
        notifyObservers("All employee's shifts were reset");
    }
    
    /*
     * This function returns the employees ordered by priority
     */
    synchronized public ArrayList<Employee> getOrderedEmployees() {
        ArrayList<Employee> orderedEmployees = new ArrayList<Employee>(employees.values());
        orderedEmployees.sort(new Comparator<Employee>() {
            @Override
            public int compare(Employee e1, Employee e2) {
                return e1.getPriority() - e2.getPriority();
            }
        });
        return orderedEmployees;
    }
    
    /*
     * This function sets the text area to the one she gets.
     */
    synchronized public void setJTextArea(JTextArea givenTextArea) {
        announcer.setJTextArea(givenTextArea);
    }
    
    /*
     * This function confirm if the given password of the given employee's id is correct.
     */
    synchronized public boolean confirmPassword(String id, String passwordToCheck) {
        String password = employeesPasswords.get(id);
        if (password != null) {
            return password.equals(passwordToCheck);
        }
        return false;
    }
    
    /*
     * This function adds Date of the time the shift starts.
     */
    synchronized public void addStartShift(String id) {
        ShiftsTracking shiftsTracking = employeesShiftsTracking.get(id);
        if (shiftsTracking != null) {
            shiftsTracking.addStartShift();
        }
    }
    
    /*
     * This function adds Date of the time the shift ends.
     */
    synchronized public void addEndShift(String id) {
        ShiftsTracking shiftsTracking = employeesShiftsTracking.get(id);
        if (shiftsTracking != null) {
            shiftsTracking.addEndShift();
        }
    }
    
    /*
     * This function returns the ShiftsTracking of the employee with the given id.
     */
    synchronized public ShiftsTracking getShiftsTracking(String id) {
        return employeesShiftsTracking.get(id);
    }
    
    /*
     * This function saves members that need to be saved between executions to files. 
     */
    synchronized public void save() {
        File employeesMapFile = new File(EMPLOYEES_MAP_FILE_NAME);
        File employeesShiftsMapFile = new File(EMPLOYEES_SHIFTS_MAP_FILE_NAME);
        File employeesPasswordsMapFile = new File(EMPLOYEES_PASSWORDS_MAP_FILE_NAME);
        File employeesShiftsTrackingMapFile = new File(EMPLOYEES_SHIFTS_TRACKING_MAP_FILE_NAME);
        // if there is such file than do not create one, but delete it's content, and if there is not, create him
        try {
            if (!employeesMapFile.createNewFile()) {
                employeesMapFile.delete();
            }
            if (!employeesShiftsMapFile.createNewFile()) {
                employeesShiftsMapFile.delete();
            }
            if (!employeesPasswordsMapFile.createNewFile()) {
                employeesPasswordsMapFile.delete();
            }
            if (!employeesShiftsTrackingMapFile.createNewFile()) {
                employeesShiftsTrackingMapFile.delete();
            }
        } catch (IOException e) {
            System.err.println("There was problem with create the file");
        }
        ObjectOutputStream out1 = null;
        ObjectOutputStream out2 = null;
        ObjectOutputStream out3 = null;
        ObjectOutputStream out4 = null;
        try {
            out1 = new ObjectOutputStream(new FileOutputStream(employeesMapFile));
            out1.writeObject(employees);
            out2 = new ObjectOutputStream(new FileOutputStream(employeesShiftsMapFile));
            out2.writeObject(employeesShifts);
            out3 = new ObjectOutputStream(new FileOutputStream(employeesPasswordsMapFile));
            out3.writeObject(employeesPasswords);
            out4 = new ObjectOutputStream(new FileOutputStream(employeesShiftsTrackingMapFile));
            out4.writeObject(employeesShiftsTracking);
        } catch (IOException e) {
            // if the saving was had Error
            System.err.println("Error in saving object");
            e.printStackTrace();
        } finally {
            try {
                if (out1 != null) {
                    out1.close();
                }
                if (out2 != null) {
                    out2.close();
                }
                if (out3 != null) {
                    out3.close();
                }
                if (out4 != null) {
                    out4.close();
                }
            } catch (IOException e) {
                System.err.println("Error in closing file");
            }
        }
    }
    
    /*
     * This function loads members that need to be loaded between executions.
     */
    @SuppressWarnings("unchecked")
    synchronized public void load(File employeesMapFile, File employeesShiftsMapFile, File employeesPasswordsMapFile,
                                  File employeesShiftsTrackingMapFile) {
        ObjectInputStream in1 = null;
        ObjectInputStream in2 = null;
        ObjectInputStream in3 = null;
        ObjectInputStream in4 = null;
        try {
            in1 = new ObjectInputStream(new FileInputStream(employeesMapFile));
            employees = (HashMap<String, Employee>)in1.readObject();
            in2 = new ObjectInputStream(new FileInputStream(employeesShiftsMapFile));
            employeesShifts = (HashMap<String, WeeklyShifts>)in2.readObject();
            for (WeeklyShifts shifts : employeesShifts.values()) {
                shifts.clearObservers();
                shifts.addObserver(announcer);
            }
            in3 = new ObjectInputStream(new FileInputStream(employeesPasswordsMapFile));
            employeesPasswords = (HashMap<String, String>)in3.readObject();
            in4 = new ObjectInputStream(new FileInputStream(employeesShiftsTrackingMapFile));
            employeesShiftsTracking = (HashMap<String, ShiftsTracking>)in4.readObject();
        } catch (FileNotFoundException e) {
            // can't find file to open
            System.err.println("Unable to find file");
        } catch (ClassNotFoundException e) {
            // the class in the stream is unknown to the JVM
            System.err.println("Unable to find class for object in file");
        } catch (IOException e) {
            // Some other problem
            System.err.println("Error in reading object");
        } finally {
            try {
                if (in1 != null) {
                    in1.close();
                }
                if (in2 != null) {
                    in2.close();
                }
                if (in3 != null) {
                    in3.close();
                }
                if (in4 != null) {
                    in3.close();
                }
            } catch (IOException e) {
                System.err.println("Error in closing file");
            }
        }
    }
}