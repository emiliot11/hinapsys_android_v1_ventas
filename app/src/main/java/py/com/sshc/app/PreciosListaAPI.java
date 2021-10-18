package py.com.sshc.app;


import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;


/**
 * Created by Emilio on 07/06/15.
 */
public interface PreciosListaAPI {


    @POST("/PreciosLista")
    public void getPreciosLista(@Body PreciosListaParametros preciosLista, Callback<List<PreciosLista>> respuesta);

}
