package py.com.sshc.app;

/**
 * Created by Emilio on 14/06/15.
 */
public class ClientesAccionParametros {

    private String empresa;
    private ClientAccountOnServer cuenta;

    public ClientesAccionParametros(){
        empresa = null;
        cuenta = null;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public ClientAccountOnServer getCuenta() {
        return cuenta;
    }

    public void setCuenta(ClientAccountOnServer cuenta) {
        this.cuenta = cuenta;
    }
}
