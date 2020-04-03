package Screens;

import java.awt.Component;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;

public class MultiLineCellRenderer extends JTextArea implements TableCellRenderer {

    public MultiLineCellRenderer() {
      setLineWrap(true);
      setWrapStyleWord(true);
      setOpaque(true);
    }

    /*
     * This function get the component of the cell renderer.
     * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object,
     * boolean, boolean, int, int)
     */
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                                                   int row, int column) {
      if (isSelected) {
          
          setForeground(table.getSelectionForeground());
          setBackground(table.getSelectionBackground());
      } else {
          setForeground(table.getForeground());
          setBackground(table.getBackground());
      }
      setFont(table.getFont());
      if (hasFocus) {
          setBorder(UIManager.getBorder("Table.focusCellHighlightBorder"));
          if (table.isCellEditable(row, column)) {
              setForeground(UIManager.getColor("Table.focusCellForeground"));
              setBackground(UIManager.getColor("Table.focusCellBackground"));
          }
      } else {
          setBorder(new EmptyBorder(1, 2, 1, 2));
      }
      setText((value == null) ? "" : value.toString());
      return this;
    }
}