/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import comunidad.Comunidades;
import entity_class.Comunidad;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Araceli Ramirez
 */
public class ComunidadTableModel extends AbstractTableModel {

    private boolean editable;

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    @Override
    public int getRowCount() {
        return Comunidades.getSize();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "Nombre";
            case 1:
                return "Dirección";
        }
        return null;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Comunidad comunidad = Comunidades.getComunidad(rowIndex);

        switch (columnIndex) {
            case 0:
                return comunidad.getNombre();
            case 1:
                return comunidad.getDireccion();
        }
        return null;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return editable;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

        Comunidad comunidad = Comunidades.getComunidad(rowIndex);

        switch (columnIndex) {
            case 0:
                comunidad.setNombre(String.valueOf(aValue));
                break;
            case 1:
                comunidad.setDireccion(String.valueOf(aValue));
                ;
                break;
        }
    }

}
