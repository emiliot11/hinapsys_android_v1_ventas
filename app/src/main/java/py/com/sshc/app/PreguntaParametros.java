package py.com.sshc.app;

/**
 * Created by Emilio on 04/07/15.
 */
public class PreguntaParametros {


    private String empresa;
    private ClientAccountOnServer cuenta;

    public PreguntaParametros() {
    }

    public PreguntaParametros(String empresa, ClientAccountOnServer cuenta) {
        this.empresa = empresa;
        this.cuenta = cuenta;
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
