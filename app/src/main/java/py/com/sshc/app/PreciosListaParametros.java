package py.com.sshc.app;

/**
 * Created by Emilio on 17/09/2014.
 */
public class PreciosListaParametros {

    private String empresa;
    private String cliente;
    private ClientAccountOnServer cuenta;



    public PreciosListaParametros() {
        empresa = null;
        cliente = null;
        cuenta = null;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public ClientAccountOnServer getCuenta() {
        return cuenta;
    }

    public void setCuenta(ClientAccountOnServer cuenta) {
        this.cuenta = cuenta;
    }



}
