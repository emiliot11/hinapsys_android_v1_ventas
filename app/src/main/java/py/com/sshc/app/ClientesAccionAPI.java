package py.com.sshc.app;

import java.util.List;

import retrofit.http.POST;
import retrofit.Callback;
import retrofit.http.Body;

/**
 * Created by Emilio on 14/06/15.
 */
public interface ClientesAccionAPI {

    @POST("/ClientesAccion")
    public void getClientesAccion(@Body ClientesAccionParametros parametros,Callback<List<ClientesAccion>> respuesta );


}
