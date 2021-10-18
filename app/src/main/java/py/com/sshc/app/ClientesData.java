package py.com.sshc.app;

import java.util.List;

import static py.com.sshc.app.ClientesJSONParser.obtenerListaClientesDesdeJSON;

/**
 * Created by Emilio on 16/09/2014.
 */
public class ClientesData {



    public ClientesData(){

    }

    public List<Cliente> obtenerClientesLista(String content){

        List<Cliente> clientesLista = obtenerListaClientesDesdeJSON(content);

        return clientesLista;


    }



}
