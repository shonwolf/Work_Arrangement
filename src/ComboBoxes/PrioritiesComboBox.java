package ComboBoxes;

import javax.swing.JComboBox;

public class PrioritiesComboBox extends JComboBox<Integer> {
    // the members
    private static final long serialVersionUID = 1L;
    
    public PrioritiesComboBox() {
        super();
        Integer[] priorities = {1, 2, 3, 4 ,5};
        for (int priority : priorities) {
            addItem(priority);
        }
    }
}
