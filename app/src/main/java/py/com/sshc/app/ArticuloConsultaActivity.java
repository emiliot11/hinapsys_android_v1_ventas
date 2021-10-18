package py.com.sshc.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;


public class ArticuloConsultaActivity extends Activity implements ArticuloConsultaActivityListFragment.Callbacks {


    ArticuloConsulta ArticuloConsultaSeleccionado = null;
    String codigoBuscado = null;
    String descripcionBuscada = null;
    EditText et_codigoBuscado;
    EditText et_descripcionBuscada;
    ImageButton ib_buscar;
    ArticuloConsultaActivityListFragment frag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articulo_consulta);


        //OPCIONES
        getActionBar().setDisplayHomeAsUpEnabled(true);
        //getActionBar().setDisplayShowTitleEnabled(false);

        frag = new ArticuloConsultaActivityListFragment();

        Bundle b = getIntent().getBundleExtra(MainActivity.ELEGIR_ARTICULO_PEDIDO_BUNDLE);

        frag.setArguments(b);

        getFragmentManager().beginTransaction()
                .add(R.id.frameArticuloConsulta, frag)
                .commit();



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_buscar_codigo_descripcion, menu);
        final Menu m = menu;
        final MenuItem item = menu.findItem(R.id.action_encontrar_codigo_descripcion);
        et_codigoBuscado = (EditText) item.getActionView().findViewById(R.id.item_accion_buscar_codigo);
        et_codigoBuscado.setInputType(InputType.TYPE_CLASS_TEXT);
        et_codigoBuscado.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    buscarArticulo();

                    return true;
                }
                return false;
            }
        });


        et_descripcionBuscada = (EditText) item.getActionView().findViewById(R.id.item_accion_buscar_descripcion);
        et_descripcionBuscada.setInputType(InputType.TYPE_CLASS_TEXT);
        et_descripcionBuscada.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    buscarArticulo();

                    return true;
                }
                return false;
            }
        });



        ib_buscar = (ImageButton) item.getActionView().findViewById(R.id.item_accion_boton_buscar);
        ib_buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                buscarArticulo();

            }
        });
        return true;
    }

    private void buscarArticulo() {

        frag.buscarArticuloConsulta(et_codigoBuscado.getText().toString(), et_descripcionBuscada.getText().toString());

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(et_codigoBuscado.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(et_descripcionBuscada.getWindowToken(), 0);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(ArticuloConsulta articuloConsulta) {


        ArticuloConsultaSeleccionado = articuloConsulta;
        if (ArticuloConsultaSeleccionado != null) {

            Intent intent = new Intent();
            intent.putExtra("COD", articuloConsulta.getArticuloCodigo());
            intent.putExtra("DES", articuloConsulta.getArticuloDescripcion());
            intent.putExtra("PRECIO", articuloConsulta.getPrecio());
            setResult(RESULT_OK, intent);
            finish();

        }



    }
}
