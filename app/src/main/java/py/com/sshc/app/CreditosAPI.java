package py.com.sshc.app;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by Emilio on 17/09/2014.
 */
public interface CreditosAPI {


    @POST("/Creditos")
    public void getCreditos(@Body CreditoParametros credito, Callback<List<Credito>> respuesta);

}
