package Screens;

import java.awt.EventQueue;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import Data.EmployeesData;
import Employees.Employee;
import Employees.Manager;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ManageEmployeeManagerScreen extends JFrame {
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
                    ManageEmployeeManagerScreen frame = new ManageEmployeeManagerScreen();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    
    /*
     * This function checks if the given cradentials are correct.
     */
    public boolean checkCradetials(Employee employee, Employee manager, JLabel noEmployeeLabel, JLabel noManagerLabel,
                                   JLabel missionAccomplishedLabel) {
        if (employee == null) {
            missionAccomplishedLabel.setVisible(false);
            noManagerLabel.setVisible(false);
            noEmployeeLabel.setVisible(true);
            return false;
        } else if (manager == null || !(manager instanceof Manager)) {
            missionAccomplishedLabel.setVisible(false);
            noEmployeeLabel.setVisible(false);
            noManagerLabel.setVisible(true);
            return false;
        }
        return true;
    }
    
    /*
     * This function alert that the mission accomplished.
     */
    public void missionAccomplished(JLabel noEmployeeLabel, JLabel noManagerLabel, JLabel missionAccomplishedLabel) {
        noEmployeeLabel.setVisible(false);
        noManagerLabel.setVisible(false);
        missionAccomplishedLabel.setVisible(true);
    }
    
    
    /**
     * Create the frame.
     */
    public ManageEmployeeManagerScreen() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(450, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel lblChangeEmployeesPriority = new JLabel("Manage Employee Manager");
        lblChangeEmployeesPriority.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblChangeEmployeesPriority.setBounds(70, 11, 286, 52);
        contentPane.add(lblChangeEmployeesPriority);
        
        JTextField textField = new JTextField();
        textField.setBounds(20, 87, 127, 20);
        contentPane.add(textField);
        textField.setColumns(10);
        
        JLabel lblEmployeesId = new JLabel("Enter employee's ID:");
        lblEmployeesId.setFont(new Font("Tahoma", Font.BOLD, 11));
        lblEmployeesId.setBounds(20, 62, 142, 14);
        contentPane.add(lblEmployeesId);
        
        JLabel lblEnterManagersId = new JLabel("Enter manager's ID:");
        lblEnterManagersId.setFont(new Font("Tahoma", Font.BOLD, 11));
        lblEnterManagersId.setBounds(245, 62, 167, 14);
        contentPane.add(lblEnterManagersId);
        
        JTextField textField_1 = new JTextField();
        textField_1.setColumns(10);
        textField_1.setBounds(245, 87, 127, 20);
        contentPane.add(textField_1);
        
        JLabel missionAccomplishedLabel = new JLabel("Mission accomplished!");
        missionAccomplishedLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
        missionAccomplishedLabel.setBounds(20, 180, 140, 14);
        contentPane.add(missionAccomplishedLabel);
        missionAccomplishedLabel.setVisible(false);
        
        JLabel noEmployeeLabel = new JLabel("There isn't employee with the given id...");
        noEmployeeLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
        noEmployeeLabel.setBounds(20, 180, 286, 14);
        contentPane.add(noEmployeeLabel);
        noEmployeeLabel.setVisible(false);
        
        JLabel noManagerLabel = new JLabel("There isn't manager with the given id...");
        noManagerLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
        noManagerLabel.setBounds(20, 180, 286, 14);
        contentPane.add(noManagerLabel);
        noManagerLabel.setVisible(false);
        
        JButton btnNewButton = new JButton("Assign employee to manager");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                EmployeesData employeesData = EmployeesData.getInstance();
                String employeeId = textField.getText();
                String managerId = textField_1.getText();
                Employee employee = employeesData.getEmployeeWithId(employeeId);
                Employee manager = employeesData.getEmployeeWithId(managerId);
                if (!checkCradetials(employee, manager, noEmployeeLabel, noManagerLabel, missionAccomplishedLabel)) {
                    return;
                }
                ((Manager)manager).addDirectEmployed(employee);
                missionAccomplished(noEmployeeLabel, noManagerLabel, missionAccomplishedLabel);
            }
        });
        btnNewButton.setBounds(95, 120, 215, 23);
        contentPane.add(btnNewButton);
        
        JButton btnNewButton_1 = new JButton("Unassign employee to manager");
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                EmployeesData employeesData = EmployeesData.getInstance();
                String employeeId = textField.getText();
                String managerId = textField_1.getText();
                Employee employee = employeesData.getEmployeeWithId(employeeId);
                Employee manager = employeesData.getEmployeeWithId(managerId);
                if (!checkCradetials(employee, manager, noEmployeeLabel, noManagerLabel, missionAccomplishedLabel)) {
                    return;
                }
                ((Manager)manager).removeDirectEmployed(employee);
                missionAccomplished(noEmployeeLabel, noManagerLabel, missionAccomplishedLabel);
            }
        });
        btnNewButton_1.setBounds(95, 149, 215, 23);
        contentPane.add(btnNewButton_1);
        
        JLabel lblNewLabel = new JLabel("--->");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
        lblNewLabel.setBounds(174, 90, 36, 14);
        contentPane.add(lblNewLabel);
        
        JLabel label = new JLabel("Enter employee's ID:");
        label.setFont(new Font("Tahoma", Font.BOLD, 11));
        label.setBounds(20, 199, 142, 14);
        contentPane.add(label);
        
        JTextField textField_2 = new JTextField();
        textField_2.setColumns(10);
        textField_2.setBounds(20, 224, 127, 20);
        contentPane.add(textField_2);
        
        JLabel lblTheManagersId = new JLabel("The manager's ID(s) is/are:");
        lblTheManagersId.setFont(new Font("Tahoma", Font.BOLD, 11));
        lblTheManagersId.setBounds(245, 199, 167, 14);
        contentPane.add(lblTheManagersId);
        
        JTextField textField_3 = new JTextField();
        textField_3.setColumns(10);
        textField_3.setBounds(245, 224, 127, 20);
        contentPane.add(textField_3);
        
        JButton btnNewButton_2 = new JButton("--->");
        btnNewButton_2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                EmployeesData employeesData = EmployeesData.getInstance();
                String employeeId = textField_2.getText();
                Employee employee = employeesData.getEmployeeWithId(employeeId);
                if (employee == null) {
                    missionAccomplishedLabel.setVisible(false);
                    noManagerLabel.setVisible(false);
                    noEmployeeLabel.setVisible(true);
                }
                StringBuilder managersId = new StringBuilder("");
                for (Employee curEmployee : employeesData.getEmployees()) {
                    for (Employee directEmployee : curEmployee.getDirectEmployees()) {
                        if (employee.equals(directEmployee)) {
                            managersId.append(curEmployee.getId() + ", ");
                        }
                    }
                }
                if (!managersId.toString().equals("")) {
                    // erase the last ", "
                    managersId.deleteCharAt(managersId.length() - 1);
                    managersId.deleteCharAt(managersId.length() - 1);
                }
                textField_3.setText(managersId.toString());
            }
        });
        btnNewButton_2.setFont(new Font("Tahoma", Font.BOLD, 11));
        btnNewButton_2.setBounds(157, 223, 65, 23);
        contentPane.add(btnNewButton_2);
    }
}
