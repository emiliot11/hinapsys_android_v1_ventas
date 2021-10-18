package py.com.sshc.app;

/**
 * Created by Emilio on 04/07/15.
 */
import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;


public interface PreguntaAPI {


    @POST("/Preguntas")
    public void getPreguntas(@Body PreguntaParametros cliente, Callback<List<Pregunta>> respuesta);

}
