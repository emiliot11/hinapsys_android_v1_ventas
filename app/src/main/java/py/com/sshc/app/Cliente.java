package py.com.sshc.app;

/**
 * Created by Emilio on 16/09/2014.
 */
public class Cliente {

    private String clienteCodigo;
    private String clienteDescripcion;
    private String clienteSucursal;
    private String clienteEsVarios;

    public Cliente(){

    }

    public String getClienteSucursal() {
        return clienteSucursal;
    }

    public void setClienteSucursal(String clienteSucursal) {
        this.clienteSucursal = clienteSucursal;
    }

    public String getClienteCodigo() {
        return clienteCodigo;
    }

    public void setClienteCodigo(String clienteCodigo) {
        this.clienteCodigo = clienteCodigo;
    }

    public String getClienteDescripcion() {
        return clienteDescripcion;
    }

    public void setClienteDescripcion(String clienteDescripcion) {
        this.clienteDescripcion = clienteDescripcion;
    }

    public String getClienteEsVarios() {
        return clienteEsVarios;
    }

    public void setClienteEsVarios(String clienteEsVarios) {
        this.clienteEsVarios = clienteEsVarios;
    }
}
