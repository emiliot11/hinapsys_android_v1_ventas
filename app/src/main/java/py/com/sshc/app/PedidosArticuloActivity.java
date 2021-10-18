package py.com.sshc.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;


public class PedidosArticuloActivity extends Activity implements PedidosArticuloListFragment.Callbacks {

    public final int REQUEST_PEDIDO_ARTICULO = 100;

    PedidosArticuloListFragment frag;
    String codigoPrecioLista;
    String codigoSucursal;
    Bundle b;
    ArrayList<PedidosArticulo> pedidosArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos_articulo);

        //OPCIONES
        getActionBar().setDisplayHomeAsUpEnabled(true);
        //getActionBar().setDisplayShowTitleEnabled(false);

        b = getIntent().getBundleExtra(MainActivity.ELEGIR_ARTICULO_PEDIDO_BUNDLE);
        if (b != null) {
            codigoPrecioLista = b.getString(MainActivity.PRECIOLISTA_CODIGO);
            codigoSucursal =  b.getString(MainActivity.SUCURSAL_CODIGO);
        }

        pedidosArray = getIntent().getParcelableArrayListExtra("ITEMS");

        if (pedidosArray != null) {

            b.putParcelableArrayList("ITEMS",pedidosArray);

        }

        frag = new PedidosArticuloListFragment();

        frag.setArguments(b);

        getFragmentManager().beginTransaction()
                .add(R.id.framePedidosArticulo, frag)
                .commit();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pedidos_articulo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_pedidos_articulo_agregar) {

            agregarArticulo();

            return true;
        }

        if (id == R.id.action_pedidos_articulo_guardar) {

            guardarArticulos();

            return true;
        }

        if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void guardarArticulos() {

        List<PedidosArticulo> pedidos;

        pedidos = frag.adapter.getObjects();

        pedidosArray = new ArrayList<PedidosArticulo>(pedidos);

        Intent intent = new Intent();
        if (pedidos.size() > 0)  {
            intent.putParcelableArrayListExtra("ITEMS", pedidosArray);
        }

        setResult(RESULT_OK, intent);
        finish();


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == REQUEST_PEDIDO_ARTICULO && resultCode == this.RESULT_OK) {


            PedidosArticulo pedidosArticulo = data.getParcelableExtra("ITEM");

            frag.adapter.add(pedidosArticulo);


        }
    }

    public void agregarArticulo(){

        Intent intent = new Intent(this,PedidosArticuloDetailActivity.class);

        intent.putExtra(MainActivity.ELEGIR_ARTICULO_PEDIDO_BUNDLE, b);

        intent.putExtra("NRO_ITEMS",frag.adapter.getObjects().size());

        startActivityForResult(intent, REQUEST_PEDIDO_ARTICULO);
    }
}
