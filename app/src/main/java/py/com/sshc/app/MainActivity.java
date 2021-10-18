package py.com.sshc.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class MainActivity extends Activity implements MenuFragment.OnFragmentInteractionListener {

    public static String AUTENTICACION_BUNDLE = "AUTENTICACION_BUNDLE";
    public static String CLIENTE_BUNDLE = "CLIENTE_BUNDLE";
    public static String CLIENTE_CODIGO = "CLIENTE_CODIGO";

    public static String ELEGIR_ARTICULO_PEDIDO_BUNDLE = "ELEGIR_ARTICULO_PEDIDO_BUNDLE";
    public static String PRECIOLISTA_CODIGO = "PRECIOLISTA_CODIGO";
    public static String SUCURSAL_CODIGO = "SUCURSAL_CODIGO";

    public static final String TAG = "SSHC";
    NetworkUtility networkUtility = new NetworkUtility();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MenuFragment menufrag = new MenuFragment();

        getFragmentManager().beginTransaction()
                .add(R.id.contenedorPrincipal,menufrag)
                .commit();



    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {


            Intent intent = new Intent(this,PrefGeneralActivity.class);
            startActivity(intent);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void ejecutarTest(MenuItem item){

        Toast.makeText(this, new PreferencesManager(this).getServidorConServicio(), Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onFragmentInteraction(String id) {


        //Toast.makeText(this,id,Toast.LENGTH_LONG).show();

        switch (id) {
            case "1":

//                Bundle b = new Bundle();
//
//                b.putString(USUARIO_KEY,"dba");
//                b.putString(CLAVE_KEY,"123");
//
                if (!networkUtility.comprobarConexionConMensaje(this)) {return;};
//
                Intent i0 = new Intent(this,CotizacionActivity.class);
//
//                i.putExtra(AUTENTICACION_BUNDLE,b);

                startActivity(i0);


                break;

            case "2":

//                Bundle b = new Bundle();
//
//                b.putString(USUARIO_KEY,"dba");
//                b.putString(CLAVE_KEY,"123");
//
                if (!networkUtility.comprobarConexionConMensaje(this)) {return;};
//
                Intent i = new Intent(this,PedidosActivity.class);
//
//                i.putExtra(AUTENTICACION_BUNDLE,b);

                startActivity(i);


                break;

            case "3":

                if (!networkUtility.comprobarConexionConMensaje(this)) {return;};
//
                Intent i2 = new Intent(this,ClientesContactoActivity.class);
//
//                i.putExtra(AUTENTICACION_BUNDLE,b);

                startActivity(i2);

                break;

            case "4":

                if (!networkUtility.comprobarConexionConMensaje(this)) {return;};
//
                Intent i3 = new Intent(this,EncuestaActivity.class);
//
//                i.putExtra(AUTENTICACION_BUNDLE,b);

                startActivity(i3);

                break;

            case "5":

                if (!networkUtility.comprobarConexionConMensaje(this)) {return;};

                Intent i4 = new Intent(this,StockActivity.class);

                //i.putExtra(AUTENTICACION_BUNDLE,b);

                startActivity(i4);

                break;

            case "6":

                if (!networkUtility.comprobarConexionConMensaje(this)) {return;};

                Intent i5 = new Intent(this,MapaActivity.class);

                //i.putExtra(AUTENTICACION_BUNDLE,b);

                startActivity(i5);

                break;

            default:
                break;
        }



    }
}
