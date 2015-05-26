
package renders;

import entity_class.Vivienda;
import java.awt.Color;
import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

public class ListaViviendasRenderer extends DefaultListCellRenderer {

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        Vivienda vivienda = (Vivienda)value;
        if(vivienda != null){
            setText(vivienda.getNumero() + " ("+vivienda.getComunidad().getNombre()+")");
        } else{
            setText(null);
        }
        
        if(isSelected){
            setBackground(Color.CYAN);
        } else{
            setBackground(Color.WHITE);
        }
        return this;
    }
    
}
