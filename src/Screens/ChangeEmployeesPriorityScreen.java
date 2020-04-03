package Screens;

import java.awt.EventQueue;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import ComboBoxes.PrioritiesComboBox;
import Data.EmployeesData;
import Employees.Employee;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ChangeEmployeesPriorityScreen extends JFrame {
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
                    ChangeEmployeesPriorityScreen frame = new ChangeEmployeesPriorityScreen();
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
    public ChangeEmployeesPriorityScreen() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(450, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JTextField textField = new JTextField();
        textField.setBounds(20, 104, 200, 20);
        contentPane.add(textField);
        textField.setColumns(10);
        
        JLabel lblEnterEmployeesName = new JLabel("Enter employee's name");
        lblEnterEmployeesName.setFont(new Font("Tahoma", Font.BOLD, 11));
        lblEnterEmployeesName.setBounds(20, 84, 140, 14);
        contentPane.add(lblEnterEmployeesName);
        
        JLabel lblChangeEmployeesPriority = new JLabel("Change Employee's Priority");
        lblChangeEmployeesPriority.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblChangeEmployeesPriority.setBounds(70, 11, 286, 52);
        contentPane.add(lblChangeEmployeesPriority);
        
        JTextField textField_1 = new JTextField();
        textField_1.setBounds(250, 104, 170, 20);
        contentPane.add(textField_1);
        textField_1.setColumns(10);
        
        PrioritiesComboBox prioritiesComboBox = new PrioritiesComboBox();
        prioritiesComboBox.setBounds(20, 145, 50, 23);
        contentPane.add(prioritiesComboBox);
        
        JLabel priorityChangedLabel = new JLabel("Priority has changed...");
        priorityChangedLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
        priorityChangedLabel.setBounds(20, 180, 140, 14);
        contentPane.add(priorityChangedLabel);
        priorityChangedLabel.setVisible(false);
        
        JLabel noEmployeeLabel = new JLabel("There isn't employee with the given cradentials...");
        noEmployeeLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
        noEmployeeLabel.setBounds(20, 180, 286, 14);
        contentPane.add(noEmployeeLabel);
        noEmployeeLabel.setVisible(false);
        
        JLabel moreThanOneEmployeeWithEqualNameLabel = new JLabel("There are more than one employee with given name. "
                                                                   + "Please enter ID instead.");
        moreThanOneEmployeeWithEqualNameLabel.setFont(new Font("Tahoma", Font.BOLD, 10));
        moreThanOneEmployeeWithEqualNameLabel.setBounds(20, 180, 404, 14);
        contentPane.add(moreThanOneEmployeeWithEqualNameLabel);
        moreThanOneEmployeeWithEqualNameLabel.setVisible(false);
        
        JButton btnChangePriority = new JButton("Change priority");
        btnChangePriority.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                EmployeesData employeesData = EmployeesData.getInstance();
                String employeeId = textField_1.getText();
                if (employeeId.equals("")) {
                    String employeeName = textField.getText();
                    String[] splittedName = employeeName.split(" ", 2);
                    String firstName = splittedName[0];
                    String lastName = splittedName[1];
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
                                priorityChangedLabel.setVisible(false);
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
                    Employee employee = employeesData.getEmployeeWithId(employeeId);
                    employee.setPriority((int)prioritiesComboBox.getSelectedItem());
                    noEmployeeLabel.setVisible(false);
                    moreThanOneEmployeeWithEqualNameLabel.setVisible(false);
                    priorityChangedLabel.setVisible(true);
                } else {
                    priorityChangedLabel.setVisible(false);
                    moreThanOneEmployeeWithEqualNameLabel.setVisible(false);
                    noEmployeeLabel.setVisible(true);
                }
            }
        });
        btnChangePriority.setBounds(80, 145, 140, 23);
        contentPane.add(btnChangePriority);
        
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