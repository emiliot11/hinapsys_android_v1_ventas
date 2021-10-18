package py.com.sshc.app;

/**
 * Created by Emilio on 05/07/15.
 */
public class EncuestaParametros {

    private String empresa;
    private Encuestas encuestas;
    private ClientAccountOnServer cuenta;

    public EncuestaParametros() {
        this.empresa = null;
        this.encuestas = null;
        this.cuenta = null;

    }

    public EncuestaParametros(String empresa, Encuestas encuestas, ClientAccountOnServer cuenta) {
        this.empresa = empresa;
        this.encuestas = encuestas;
        this.cuenta = cuenta;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public Encuestas getEncuestas() {
        return encuestas;
    }

    public void setEncuestas(Encuestas encuestas) {
        this.encuestas = encuestas;
    }

    public ClientAccountOnServer getCuenta() {
        return cuenta;
    }

    public void setCuenta(ClientAccountOnServer cuenta) {
        this.cuenta = cuenta;
    }
}
