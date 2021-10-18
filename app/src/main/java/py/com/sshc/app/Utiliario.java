package py.com.sshc.app;

import java.text.DecimalFormat;
import java.text.ParseException;

/**
 * Created by Emilio on 10/06/15.
 */
public class Utiliario {

    private DecimalFormat df;
    private DecimalFormat dfnd;
    private boolean hasFractionalPart;

    public Utiliario() {

        df = new DecimalFormat("#,###.##");
        df.setDecimalSeparatorAlwaysShown(true);
        dfnd = new DecimalFormat("#,###");
        hasFractionalPart = false;

    }

    public String formatearNumero(Double numero){

        if (numero.toString().contains(String.valueOf(df.getDecimalFormatSymbols().getDecimalSeparator())))
        {
            hasFractionalPart = true;
        } else {
            hasFractionalPart = false;
        }

        try {


            if (hasFractionalPart) {
               return df.format(numero);
            } else {
                return dfnd.format(numero);
            }

        } catch (NumberFormatException nfe) {
            // do nothing?
            return "";
        }

    }
    public String formatearNumeroSinDecimales(Double numero){


        try {

            return dfnd.format(numero);


        } catch (NumberFormatException nfe) {
            // do nothing?
            return "";
        }

    }

    public Double convertirNumeroFormateadoToDouble (String numeroFormateado){

        String v = numeroFormateado.replace(String.valueOf(df.getDecimalFormatSymbols().getGroupingSeparator()), "");
        try {
            if ("".equals((v))) {
                return null;
            }
            Number n = df.parse(v);
            return n.doubleValue();
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }

    }




}
