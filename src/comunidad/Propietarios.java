package comunidad;

import entity_class.Propietario;
import java.util.List;

public class Propietarios {

    private List<Propietario> listaPropietarios;

    public void setPropietario(Propietario propietario) {
        listaPropietarios.add(propietario);
    }

    @Override
    public String toString() {
        String texto = "";
        for (int i = 0; i < listaPropietarios.size(); i++) {
            texto += listaPropietarios.get(i).toString() + ".\n";
        }
        return texto;
    }

    public int getSize() {
        return listaPropietarios.size();
    }

    public Propietario getPropietario(int posicion) {
        return listaPropietarios.get(posicion);
    }

    public List<Propietario> getListaPropietarios() {
        return listaPropietarios;
    }

    public void setListaPropietarios(List<Propietario> listaPropietarios) {
        this.listaPropietarios = listaPropietarios;
    }
        
}
