package models;


import comunidad.Cuotas;
import entity_class.Cuota;
import java.text.DateFormat;
import javax.swing.table.AbstractTableModel;

public class CuotasTableModel extends AbstractTableModel {

    private Cuotas cuotas;

    public CuotasTableModel(Cuotas cuotas) {
        this.cuotas = cuotas;
    }
    
    @Override
    public int getRowCount() {
        return cuotas.getSize();
    }

    @Override
    public int getColumnCount() {
        return 9;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Cuota cuota = cuotas.getCuota(rowIndex);
        switch (columnIndex) {
            case 0:
                return cuota.getVivienda().getNumero();
            case 1:
                return cuota.getAgua();
            case 2:
                return cuota.getLuz();
            case 3:
                return cuota.getBasura();
            case 4:
                return cuota.getZonasComunes();
            case 5:
                return cuota.getOtros();
            case 6:
                return cuota.getTotal();
            case 7:
                return cuota.getFecha();
            case 8:
                return cuota.getPagado();
        }
        return null;
    }

    @Override
    public String getColumnName(int column) {
        String nombre = "";
        switch (column) {
            case 0:
                nombre = "Vivienda";
                break;
            case 1:
                nombre = "Agua";
                break;
            case 2:
                nombre = "Luz";
                break;
            case 3:
                nombre = "Basura";
                break;
            case 4:
                nombre = "Zonas Comunes";
                break;
            case 5:
                nombre = "Otros";
                break;
            case 6:
                nombre = "Total";
                break;
            case 7:
                nombre = "Fecha";
                break;
            case 8:
                nombre = "Pagado";
                break;
        }
        return nombre;
    }
}
