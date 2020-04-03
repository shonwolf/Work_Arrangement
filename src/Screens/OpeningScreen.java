package Screens;

import java.awt.EventQueue;
import javax.swing.JFrame;
import Servers.ManageClientsRequestsServer;
import Tables.EmployeesInfoTable;
import Tables.ShiftsTrackingTable;
import Tables.SubmittedShiftsTable;
import Tables.WorkScheduleTable;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import Data.EmployeesData;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.ActionEvent;
import java.awt.CardLayout;
import Announcers.ButtonAnnouncer;
import ComboBoxes.ComparatorsComboBox;
import ComboBoxes.OperationsComboBox;
import Data.WorkSchedule;
import javax.swing.JTextField;

public class OpeningScreen {
    // the members
    private JFrame frame;

    /**
     * This function launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    OpeningScreen window = new OpeningScreen();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        ManageClientsRequestsServer mcrs = new ManageClientsRequestsServer();
        mcrs.manageClientsRequests();
    }
    
    public boolean checkDateFormat(String date) {
        return date.matches("^([0-2][0-9]|(3)[0-1])(\\/)(((0)[0-9])|((1)[0-2]))(\\/)\\d{4}$");
    }
    
    /**
     * This function creates the application.
     */
    public OpeningScreen() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(75, 50, 1200, 650);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
           public void windowClosing(WindowEvent e)
           {
              EmployeesData.getInstance().save();
              frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
           }
        });
        frame.getContentPane().setLayout(new CardLayout(0, 0));
        
        JPanel menuPanel = new JPanel();
        frame.getContentPane().add(menuPanel, "name_1562951908891293");
        menuPanel.setLayout(null);
        menuPanel.setVisible(true);
        
        JPanel allSubmittedShiftsPanel = new JPanel();
        frame.getContentPane().add(allSubmittedShiftsPanel, "name_1562954363247137");
        allSubmittedShiftsPanel.setLayout(null);
        allSubmittedShiftsPanel.setVisible(false);
        
        JPanel allEmployeesPanel = new JPanel();
        frame.getContentPane().add(allEmployeesPanel, "name_2291264023535375");
        allEmployeesPanel.setLayout(null);
        allEmployeesPanel.setVisible(false);
        
        JPanel workSchedulePanel = new JPanel();
        frame.getContentPane().add(workSchedulePanel, "name_2509206145563875");
        workSchedulePanel.setLayout(null);
        workSchedulePanel.setVisible(false);
        
        JPanel shiftsTrackingPanel = new JPanel();
        frame.getContentPane().add(shiftsTrackingPanel, "name_3034124109851137");
        shiftsTrackingPanel.setLayout(null);
        shiftsTrackingPanel.setVisible(false);
        
        JScrollPane scrollPane3 = new JScrollPane();
        scrollPane3.setBounds(10, 11, 1164, 518);
        workSchedulePanel.add(scrollPane3);
        
        WorkScheduleTable workScheduleTable = new WorkScheduleTable();
        scrollPane3.setViewportView(workScheduleTable);
        
        JButton showCurrentWorkScheduleButton = new JButton("Show Current Work Schedule");
        showCurrentWorkScheduleButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                workSchedulePanel.setVisible(true);
                menuPanel.setVisible(false);
                workScheduleTable.fillTable();
            }
        });
        showCurrentWorkScheduleButton.setFont(new Font("Tahoma", Font.BOLD, 14));
        showCurrentWorkScheduleButton.setBounds(10, 30, 263, 60);
        menuPanel.add(showCurrentWorkScheduleButton);
        
        WorkSchedule workSchedule = WorkSchedule.getInstance();
        workSchedule.addObserver(new ButtonAnnouncer(showCurrentWorkScheduleButton));
        workSchedule.notifyObservers("");
        
        ComparatorsComboBox comparatorsComboBox = new ComparatorsComboBox();
        comparatorsComboBox.setBounds(572, 30, 215, 33);
        menuPanel.add(comparatorsComboBox);
        
        JButton createNewWorkScheduleButton = new JButton("Create New Work Schedule");
        createNewWorkScheduleButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                WorkSchedule.getInstance().createWorkSchedule(comparatorsComboBox.getComparator(), 2);
            }
        });
        createNewWorkScheduleButton.setFont(new Font("Tahoma", Font.BOLD, 14));
        createNewWorkScheduleButton.setBounds(283, 30, 263, 60);
        menuPanel.add(createNewWorkScheduleButton);
        
        JLabel lblNewLabel = new JLabel("Updates:");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 22));
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setBounds(50, 320, 98, 27);
        menuPanel.add(lblNewLabel);
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(50, 350, 400, 202);
        menuPanel.add(scrollPane);
        
        JTextArea textArea = new JTextArea();
        scrollPane.setViewportView(textArea);
        
        EmployeesData.getInstance().setJTextArea(textArea);
        
        JButton backToMenuButton1 = new JButton("Back To Menu");
        backToMenuButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                allSubmittedShiftsPanel.setVisible(false);
                menuPanel.setVisible(true);
            }
        });
        backToMenuButton1.setFont(new Font("Tahoma", Font.BOLD, 12));
        backToMenuButton1.setBounds(500, 550, 150, 33);
        allSubmittedShiftsPanel.add(backToMenuButton1);
        
        JButton backToMenuButton2 = new JButton("Back To Menu");
        backToMenuButton2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                allEmployeesPanel.setVisible(false);
                menuPanel.setVisible(true);
            }
        });
        backToMenuButton2.setFont(new Font("Tahoma", Font.BOLD, 12));
        backToMenuButton2.setBounds(500, 550, 150, 33);
        allEmployeesPanel.add(backToMenuButton2);
        
        JButton backToMenuButton3 = new JButton("Back To Menu");
        backToMenuButton3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                workSchedulePanel.setVisible(false);
                menuPanel.setVisible(true);
            }
        });
        backToMenuButton3.setFont(new Font("Tahoma", Font.BOLD, 12));
        backToMenuButton3.setBounds(500, 550, 150, 33);
        workSchedulePanel.add(backToMenuButton3);
        
        JButton backToMenuButton4 = new JButton("Back To Menu");
        backToMenuButton4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                shiftsTrackingPanel.setVisible(false);
                menuPanel.setVisible(true);
            }
        });
        backToMenuButton4.setFont(new Font("Tahoma", Font.BOLD, 12));
        backToMenuButton4.setBounds(500, 550, 150, 33);
        shiftsTrackingPanel.add(backToMenuButton4);
        
        JScrollPane scrollPane1 = new JScrollPane();
        scrollPane1.setBounds(10, 11, 1164, 518);
        allSubmittedShiftsPanel.add(scrollPane1);
        
        SubmittedShiftsTable submittedShiftsTable = new SubmittedShiftsTable();
        scrollPane1.setViewportView(submittedShiftsTable);
        
        JButton showAllSubmittedShiftsButton = new JButton("Show All Submitted Shifts");
        showAllSubmittedShiftsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                allSubmittedShiftsPanel.setVisible(true);
                menuPanel.setVisible(false);
                submittedShiftsTable.fillTable();
            }
        });
        showAllSubmittedShiftsButton.setFont(new Font("Tahoma", Font.BOLD, 14));
        showAllSubmittedShiftsButton.setBounds(10, 131, 263, 60);
        menuPanel.add(showAllSubmittedShiftsButton);
        
        JScrollPane scrollPane2 = new JScrollPane();
        scrollPane2.setBounds(10, 11, 1164, 518);
        allEmployeesPanel.add(scrollPane2);
        
        EmployeesInfoTable allEmployeesTable = new EmployeesInfoTable();
        scrollPane2.setViewportView(allEmployeesTable);
        
        JButton showAllEmployeesButton = new JButton("Show All Employees");
        showAllEmployeesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                allEmployeesPanel.setVisible(true);
                menuPanel.setVisible(false);
                allEmployeesTable.fillTable();
            }
        });
        showAllEmployeesButton.setFont(new Font("Tahoma", Font.BOLD, 14));
        showAllEmployeesButton.setBounds(283, 131, 263, 60);
        menuPanel.add(showAllEmployeesButton);
        
        JButton cleanUpdatesButton = new JButton("Clean");
        cleanUpdatesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                textArea.setText("");
            }
        });
        cleanUpdatesButton.setFont(new Font("Tahoma", Font.BOLD, 14));
        cleanUpdatesButton.setBounds(175, 565, 150, 33);
        menuPanel.add(cleanUpdatesButton);
        
        OperationsComboBox operationsComboBox = new OperationsComboBox();
        operationsComboBox.setBounds(500, 350, 250, 33);
        menuPanel.add(operationsComboBox);
        
        JButton doButton = new JButton("Do");
        doButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                String value = (String)operationsComboBox.getSelectedItem();
                operationsComboBox.operate(value);
            }
        });
        doButton.setBounds(500, 390, 250, 23);
        menuPanel.add(doButton);
        
        JLabel lblNewLabel_1 = new JLabel("Fill dates in format: dd/MM/yyyy, and fill ID");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 11));
        lblNewLabel_1.setBounds(20, 480, 269, 14);
        shiftsTrackingPanel.add(lblNewLabel_1);
        
        JTextField txtStartDate = new JTextField();
        txtStartDate.setText("Start date");
        txtStartDate.setToolTipText("");
        txtStartDate.setBounds(20, 500, 108, 20);
        shiftsTrackingPanel.add(txtStartDate);
        txtStartDate.setColumns(10);
        
        JTextField txtEndDate = new JTextField();
        txtEndDate.setText("End date");
        txtEndDate.setBounds(20, 523, 108, 20);
        shiftsTrackingPanel.add(txtEndDate);
        txtEndDate.setColumns(10);
        
        JTextField txtId = new JTextField();
        txtId.setText("ID");
        txtId.setBounds(20, 546, 108, 20);
        shiftsTrackingPanel.add(txtId);
        txtId.setColumns(10);
        
        JScrollPane scrollPane4 = new JScrollPane();
        scrollPane4.setBounds(10, 11, 1162, 459);
        shiftsTrackingPanel.add(scrollPane4);
        
        ShiftsTrackingTable shiftsTrackingTable = new ShiftsTrackingTable();
        scrollPane4.setViewportView(shiftsTrackingTable);
        
        JButton showShiftsTrackingButton = new JButton("Show Shifts Tracking");
        showShiftsTrackingButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                shiftsTrackingPanel.setVisible(true);
                menuPanel.setVisible(false);
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date currentDate = new Date();
                txtStartDate.setText(dateFormat.format(currentDate));
                txtEndDate.setText(dateFormat.format(currentDate));
            }
        });
        showShiftsTrackingButton.setFont(new Font("Tahoma", Font.BOLD, 14));
        showShiftsTrackingButton.setBounds(10, 225, 263, 60);
        menuPanel.add(showShiftsTrackingButton);
        
        JButton btnNewButton = new JButton("Search");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
              String id = txtId.getText();
              String start = txtStartDate.getText();
              String end = txtEndDate.getText();
              if (!checkDateFormat(start) || !checkDateFormat(end)) {
                  return;
              }
              SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
              try {
                  shiftsTrackingTable.fillTable(id, dateFormat.parse(start), dateFormat.parse(end));
              } catch(Exception e) {
                  e.printStackTrace();
              }
            }
        });
        btnNewButton.setBounds(172, 523, 89, 23);
        shiftsTrackingPanel.add(btnNewButton);
        
    }
}