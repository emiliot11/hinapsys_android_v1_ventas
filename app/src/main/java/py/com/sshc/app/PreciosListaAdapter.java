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
public class PreciosListaAdapter extends ArrayAdapter<PreciosLista> {

    private Context context;
    private List<PreciosLista> objects;
    private Boolean usarMinimalLista = false;

    public PreciosListaAdapter(Context context, int resource, List<PreciosLista> objects) {
        super(context, resource, objects);

        this.context = context;
        this.objects = objects;
    }

    public PreciosListaAdapter(Context context, int resource, List<PreciosLista> objects, Boolean usarMinimalLista) {
        super(context, resource, objects);

        this.context = context;
        this.objects = objects;
        this.usarMinimalLista = usarMinimalLista;
    }

    public int getCount(){
        return objects.size();
    }

    public PreciosLista getItem(int position){
        return objects.get(position);
    }

    public long getItemId(int position){
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // I created a dynamic TextView here, but you can reference your own  custom layout for each spinner item
        /*TextView label = new TextView(context);
        label.setTextColor(Color.BLACK);
        // Then you can get the current item using the values array (Users array) and the current position
        // You can NOW reference each method you has created in your bean object (User class)
        label.setText(objects.get(position).getPrecioListaDescripcion());

        // And finally return your dynamic (or custom) view for each spinner item
        return label;*/

        PreciosLista preciosLista = objects.get(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        View view;

        if (!usarMinimalLista) {
            view = inflater.inflate(R.layout.item_precioslista_spinner, null);
        }
        else
        {
            view = inflater.inflate(R.layout.item_precioslista_spinner_min, null);
        }

        TextView tv = (TextView) view.findViewById(R.id.textView_listaPrecioslista_codigo);
        tv.setText(preciosLista.getPrecioListaCodigo());

        TextView tv2 = (TextView) view.findViewById(R.id.textView_listaPrecioslista_descripcion);
        tv2.setText(preciosLista.getPrecioListaDescripcion());

        return view;

    }

    // And here is when the "chooser" is popped up
    // Normally is the same view, but you can customize it if you want
    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {


        /*TextView label = new TextView(context);
        //label.setTextColor(Color.BLACK);
        label.setText(objects.get(position).getPrecioListaDescripcion());

        return label;*/

        PreciosLista preciosLista = objects.get(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.item_precioslista_spinner, null);

        TextView tv = (TextView) view.findViewById(R.id.textView_listaPrecioslista_codigo);
        tv.setText(preciosLista.getPrecioListaCodigo());

        TextView tv2 = (TextView) view.findViewById(R.id.textView_listaPrecioslista_descripcion);
        tv2.setText(preciosLista.getPrecioListaDescripcion());

        return view;
    }



}
