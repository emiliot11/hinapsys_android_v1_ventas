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
 * Created by Emilio on 05/06/15.
 */
public class SucursalAdapter extends ArrayAdapter<Sucursal> {

    private Context context;
    private List<Sucursal> objects;
    private Boolean usarMinimalLista = false;

    public SucursalAdapter(Context context, int resource, List<Sucursal> objects) {
        super(context, resource, objects);

        this.context = context;
        this.objects = objects;

    }

    public SucursalAdapter(Context context, int resource, List<Sucursal> objects, boolean usarMinimalLista) {
        super(context, resource, objects);

        this.context = context;
        this.objects = objects;
        this.usarMinimalLista = usarMinimalLista;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {




        Sucursal sucursal = objects.get(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        View view;
        if (!usarMinimalLista) {
            view = inflater.inflate(R.layout.item_sucursal, null);
        }
        else
        {
            view = inflater.inflate(R.layout.item_sucursal_min, null);
        }

        TextView tv = (TextView) view.findViewById(R.id.textView_listaSucursal_codigo);

        tv.setText(sucursal.getSucursalCodigo());

        TextView tv2 = (TextView) view.findViewById(R.id.textView_listaSucursal_descripcion);

            tv2.setText(sucursal.getSucursalDescripcion());



        return view;

    }

    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {


        Sucursal sucursal = objects.get(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.item_sucursal, null);

        TextView tv = (TextView) view.findViewById(R.id.textView_listaSucursal_codigo);

        tv.setText(sucursal.getSucursalCodigo());

        TextView tv2 = (TextView) view.findViewById(R.id.textView_listaSucursal_descripcion);

        tv2.setText(sucursal.getSucursalDescripcion());



        return view;

    }
}
