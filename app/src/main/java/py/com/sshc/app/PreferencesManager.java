package py.com.sshc.app;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;

/**
 * Created by Emilio on 17/09/2014.
 */
public class PreferencesManager {

    SharedPreferences pref;


    public PreferencesManager(Activity activity) {
        pref = PreferenceManager.getDefaultSharedPreferences(activity);
    }

    public String getServidor(){

        String prefijo;
        if (pref.getBoolean("usarssl",false)) {
            prefijo = "https://";
        } else {
            prefijo = "http://";
        }



        return prefijo + pref.getString("url","") + ":"  + pref.getString("puerto","80");

    }

    public String getServidorConServicio(){
        String WebServerName = "/sshc_web_server_war";


        return getServidor() + WebServerName;


    }

    public ClientAccountOnServer getCuenta(Context context){

        String version = null;

        try {
            Integer versionNumber = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
            version = versionNumber.toString();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        return new ClientAccountOnServer(pref.getString("usuario",""),pref.getString("clave",""),version);

    }

}
