package py.com.sshc.app;

/**
 * Created by Emilio on 16/06/15.
 */
public class GeoTagDocRESP {

    private Boolean estado;
    private String mensaje;


    public GeoTagDocRESP() {
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
