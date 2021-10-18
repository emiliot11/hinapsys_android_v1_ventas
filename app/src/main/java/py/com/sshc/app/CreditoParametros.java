package py.com.sshc.app;

/**
 * Created by Emilio on 17/09/2014.
 */
public class CreditoParametros {

    private String empresa;
    private String codigo;
    private ClientAccountOnServer cuenta;



    public CreditoParametros() {
        empresa = null;
        codigo = null;
        cuenta = null;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public ClientAccountOnServer getCuenta() {
        return cuenta;
    }

    public void setCuenta(ClientAccountOnServer cuenta) {
        this.cuenta = cuenta;
    }



}
