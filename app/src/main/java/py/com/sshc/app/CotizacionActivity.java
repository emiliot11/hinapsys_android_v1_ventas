package py.com.sshc.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class CotizacionActivity extends Activity implements CotizacionFragment.Callbacks, ClientesDatosDialogFragment.Callbacks {

    Menu menuGuardar;
    CotizacionFragment frag = new CotizacionFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {

            getFragmentManager().beginTransaction()
                    .add(R.id.framePedidos, frag, "COTIZACION")
                    .commit();


        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.pedidos, menu);

        menuGuardar = menu;

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }

        if (id == R.id.action_settings_pedidos_guardar) {

            if("NUEVO".equals(item.getTitle())) {
                //NUEVO

                getFragmentManager().beginTransaction().remove(frag).commit();

                frag = new CotizacionFragment();

                getFragmentManager().beginTransaction()
                        .add(R.id.framePedidos, frag)
                        .commit();

                menuGuardar.findItem(R.id.action_settings_pedidos_guardar).setTitle("GUARDAR");


            } else
            {
                //GUARDAR
                frag.enviarPedido();
            }



        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void prepararNuevo() {

        menuGuardar.findItem(R.id.action_settings_pedidos_guardar).setTitle("NUEVO");
        frag.elegirClienteBoton.setEnabled(false);
        frag.spinnerPreciosLista.setEnabled(false);
        frag.elegirArticulosBoton.setEnabled(false);

    }

    @Override
    public void onDatosCargados(ClienteDato clienteDato) {
        frag.setClienteDato(clienteDato);
    }
}


