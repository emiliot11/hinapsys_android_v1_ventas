package py.com.sshc.app;


import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;
/**
 * Created by Emilio on 10/06/15.
 */
public interface ArticuloConsultaAPI {

    @POST("/articulos_consultas")
    public void getArticuloConsulta(@Body ArticuloConsultaParametros articuloConsulta, Callback<List<ArticuloConsulta>> respuesta);


}
