package py.com.sshc.app;

/**
 * Created by Emilio on 10/06/15.
 */
public class ArticuloConsulta {

    private String articuloCodigo;
    private String articuloDescripcion;
    private Double cantidadExistencia;
    private Double precio;


    public ArticuloConsulta() {
    }

    public String getArticuloCodigo() {
        return articuloCodigo;
    }

    public void setArticuloCodigo(String articuloCodigo) {
        this.articuloCodigo = articuloCodigo;
    }

    public String getArticuloDescripcion() {
        return articuloDescripcion;
    }

    public void setArticuloDescripcion(String articuloDescripcion) {
        this.articuloDescripcion = articuloDescripcion;
    }

    public Double getCantidadExistencia() {
        return cantidadExistencia;
    }

    public void setCantidadExistencia(Double cantidadExistencia) {
        this.cantidadExistencia = cantidadExistencia;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }
}
