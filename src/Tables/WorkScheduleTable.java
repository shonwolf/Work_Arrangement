package Tables;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import Data.WorkSchedule;
import Data.WorkSchedule.JobType;
import General.RowsCounter;
import Screens.MultiLineCellRenderer;

public class WorkScheduleTable extends JTable {
    // the members
    static final long serialVersionUID = 1L;
    private DefaultTableModel tableModel;
    private int initialRowHighet;
    
    public WorkScheduleTable() {
        super();
        setBackground(Color.ORANGE);
        tableModel = new DefaultTableModel(
            new Object[][] {
            },
            new String[] {
                "JobType / Day", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"
            }
        ) {
            Class[] columnTypes = new Class[] {
                String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class
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
        initialRowHighet = getRowHeight();
    }
    
    /*
     * This function fills the table with data.
     */
    public void fillTable() {
        tableModel.setRowCount(0);
        String[][] workSchedule = WorkSchedule.getInstance().getWorkSchedule();
        JobType[] jobTypes = JobType.values();
        int maxNumRows = 0;
        for (int i = 0; i < workSchedule.length; i++) {
            Object[] objects = new Object[workSchedule[i].length + 1];
            objects[0] = jobTypes[i].name();
            for (int j = 0; j < workSchedule[i].length; j++) {
                objects[j + 1] = workSchedule[i][j];
                int numRows = RowsCounter.countRows(workSchedule[i][j]);
                if (numRows > maxNumRows) {
                    maxNumRows = numRows;
                }
            }
            tableModel.addRow(objects);
        }
        if (maxNumRows >= 1) {
            setRowHeight(initialRowHighet * maxNumRows);
        }
    }
}