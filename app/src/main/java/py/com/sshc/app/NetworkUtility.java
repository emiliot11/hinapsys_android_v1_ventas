package py.com.sshc.app;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * Created by Emilio on 12/06/15.
 */
public class NetworkUtility {


    public NetworkUtility() {
    }

    public boolean comprobarConexion(Context ctx){

        ConnectivityManager conMgr = (ConnectivityManager) ctx
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo i = conMgr.getActiveNetworkInfo();
        if ((i != null) && (i.isConnected()) && (i.isAvailable())) {
            return true;
        }
        return false;

    }

    public boolean comprobarConexionConMensaje(Context ctx){
        Boolean estado = comprobarConexion(ctx);
        if  (!estado) {
            Toast.makeText(ctx, "NO HAY CONEXIONES DISPONIBLES.", Toast.LENGTH_SHORT).show();
        }
        return estado;

    }

}
