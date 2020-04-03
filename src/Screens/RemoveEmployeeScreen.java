package Screens;


import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import Data.EmployeesData;
import Employees.Employee;

public class RemoveEmployeeScreen extends JFrame {
    // the members
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    RemoveEmployeeScreen frame = new RemoveEmployeeScreen();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public RemoveEmployeeScreen() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(450, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JTextField textField = new JTextField();
        textField.setBounds(34, 104, 200, 20);
        contentPane.add(textField);
        textField.setColumns(10);
        
        JLabel lblEnterEmployeesName = new JLabel("Enter employee's name");
        lblEnterEmployeesName.setFont(new Font("Tahoma", Font.BOLD, 11));
        lblEnterEmployeesName.setBounds(34, 84, 140, 14);
        contentPane.add(lblEnterEmployeesName);
        
        JTextField textField_1 = new JTextField();
        textField_1.setBounds(250, 104, 170, 20);
        contentPane.add(textField_1);
        textField_1.setColumns(10);
        
        JLabel lblRemoveEmployee = new JLabel("Remove Employee");
        lblRemoveEmployee.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblRemoveEmployee.setBounds(106, 11, 200, 52);
        contentPane.add(lblRemoveEmployee);
        
        JLabel removeEmployeeLabel = new JLabel("Employee was being removed...");
        removeEmployeeLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
        removeEmployeeLabel.setBounds(34, 180, 200, 14);
        contentPane.add(removeEmployeeLabel);
        removeEmployeeLabel.setVisible(false);
        
        JLabel noEmployeeLabel = new JLabel("There isn't Employee with the given cradentials...");
        noEmployeeLabel.setFont(new Font("Tahoma", Font.BOLD, 10));
        noEmployeeLabel.setBounds(34, 180, 350, 14);
        contentPane.add(noEmployeeLabel);
        noEmployeeLabel.setVisible(false);
        
        JLabel moreThanOneEmployeeWithEqualNameLabel = new JLabel("There are more than one employees with given name. "
                                                                   + "Please enter ID instead.");
        moreThanOneEmployeeWithEqualNameLabel.setFont(new Font("Tahoma", Font.BOLD, 10));
        moreThanOneEmployeeWithEqualNameLabel.setBounds(20, 180, 404, 14);
        contentPane.add(moreThanOneEmployeeWithEqualNameLabel);
        moreThanOneEmployeeWithEqualNameLabel.setVisible(false);
        
        JButton btnRemoveEmployee = new JButton("Remove employee");
        btnRemoveEmployee.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                EmployeesData employeesData = EmployeesData.getInstance();
                String employeeId = textField_1.getText();
                if (employeeId.equals("")) {
                    String employeeName = textField.getText();
                    String[] splittedName = employeeName.split(" ", 2);
                    String firstName = splittedName[0];
                    String lastName = splittedName[1];
                    employeeId = employeesData.getEmployeesId(firstName, lastName);
                    employeeId = employeesData.getEmployeesId(firstName, lastName);
                    if (employeeId != null) {
                        int numPerformed = 0;
                        for (Employee employee : employeesData.getEmployees()) {
                            if ((firstName + " " + lastName).
                                    equals(employee.getFirstName() + " " + employee.getLastName())) {
                            	numPerformed++;
                            }
                            if (numPerformed > 1) {
                                noEmployeeLabel.setVisible(false);
                                removeEmployeeLabel.setVisible(false);
                                moreThanOneEmployeeWithEqualNameLabel.setVisible(true);
                                return;
                            }
                        }
                    }
                } else {
                    if (!employeesData.containEmployee(employeeId)) {
                        employeeId = null;
                    }
                }
                if (employeeId != null) {
                    employeesData.removeEmployee(employeeId);
                    noEmployeeLabel.setVisible(false);
                    moreThanOneEmployeeWithEqualNameLabel.setVisible(false);
                    removeEmployeeLabel.setVisible(true);
                } else {
                    removeEmployeeLabel.setVisible(false);
                    moreThanOneEmployeeWithEqualNameLabel.setVisible(false);
                    noEmployeeLabel.setVisible(true);
                }
            }
        });
        btnRemoveEmployee.setBounds(34, 146, 140, 23);
        contentPane.add(btnRemoveEmployee);
        
        JLabel lblNewLabel = new JLabel("Or");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
        lblNewLabel.setBounds(225, 84, 28, 14);
        contentPane.add(lblNewLabel);
        
        JLabel lblNewLabel_1 = new JLabel("Enter employee's ID");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 11));
        lblNewLabel_1.setBounds(250, 84, 140, 14);
        contentPane.add(lblNewLabel_1);
    }
}