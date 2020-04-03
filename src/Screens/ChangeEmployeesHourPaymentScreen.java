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

public class ChangeEmployeesHourPaymentScreen extends JFrame {
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
                    ChangeEmployeesHourPaymentScreen frame = new ChangeEmployeesHourPaymentScreen();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    
    /*
     * This function return true if the hour payment is in numeric format and false otherwise.
     */
    public boolean checkHourPayment(String price) {
        return price.matches("^[0-9]+([\\\\,\\\\.][0-9]+)?$");
    }

    /**
     * Create the frame.
     */
    public ChangeEmployeesHourPaymentScreen() {
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
        
        JLabel lblChangeEmployeesHourPayment = new JLabel("Change Employee's Hour Payment");
        lblChangeEmployeesHourPayment.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblChangeEmployeesHourPayment.setBounds(30, 11, 370, 52);
        contentPane.add(lblChangeEmployeesHourPayment);
        
        JTextField textField_1 = new JTextField();
        textField_1.setBounds(250, 104, 170, 20);
        contentPane.add(textField_1);
        textField_1.setColumns(10);
        
        JTextField txtHourPayemnt = new JTextField();
        txtHourPayemnt.setBounds(20, 145, 50, 23);
        contentPane.add(txtHourPayemnt);
        
        JLabel hourPaymentChangeLabel = new JLabel("Hour payment has changed...");
        hourPaymentChangeLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
        hourPaymentChangeLabel.setBounds(20, 180, 190, 11);
        contentPane.add(hourPaymentChangeLabel);
        hourPaymentChangeLabel.setVisible(false);
        
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
        
        JLabel hourPaymentErrorLabel = new JLabel("Hour payment must be a number");
        hourPaymentErrorLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
        hourPaymentErrorLabel.setBounds(20, 180, 190, 14);
        contentPane.add(hourPaymentErrorLabel);
        hourPaymentErrorLabel.setVisible(false);
        
        JButton btnChangeHourPayment = new JButton("Change hour payment");
        btnChangeHourPayment.addActionListener(new ActionListener() {
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
                                hourPaymentChangeLabel.setVisible(false);
                                hourPaymentErrorLabel.setVisible(false);
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
                    String hourPayment = txtHourPayemnt.getText();
                    if (!checkHourPayment(hourPayment)) {
                        noEmployeeLabel.setVisible(false);
                        moreThanOneEmployeeWithEqualNameLabel.setVisible(false);
                        hourPaymentChangeLabel.setVisible(false);
                        hourPaymentErrorLabel.setVisible(true);
                        return;
                    }
                    employee.setHourPayment(Double.parseDouble(hourPayment));;
                    noEmployeeLabel.setVisible(false);
                    moreThanOneEmployeeWithEqualNameLabel.setVisible(false);
                    hourPaymentErrorLabel.setVisible(false);
                    hourPaymentChangeLabel.setVisible(true);
                } else {
                    hourPaymentChangeLabel.setVisible(false);
                    moreThanOneEmployeeWithEqualNameLabel.setVisible(false);
                    hourPaymentErrorLabel.setVisible(false);
                    noEmployeeLabel.setVisible(true);
                }
            }
        });
        btnChangeHourPayment.setBounds(80, 145, 158, 23);
        contentPane.add(btnChangeHourPayment);
        
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