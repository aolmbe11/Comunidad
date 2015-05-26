
package comunidad;

import entity_class.Vivienda;
import java.util.List;

public class Viviendas {

    private  List<Vivienda> listaViviendas;

    public void setVivienda(Vivienda vivienda) {
        listaViviendas.add(vivienda);
    }
    
    @Override
    public String toString(){
        String texto = "";        
        for (int i = 0; i < listaViviendas.size(); i++) {
            texto += listaViviendas.get(i).toString()+ ".\n";               
        }
        return texto;
    }
    
    public int getSize(){
       return listaViviendas.size();
    }
    
    public Vivienda getVivienda(int posicion){
        return listaViviendas.get(posicion);
    }

    public List<Vivienda> getListaViviendas() {
        return listaViviendas;
    }

    public void setListaViviendas(List<Vivienda> listaViviendas) {
        this.listaViviendas = listaViviendas;
    }
    
    
}
