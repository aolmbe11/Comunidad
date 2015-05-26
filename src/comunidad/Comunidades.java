package comunidad;

import entity_class.Comunidad;
import java.util.List;

public class Comunidades {

    private static List<Comunidad> listaComunidades;
   
    
    public void setComunidad(Comunidad comunidad) {
        listaComunidades.add(comunidad);
    }

    @Override
    public String toString() {
        String texto = "";
        for (int i = 0; i < listaComunidades.size(); i++) {
            texto += listaComunidades.get(i).toString() + ".\n";
        }
        return texto;
    }

    public static List<Comunidad> getListaComunidades() {
        return listaComunidades;
    }

    public static void setListaComunidades(List<Comunidad> listaComunidades) {
        Comunidades.listaComunidades = listaComunidades;
    }
    
    public static int getSize() {
        return listaComunidades.size();
    }

    public static Comunidad getComunidad(int posicion) {
        return listaComunidades.get(posicion);
    }
}
