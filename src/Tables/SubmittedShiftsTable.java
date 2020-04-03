package Tables;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Data.EmployeesData;
import Employees.Employee;
import Employees.WeeklyShifts;
import General.RowsCounter;
import Screens.MultiLineCellRenderer;

public class SubmittedShiftsTable extends JTable {
    // the members
    private static final long serialVersionUID = 1L;
    private DefaultTableModel tableModel;
    private int initialRowHighet;
    
    public SubmittedShiftsTable() {
        super();
        setBackground(Color.ORANGE);
        tableModel = new DefaultTableModel(
            new Object[][] {
            },
            new String[] {
                "ID", "Full Name", "Job Type", "Submitted Shifts"
            }
        ) {
            Class[] columnTypes = new Class[] {
                String.class, String.class, String.class, String.class
            };
            public Class getColumnClass(int columnIndex) {
                return columnTypes[columnIndex];
            }
            boolean[] columnEditables = new boolean[] {
                false, false, false, false
            };
            public boolean isCellEditable(int row, int column) {
                return columnEditables[column];
            }
        };
        setModel(tableModel);
        setFont(new Font("Tahoma", Font.PLAIN, 12));
        setDefaultRenderer(String.class, new MultiLineCellRenderer());
        initialRowHighet = getRowHeight();
    }
    
    /*
     * This function fills the table with data.
     */
    public void fillTable() {
        tableModel.setRowCount(0);
        EmployeesData employeesData = EmployeesData.getInstance();
        int maxNumRows = 0;
        for (Employee employee : employeesData.getEmployees()) {
            WeeklyShifts shifts = employeesData.getThisEmployeesShifts(employee.getId());
            String shiftsInStringRows = shifts.getAllShiftsInStringsRows();
            tableModel.addRow(new Object[] {employee.getId(),
                                            employee.getFirstName() + " " + employee.getLastName(),
                                            employee.getClass().getName().substring("Employees.".length()),
                                            shiftsInStringRows});
            int numRows = RowsCounter.countRows(shiftsInStringRows);
            if (numRows > maxNumRows) {
                maxNumRows = numRows;
            }
        }
        if (maxNumRows >= 1) {
            setRowHeight(initialRowHighet * maxNumRows);
        }
    }
}