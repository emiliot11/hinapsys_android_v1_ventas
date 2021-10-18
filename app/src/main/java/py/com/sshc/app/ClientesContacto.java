package py.com.sshc.app;

import java.util.List;

/**
 * Created by Emilio on 16/06/15.
 */
public class ClientesContacto {

    private String codigoCliente;
    private String codigoContactoTipo;
    private String contacto;
    private String comentarios;
    private List<ClientesAccion> acciones;

    public ClientesContacto() {
    }

    public String getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(String codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    public String getCodigoContactoTipo() {
        return codigoContactoTipo;
    }

    public void setCodigoContactoTipo(String codigoContactoTipo) {
        this.codigoContactoTipo = codigoContactoTipo;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public List<ClientesAccion> getAcciones() {
        return acciones;
    }

    public void setAcciones(List<ClientesAccion> acciones) {
        this.acciones = acciones;
    }
}
