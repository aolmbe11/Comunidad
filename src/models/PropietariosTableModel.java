package models;


import comunidad.Propietarios;
import entity_class.Propietario;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Araceli Ramirez
 */
public class PropietariosTableModel extends AbstractTableModel {

    private Propietarios propietarios;

    public PropietariosTableModel(Propietarios Propietarios) {
        this.propietarios = Propietarios;
    }

    @Override
    public int getRowCount() {
        return propietarios.getSize();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        
        Propietario propietario = propietarios.getPropietario(rowIndex);
        switch (columnIndex) {
            case 0:
                return propietario.getApellidos();
            case 1:
                return propietario.getNombre();
            case 2:
                return propietario.getEmail();
            case 3:
                return propietario.getTelefono();
            case 4:
                return propietario.getDni();                  
        }
        return null;     
    }

    @Override
    public String getColumnName(int column) {
        String nombre = "";
        switch (column) {
            case 0:
                nombre = "Apellidos";
                break;
            case 1:
                nombre = "Nombre";
                break;
            case 2:
                nombre = "E-Mail";
                break;                
            case 3:
                nombre = "Teléfono";
                break;
            case 4:
                nombre = "DNI";
                break;           
        }
        return nombre;
    }
}
