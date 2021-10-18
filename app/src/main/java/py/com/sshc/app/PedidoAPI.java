package py.com.sshc.app;


import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by Emilio on 12/06/15.
 */
public interface PedidoAPI {


    @POST("/Pedido")
    public void setPedido(@Body PedidoParametro pedidoParametro, Callback<List<PedidoRESP>> respuesta);


}
