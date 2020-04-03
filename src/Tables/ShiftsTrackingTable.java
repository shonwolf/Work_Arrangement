package Tables;

import java.awt.Color;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import Data.EmployeesData;
import Employees.ShiftsTracking;
import Screens.MultiLineCellRenderer;
import javafx.util.Pair;
public class ShiftsTrackingTable  extends JTable {
    // the members
    private static final long serialVersionUID = 1L;
    private DefaultTableModel tableModel;
    
    public ShiftsTrackingTable() {
        super();
        setBackground(Color.ORANGE);
        tableModel = new DefaultTableModel(
                new Object[][] {
                },
                new String[] {
                    "Day", "Start", "End", "Duration", "Payment for shift"
                }
        ) {
            Class[] columnTypes = new Class[] {
                String.class, String.class, String.class, String.class, String.class
            };
            public Class getColumnClass(int columnIndex) {
                return columnTypes[columnIndex];
            }
            boolean[] columnEditables = new boolean[] {
                false, false, false, false, false
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
    public void fillTable(String id, Date start, Date end) {
        tableModel.setRowCount(0);
        EmployeesData employeesData = EmployeesData.getInstance();
        ShiftsTracking shiftTracking = employeesData.getShiftsTracking(id);
        double hourPayment = employeesData.getEmployeeWithId(id).getHourPayment();
        if (shiftTracking != null) {
            ArrayList<Pair<Date, Date>> dates = shiftTracking.getDatesBetween(start, end);
            SimpleDateFormat dayName = new SimpleDateFormat("EEEE");
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            double totalHours = 0.0;
            for (Pair<Date, Date> datePair : dates) {
                HashMap<String, Long> map = shiftTracking.getShiftDuration(datePair.getKey(), datePair.getValue());
                String duration = map.get("hours") + "h, " + map.get("minutes") + "m";
                double totalShiftHours = map.get("hours") + (double)map.get("minutes") / 60;
                totalHours += totalShiftHours;
                tableModel.addRow(new Object[] {dayName.format(datePair.getKey()),
                                                dateFormat.format(datePair.getKey()),
                                                dateFormat.format(datePair.getValue()),
                                                duration,
                                                Double.toString(totalShiftHours * hourPayment)});
            }
            tableModel.addRow(new Object[] {"", "", "", 
                                            "total hours: " + Double.toString(totalHours),
                                            "total: " + Double.toString(totalHours * hourPayment)});
        }
    }
}