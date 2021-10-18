package py.com.sshc.app;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;


public class ClientesContactoActivity extends Activity implements ClientesAccionDialogFragment.Callbacks {

    ClientesContactoActivityFragment frag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientes_contacto);

        //OPCIONES
        getActionBar().setDisplayHomeAsUpEnabled(true);
        //getActionBar().setDisplayShowTitleEnabled(false);

        frag = new ClientesContactoActivityFragment();
        getFragmentManager().beginTransaction()
                .add(R.id.frameClientesContacto, frag)
                .commit();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_clientes_contacto, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.action_clientes_contacto_guardar){
            frag.guardarCrm();
        }

        return super.onOptionsItemSelected(item);
    }

    public void doPositiveClick(List<ClientesAccion> clientesAccions) {
        // Do stuff here.
        Log.i("Fragment Alert Dialog", "Positive click!");
        frag.setListaAcciones(clientesAccions);
    }

    public void doNegativeClick() {
        // Do stuff here.
        Log.i("Fragment Alert Dialog", "Negative click!");
    }

    public void actualizarAccionesDescripcion(){
        frag.actualizarAccionesDescripcion();
    }

    public void limpiar(){

        getFragmentManager().beginTransaction().remove(frag).commit();

        frag = new ClientesContactoActivityFragment();

        getFragmentManager().beginTransaction()
                .add(R.id.frameClientesContacto, frag)
                .commit();

    }

}
