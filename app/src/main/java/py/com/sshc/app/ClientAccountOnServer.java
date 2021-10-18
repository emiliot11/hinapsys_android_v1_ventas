package py.com.sshc.app;

/**
 * Created by Emilio on 17/09/2014.
 */
public class ClientAccountOnServer {

    private String usuario;
    private String clave;
    private String version;

    public ClientAccountOnServer() {
    }

    public ClientAccountOnServer(String usuario, String clave, String version) {
        this.usuario = usuario;
        this.clave = clave;
        this.version = version;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
