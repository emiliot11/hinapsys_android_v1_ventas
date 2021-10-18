package py.com.sshc.app;

/**
 * Created by Emilio on 07/06/15.
 */
public class PreciosLista {


    private String precioListaCodigo;
    private String precioListaDescripcion;
    private String predeterminado;


    public PreciosLista() {
    }

    public String getPrecioListaCodigo() {
        return precioListaCodigo;
    }

    public void setPrecioListaCodigo(String precioListaCodigo) {
        this.precioListaCodigo = precioListaCodigo;
    }

    public String getPrecioListaDescripcion() {
        return precioListaDescripcion;
    }

    public void setPrecioListaDescripcion(String precioListaDescripcion) {
        this.precioListaDescripcion = precioListaDescripcion;
    }

    public String getPredeterminado() {
        return predeterminado;
    }

    public void setPredeterminado(String predeterminado) {
        this.predeterminado = predeterminado;
    }
}
