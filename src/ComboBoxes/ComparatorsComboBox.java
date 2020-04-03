package ComboBoxes;

import java.util.Comparator;
import java.util.HashMap;
import javax.swing.JComboBox;
import Comparators.PriorityComperator;
import Comparators.SeniorityComperator;
import Employees.Employee;

public class ComparatorsComboBox extends JComboBox<String> {
    // the members
    private static final long serialVersionUID = 1L;
    private HashMap<String, Comparator<Employee>> comparatorByKey;

    public ComparatorsComboBox() {
        super();
        String[] comparators = {"Sort by", "Priority", "Seniority"};
        for (String comparator : comparators) {
            addItem(comparator);
        }
        comparatorByKey = new HashMap<String, Comparator<Employee>>();
        comparatorByKey.put("Priority", new PriorityComperator());
        comparatorByKey.put("Seniority", new SeniorityComperator());
    }
    
    public Comparator<Employee> getComparator() {
        String chosenComparator = (String)getSelectedItem();
        Comparator<Employee> comparator = comparatorByKey.get(chosenComparator);
        if (comparator != null) {
            return comparator;
        } else {
            // return default comparator - by priority
            return comparatorByKey.get("Priority");
        }
    }
}
