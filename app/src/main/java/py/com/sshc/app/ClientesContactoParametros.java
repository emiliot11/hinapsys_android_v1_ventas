package py.com.sshc.app;

/**
 * Created by Emilio on 16/06/15.
 */
public class ClientesContactoParametros {

    private String empresa;
    private ClientesContacto clientesContacto;
    private ClientAccountOnServer cuenta;

    public ClientesContactoParametros() {
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public ClientesContacto getClientesContacto() {
        return clientesContacto;
    }

    public void setClientesContacto(ClientesContacto clientesContacto) {
        this.clientesContacto = clientesContacto;
    }

    public ClientAccountOnServer getCuenta() {
        return cuenta;
    }

    public void setCuenta(ClientAccountOnServer cuenta) {
        this.cuenta = cuenta;
    }
}
