package comunidad;

import java.math.BigDecimal;

public class Values {
    
    public static final int TAM_NOMBRE_COMUNIDAD = 30;
    public static final int TAM_DIRECCION_COMUNIDAD = 60;
    
    public static final int TAM_NUM_VIVIENDA = 4;
    public static final int TAM_PROP_VIVIENDA = 50;
    public static final int TAM_NUM_CUENTA_VIVIENDA = 50;
    
    public static final int TAM_APELLIDOS_PROP = 50;
    public static final int TAM_NOMBRE_PROP = 30;
    public static final int TAM_EMAIL_PROP = 60;
    public static final int TAM_TELEF_PROP = 13;
    public static final int TAM_DNI_PROP = 10;
    
    public static final int TAM_CUOTA_CUOTAS = 7;
    public static final int TAM_TOTAL_CUOTAS = 9;
    
    public static final int TAM_IVA = 4;
    
    
    public static BigDecimal toBigDecimal(String s){        
        return BigDecimal.valueOf(Double.valueOf(s));
    }
}
