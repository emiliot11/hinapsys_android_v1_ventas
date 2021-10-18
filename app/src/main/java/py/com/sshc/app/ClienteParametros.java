package py.com.sshc.app;

/**
 * Created by Emilio on 17/09/2014.
 */
public class ClienteParametros {

    private String empresa;
    private String codigo;
    private String descripcion;
    private ClientAccountOnServer cuenta;



    public ClienteParametros() {
        empresa = null;
        codigo = null;
        descripcion = null;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


    public ClientAccountOnServer getCuenta() {
        return cuenta;
    }

    public void setCuenta(ClientAccountOnServer cuenta) {
        this.cuenta = cuenta;
    }



}
