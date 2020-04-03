package Employees;

public interface EmployeeFactory {
    public Employee createEmployee(String id, String firstName, String lastName, int age);
}
