package py.com.sshc.app;

/**
 * Created by Emilio on 16/09/2014.
 */
public class Credito {

    private String creditoCodigo;
    private String creditoDescripcion;

    public Credito(){

    }

    public String getCreditoCodigo() {
        return creditoCodigo;
    }

    public void setCreditoCodigo(String clienteCodigo) {
        this.creditoCodigo = clienteCodigo;
    }

    public String getCreditoDescripcion() {
        return creditoDescripcion;
    }

    public void setCreditoDescripcion(String clienteDescripcion) {
        this.creditoDescripcion = clienteDescripcion;
    }
}
