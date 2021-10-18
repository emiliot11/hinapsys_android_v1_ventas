package py.com.sshc.app;

import java.util.List;

        import retrofit.Callback;
        import retrofit.http.Body;
        import retrofit.http.POST;

/**
 * Created by Emilio on 17/09/2014.
 */
public interface ClientesAPI {


    @POST("/Clientes")
    public void getClientes(@Body ClienteParametros cliente, Callback<List<Cliente>> respuesta);

}
