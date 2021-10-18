package py.com.sshc.app;

/**
 * Created by Emilio on 14/06/15.
 */
public class ClientesAccion {


    private String codigoClientesAccion;
    private String descripcionClientesAccion;
    private Boolean estado;

    public ClientesAccion() {
    }

    public ClientesAccion(String codigoClientesAccion, String descripcionClientesAccion) {
        this.codigoClientesAccion = codigoClientesAccion;
        this.descripcionClientesAccion = descripcionClientesAccion;
        this.estado = false;
    }

    public String getCodigoClientesAccion() {
        return codigoClientesAccion;
    }

    public void setCodigoClientesAccion(String codigoClientesAccion) {
        this.codigoClientesAccion = codigoClientesAccion;
    }

    public String getDescripcionClientesAccion() {
        return descripcionClientesAccion;
    }

    public void setDescripcionClientesAccion(String descripcionClientesAccion) {
        this.descripcionClientesAccion = descripcionClientesAccion;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }
}
