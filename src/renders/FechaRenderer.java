package renders;

import java.text.DateFormat;
import javax.swing.table.DefaultTableCellRenderer;

public class FechaRenderer extends DefaultTableCellRenderer {

    @Override
    protected void setValue(Object value) {
        DateFormat formato = DateFormat.getDateInstance(DateFormat.SHORT);        
        setText(formato.format(value));
    }
    
    
}
