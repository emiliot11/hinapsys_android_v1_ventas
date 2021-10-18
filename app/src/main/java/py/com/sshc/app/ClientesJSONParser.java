package py.com.sshc.app;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Emilio on 16/09/2014.
 */
public class ClientesJSONParser {

    public static List<Cliente> obtenerListaClientesDesdeJSON(String content) {

        try {
            JSONArray ar = new JSONArray(content);
            List<Cliente> clientesLista = new ArrayList<Cliente>();

            for (int i = 0; i < ar.length(); i++) {

                JSONObject obj = ar.getJSONObject(i);
                Cliente cliente = new Cliente();

                cliente.setClienteCodigo(obj.getString("COD"));
                cliente.setClienteDescripcion(obj.getString("DES"));

                clientesLista.add(cliente);
            }

            return clientesLista;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }

}
