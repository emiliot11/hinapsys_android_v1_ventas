package py.com.sshc.app;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by Emilio on 13/06/15.
 */
public interface ContactosTipoAPI  {

    @POST("/ContactosTipo")
    public void getContactosTipo(@Body ContactosTipoParametros contactosTipoParametros, Callback<List<ContactosTipo>> respuesta);

}
