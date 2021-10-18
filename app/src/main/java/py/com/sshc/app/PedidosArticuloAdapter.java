package py.com.sshc.app;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Emilio on 07/06/15.
 */
public class PedidosArticuloAdapter extends ArrayAdapter<PedidosArticulo> {

    private Context context;
    private List<PedidosArticulo> objects;


    public PedidosArticuloAdapter(Context context, int resource, List<PedidosArticulo> objects) {
        super(context, resource, objects);

        this.context = context;
        this.objects = objects;


    }

    public List<PedidosArticulo> getObjects() {
        return objects;
    }


    public void actualizarNumero (){

        Integer posicion = 0;

        for (PedidosArticulo pedidosArticulo : objects) {
            posicion++;
            pedidosArticulo.setNumero(posicion);
        }

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        PedidosArticulo pedidosArticulo = objects.get(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        Utiliario utiliario = new Utiliario();

        View view = inflater.inflate(R.layout.item_pedidos_articulo, null);

        TextView tv = (TextView) view.findViewById(R.id.textView_listaPedidosArticulo_codigo);
        tv.setText(pedidosArticulo.getCodigo());

        TextView tv2 = (TextView) view.findViewById(R.id.textView_listaPedidosArticulo_descripcion);
        tv2.setText(pedidosArticulo.getDescripcion());

        TextView tv3 = (TextView) view.findViewById(R.id.textView_listaPedidosArticulo_cantidad);
        tv3.setText(String.valueOf(pedidosArticulo.getCantidad()));

        TextView tv4 = (TextView) view.findViewById(R.id.textView_listaPedidosArticulo_precio);
        tv4.setText(utiliario.formatearNumeroSinDecimales(pedidosArticulo.getPrecio()));

        TextView tv5 = (TextView) view.findViewById(R.id.textView_listaPedidosArticulo_porcDescuento);
        tv5.setText(utiliario.formatearNumero(pedidosArticulo.getPorcDescuento()) + "%");

        TextView tv6 = (TextView) view.findViewById(R.id.textView_listaPedidosArticulo_total);
        tv6.setText(utiliario.formatearNumeroSinDecimales(pedidosArticulo.getTotal()));

        return view;

    }

}
