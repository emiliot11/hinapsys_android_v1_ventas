package py.com.sshc.app;

/**
 * Created by Emilio on 16/06/15.
 */
public class GeoTagDocParametros {

    private String empresa;
    private GeoTagDoc geoTagDoc ;
    private ClientAccountOnServer cuenta;

    public GeoTagDocParametros() {
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public GeoTagDoc getGeoTagDoc() {
        return geoTagDoc;
    }

    public void setGeoTagDoc(GeoTagDoc geoTagDoc) {
        this.geoTagDoc = geoTagDoc;
    }

    public ClientAccountOnServer getCuenta() {
        return cuenta;
    }

    public void setCuenta(ClientAccountOnServer cuenta) {
        this.cuenta = cuenta;
    }
}
