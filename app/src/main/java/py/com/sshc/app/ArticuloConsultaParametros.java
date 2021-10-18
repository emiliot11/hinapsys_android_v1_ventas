package py.com.sshc.app;

/**
 * Created by Emilio on 10/06/15.
 */
public class ArticuloConsultaParametros {


    private String empresa;
    private String codigoPrecioLista;
    private String codigoSucursal;
    private String codigoArticulo;
    private String descripcionArticulo;
    private ClientAccountOnServer cuenta;

    public ArticuloConsultaParametros() {
        empresa = null;
        codigoPrecioLista = null;
        codigoSucursal = null;
        codigoArticulo = null;
        descripcionArticulo = null;
        cuenta = null;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getCodigoPrecioLista() {
        return codigoPrecioLista;
    }

    public void setCodigoPrecioLista(String codigoPrecioLista) {
        this.codigoPrecioLista = codigoPrecioLista;
    }

    public String getCodigoSucursal() {
        return codigoSucursal;
    }

    public void setCodigoSucursal(String codigoSucursal) {
        this.codigoSucursal = codigoSucursal;
    }

    public String getCodigoArticulo() {
        return codigoArticulo;
    }

    public void setCodigoArticulo(String codigoArticulo) {
        this.codigoArticulo = codigoArticulo;
    }

    public String getDescripcionArticulo() {
        return descripcionArticulo;
    }

    public void setDescripcionArticulo(String descripcionArticulo) {
        this.descripcionArticulo = descripcionArticulo;
    }

    public ClientAccountOnServer getCuenta() {
        return cuenta;
    }

    public void setCuenta(ClientAccountOnServer cuenta) {
        this.cuenta = cuenta;
    }
}
