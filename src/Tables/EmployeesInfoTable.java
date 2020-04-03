package Tables;

import java.awt.Color;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import Data.EmployeesData;
import Employees.Employee;
import Screens.MultiLineCellRenderer;

public class EmployeesInfoTable extends JTable {
    // the members
    private static final long serialVersionUID = 1L;
    private DefaultTableModel tableModel;
    
     public EmployeesInfoTable() {
         super();
         setBackground(Color.ORANGE);
         tableModel = new DefaultTableModel(
             new Object[][] {
             },
             new String[] {
                 "ID", "First name", "Last name", "Age", "Priority", "Hour payment", "Start work date", "Seniority"
             }
         ) {
             Class[] columnTypes = new Class[] {
                 String.class, String.class, String.class, String.class, String.class, String.class, String.class,
                 String.class
             };
             public Class getColumnClass(int columnIndex) {
                 return columnTypes[columnIndex];
             }
             boolean[] columnEditables = new boolean[] {
                 false, false, false, false, false, false, false, false
             };
             public boolean isCellEditable(int row, int column) {
                 return columnEditables[column];
             }
         };
         setModel(tableModel);
         setFont(new Font("Tahoma", Font.PLAIN, 12));
         setDefaultRenderer(String.class, new MultiLineCellRenderer());
     }
     
     /*
      * This function fills the table with data.
      */
     public void fillTable() {
         tableModel.setRowCount(0);
         SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
         for (Employee employee : EmployeesData.getInstance().getEmployees()) {
             tableModel.addRow(new Object[] {employee.getId(),
                                             employee.getFirstName(),
                                             employee.getLastName(),
                                             Integer.toString(employee.getAge()),
                                             Integer.toString(employee.getPriority()),
                                             Double.toString(employee.getHourPayment()),
                                             simpleDateFormat.format(employee.getStartWorkDate()),
                                             getFormatSeniority(employee)});
         }
     }
     
     /*
      * This function format the seniority from the HashMap of details.
      */
     public String getFormatSeniority(Employee employee) {
         HashMap<String, Long> employeeSeniorityUnits = employee.getSeniority();
         StringBuilder formatSeniority = new StringBuilder("");
         if (employeeSeniorityUnits.get("years") > 9) {
             formatSeniority.append(Long.toString(employeeSeniorityUnits.get("years")));
         } else {
             formatSeniority.append('0' + Long.toString(employeeSeniorityUnits.get("years")));
         }
         formatSeniority.append('.');
         if (employeeSeniorityUnits.get("months") > 9) {
             formatSeniority.append(Long.toString(employeeSeniorityUnits.get("months")));
         } else {
             formatSeniority.append('0' + Long.toString(employeeSeniorityUnits.get("months")));
         }
         formatSeniority.append('.');
         if (employeeSeniorityUnits.get("days") > 9) {
             formatSeniority.append(Long.toString(employeeSeniorityUnits.get("days")));
         } else {
             formatSeniority.append('0' + Long.toString(employeeSeniorityUnits.get("days")));
         }
         return formatSeniority.toString();
     }
}