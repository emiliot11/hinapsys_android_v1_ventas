package py.com.sshc.app;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by Emilio on 16/06/15.
 */
public interface ClientesContactoAPI {

    @POST("/ClientesContacto")
    public void guardarContactosCliente(@Body ClientesContactoParametros clientesContactoParametros, Callback<List<ClienteContactoRESP>> respuesta);


}
