/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package renders;

import entity_class.Propietario;
import java.awt.Color;
import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

/**
 *
 * @author Araceli Ramirez
 */
public class ListaPropietariosRenderer extends DefaultListCellRenderer {

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        Propietario propietario = (Propietario) value;
        if (value != null) {
            setText(propietario.getApellidos()+ ", " +propietario.getNombre() +" ("+ propietario.getDni()+")");
            if (isSelected) {
                setBackground(Color.CYAN);
            } else {
                setBackground(Color.WHITE);
            }
        } else{
            setText("");
        }

        return this;
    }
}
