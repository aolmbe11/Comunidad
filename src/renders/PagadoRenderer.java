package renders;

import javax.swing.table.DefaultTableCellRenderer;

public class PagadoRenderer extends DefaultTableCellRenderer{

    @Override
    protected void setValue(Object value) {
        
        if((boolean)value){
            setText("\u2713");
        } else{
            setText("-");
        }
        setHorizontalAlignment(CENTER);
    }
    
}
