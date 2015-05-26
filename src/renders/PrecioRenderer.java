package renders;

import java.text.NumberFormat;
import javax.swing.table.DefaultTableCellRenderer;

public class PrecioRenderer extends DefaultTableCellRenderer {

    @Override
    protected void setValue(Object value) {
        NumberFormat formato = NumberFormat.getCurrencyInstance();
        setText(formato.format(value));
        setHorizontalAlignment(RIGHT);
    }

}
