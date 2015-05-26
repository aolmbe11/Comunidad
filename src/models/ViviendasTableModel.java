package models;

import comunidad.Viviendas;
import entity_class.Propietario;
import entity_class.Vivienda;
import javax.swing.table.AbstractTableModel;

public class ViviendasTableModel extends AbstractTableModel {
    
    private Viviendas viviendas;
    private boolean editable;

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }  

    public ViviendasTableModel(Viviendas viviendas) {
        this.viviendas = viviendas;
    }

    @Override
    public int getRowCount() {
        return viviendas.getListaViviendas().size(); 
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        Vivienda vivienda = viviendas.getVivienda(rowIndex);

        switch (columnIndex) {
            case 0:
                return vivienda.getNumero();
            case 1:
                return vivienda.getPropietario();
            case 2:
                return vivienda.getNumeroCuenta();
        }
        return null;
    }

    @Override
    public String getColumnName(int column) {

        String nombre = "";
        switch (column) {
            case 0:
                nombre = "Número";
                break;
            case 1:
                nombre = "Propietario";
                break;
            case 2:
                nombre = "Número de cuenta";
                break;
        }
        return nombre;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return editable;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        
        Vivienda vivienda = viviendas.getVivienda(rowIndex);

        switch (columnIndex) {
            case 0:
                 vivienda.setNumero(String.valueOf(aValue));
                break;
            case 1:
                vivienda.setPropietario((Propietario)aValue);
                break;
            case 2:
                vivienda.setNumeroCuenta(String.valueOf(aValue));;
                break;
        }
    }

}
