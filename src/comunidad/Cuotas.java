package comunidad;

import entity_class.Cuota;
import java.math.BigDecimal;
import java.util.List;

public class Cuotas {

    private List<Cuota> listaCuotas;

    public void setCuota(Cuota cuota) {
        listaCuotas.add(cuota);
    }

    @Override
    public String toString() {
        String texto = "";
        for (int i = 0; i < listaCuotas.size(); i++) {
            texto += listaCuotas.get(i).toString() + ".\n";
        }
        return texto;
    }

    public int getSize() {
        return listaCuotas.size();
    }

    public Cuota getCuota(int posicion) {
        return listaCuotas.get(posicion);
    }

    public List<Cuota> getListaCuotas() {
        return listaCuotas;
    }

    public void setListaCuotas(List<Cuota> listaCuotas) {
        this.listaCuotas = listaCuotas;
    }

    public static BigDecimal calcularTotal(BigDecimal agua, BigDecimal luz, BigDecimal basura,
            BigDecimal zonasComunes, BigDecimal otros) {
        
        BigDecimal total = new BigDecimal(0);
        total = total.add(agua);
        total = total.add(luz);
        total = total.add(basura);
        total = total.add(zonasComunes);
        total = total.add(otros);
        return total;
    }
}
