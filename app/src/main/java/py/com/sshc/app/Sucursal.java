package py.com.sshc.app;

/**
 * Created by Emilio on 05/06/15.
 */
public class Sucursal {


    private String sucursalCodigo;
    private String sucursalDescripcion;


    public Sucursal(){}


    public String getSucursalCodigo() {
        return sucursalCodigo;
    }

    public void setSucursalCodigo(String sucursalCodigo) {
        this.sucursalCodigo = sucursalCodigo;
    }

    public String getSucursalDescripcion() {
        return sucursalDescripcion;
    }

    public void setSucursalDescripcion(String sucursalDescripcion) {
        this.sucursalDescripcion = sucursalDescripcion;
    }
}
