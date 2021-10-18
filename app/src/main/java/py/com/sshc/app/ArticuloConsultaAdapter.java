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
 * Created by Emilio on 10/06/15.
 */
public class ArticuloConsultaAdapter extends ArrayAdapter<ArticuloConsulta> {

    private Context context;
    private List<ArticuloConsulta> objects;

    public ArticuloConsultaAdapter(Context context, int resource, List<ArticuloConsulta> objects) {
        super(context, resource, objects);

        this.context = context;
        this.objects = objects;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ArticuloConsulta articuloConsulta = objects.get(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.item_articulo_consulta, null);

        TextView tv = (TextView) view.findViewById(R.id.textView_listaArticuloConsulta_codigo);
        tv.setText(articuloConsulta.getArticuloCodigo());

        TextView tv2 = (TextView) view.findViewById(R.id.textView_listaArticuloConsulta_descripcion);
        tv2.setText(articuloConsulta.getArticuloDescripcion());

        TextView tv3 = (TextView) view.findViewById(R.id.textView_listaArticuloConsulta_precio);
        tv3.setText(new Utiliario().formatearNumeroSinDecimales(articuloConsulta.getPrecio()));

        TextView tv4 = (TextView) view.findViewById(R.id.textView_listaArticuloConsulta_cantidadExistencia);
        tv4.setText(new Utiliario().formatearNumeroSinDecimales(articuloConsulta.getCantidadExistencia()));

        return view;
    }
}
