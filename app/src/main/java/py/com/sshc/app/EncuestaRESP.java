package py.com.sshc.app;

/**
 * Created by Emilio on 05/07/15.
 */
public class EncuestaRESP {

    private Boolean estado;
    private String mensaje;

    public EncuestaRESP() {

    }

    public EncuestaRESP(Boolean estado, String mensaje) {
        this.estado = estado;
        this.mensaje = mensaje;
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
