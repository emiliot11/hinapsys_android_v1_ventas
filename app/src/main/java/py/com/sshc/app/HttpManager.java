package py.com.sshc.app;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Emilio on 16/09/2014.
 */
public class HttpManager {

    public static String getData(String uri) {

        BufferedReader reader = null;

        try {
            URL url = new URL(uri);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            StringBuilder sb = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(con.getInputStream(),"UTF-8"));

            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }

            return sb.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }

    }

    public static String getDataDesdeServidorPrincipal(Activity activity, String uri){

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(activity);
        String servidor = pref.getString("servidor","");
        String WebServerName = "/SuperspumaServices4Android";
        if ("".equals(uri) == false && "".equals(servidor) == false) {
            return getData("http://" + servidor +  WebServerName + uri);

        } else {
            return null;
        }

    }


}
