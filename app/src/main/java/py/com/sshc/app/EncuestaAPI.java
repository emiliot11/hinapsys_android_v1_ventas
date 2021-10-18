package py.com.sshc.app;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;


/**
 * Created by Emilio on 05/07/15.
 */
public interface EncuestaAPI {


    @POST("/respuestaencuesta")
    public void setRespuestaEncuesta(@Body EncuestaParametros encuestaParametros, Callback<List<EncuestaRESP>> respuesta);
}
