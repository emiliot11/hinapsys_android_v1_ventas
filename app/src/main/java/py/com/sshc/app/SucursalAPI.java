package py.com.sshc.app;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by Emilio on 06/06/15.
 */
public interface SucursalAPI {


    @POST("/Sucursales")
    public void getSucursales(@Body SucursalParametros sucursal, Callback<List<Sucursal>> respuesta);



}
