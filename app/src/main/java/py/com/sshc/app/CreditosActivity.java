package py.com.sshc.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageButton;


public class CreditosActivity extends Activity implements CreditosListFragment.Callbacks {

    Credito creditoSeleccionado = null;
    String codigoBuscado = null;
    String descripcionBuscada = null;
    EditText et_codigoBuscado;
    EditText et_descripcionBuscada;
    ImageButton ib_buscar;
    CreditosListFragment frag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creditos);

        //OPCIONES
        getActionBar().setDisplayHomeAsUpEnabled(true);
        //getActionBar().setDisplayShowTitleEnabled(false);

        //INICIALIZAR
        frag = new CreditosListFragment();

        //SETEAR ARGUMENTOS
        Bundle b = getIntent().getBundleExtra(MainActivity.CLIENTE_BUNDLE);
        frag.setArguments(b);

        getFragmentManager().beginTransaction()
                .add(R.id.frameCreditos, frag)
                .commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.creditos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onItemSelected(Credito credito) {
        creditoSeleccionado = credito;
        if (creditoSeleccionado != null) {

            Intent intent = new Intent();
            intent.putExtra("COD", creditoSeleccionado.getCreditoCodigo());
            intent.putExtra("DES", creditoSeleccionado.getCreditoDescripcion());
            setResult(RESULT_OK, intent);
            finish();

        }
    }
}
