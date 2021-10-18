package py.com.sshc.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class PedidosArticuloDetailActivity extends Activity {

    PedidosArticuloDetailActivityFragment frag;
    PedidosArticulo pedidosArticulo;
    Integer nroItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos_articulo_detail);

        //OPCIONES
        getActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle b = getIntent().getBundleExtra(MainActivity.ELEGIR_ARTICULO_PEDIDO_BUNDLE);
        pedidosArticulo = getIntent().getParcelableExtra("ITEM");
        nroItems = getIntent().getIntExtra("NRO_ITEMS",0);

        b.putParcelable("ITEM",pedidosArticulo);
        if (nroItems != null) {
            b.putInt("NRO_ITEMS",nroItems);
        }

        frag = new PedidosArticuloDetailActivityFragment();

        frag.setArguments(b);

        getFragmentManager().beginTransaction()
                .add(R.id.framePedidosArticuloDetail, frag)
                .commit();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pedidos_articulo_detail, menu);
        return true;
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

        if (id == R.id.action_pedidos_articulo_insertar) {

            insertarItem();

        }

        return super.onOptionsItemSelected(item);
    }

    private void insertarItem() {

        Integer numero = Integer.valueOf(frag.textView_numero.getText().toString());
        String codigoArticulo = frag.editText_codigoArticulo.getText().toString();
        String descripcionActiculo = frag.button_articulo.getText().toString();
        Integer cantidad = frag.numberPicker_cantidad.getValue();
        Double porcDecuento = Double.valueOf(frag.editText_porcDescuento.getText().toString());
        Double precio = new Utiliario().convertirNumeroFormateadoToDouble(frag.editText_precio.getText().toString());
        Double total = new Utiliario().convertirNumeroFormateadoToDouble(frag.textView_total.getText().toString());

        if (numero == null) { numero = 0; }
        if (cantidad == null) { cantidad = 0; }
        if (precio == null) { precio = 0.00; }
        if (total == null) { total = 0.00; }

        if ((numero == 0)
                || ("".equals(codigoArticulo))
                || ("".equals(descripcionActiculo))
                || (cantidad == 0)
                || (precio == 0)
                || (total == 0)
        ) {
            Toast.makeText(this, "No ha completado todos los datos necesarios.", Toast.LENGTH_SHORT).show();
            return;
        }

        PedidosArticulo pedidoArticulo = new PedidosArticulo(numero,codigoArticulo,descripcionActiculo,cantidad.doubleValue(),precio,porcDecuento.doubleValue(),total);

        Intent intent = new Intent();
        intent.putExtra("ITEM", pedidoArticulo);
        setResult(RESULT_OK, intent);
        finish();


    }


}
