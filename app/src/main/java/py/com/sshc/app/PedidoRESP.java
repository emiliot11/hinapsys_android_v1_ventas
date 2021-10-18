package py.com.sshc.app;

/**
 * Created by Emilio on 12/06/15.
 */
public class PedidoRESP {


    private Boolean estado;
    private String mensaje;
    private String estadoAutorizacion;
    private String historial;

    public PedidoRESP() {
    }

    public PedidoRESP(Boolean estado, String mensaje, String estadoAutorizacion, String historial) {
        this.estado = null;
        this.mensaje = null;
        this.estadoAutorizacion = null;
        this.historial = null;
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
    public String getEstadoAutorizacion() {
        return estadoAutorizacion;
    }
    public void setEstadoAutorizacion(String estadoAutorizacion) {
        this.estadoAutorizacion = estadoAutorizacion;
    }
    public String getHistorial() {
        return historial;
    }

    public void setHistorial(String historial) {
        this.historial = historial;
    }
}
