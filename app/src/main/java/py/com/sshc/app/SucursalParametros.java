package py.com.sshc.app;

/**
 * Created by Emilio on 06/06/15.
 */
public class SucursalParametros {


    private String empresa;
    private String codigo;
    private String descripcion;
    private ClientAccountOnServer cuenta;



    public SucursalParametros() {

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
