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
 * Created by Emilio on 13/06/15.
 */
public class ContactosTipoAdapter extends ArrayAdapter<ContactosTipo> {

    private Context context;
    private List<ContactosTipo> objects;

    public ContactosTipoAdapter(Context context, int resource, List<ContactosTipo> objects) {
        super(context, resource, objects);

        this.context = context;
        this.objects = objects;


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ContactosTipo contactosTipo = objects.get(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.item_contactostipo, null);

        TextView tv = (TextView) view.findViewById(R.id.textView_listaContactosTipo_codigo);
        tv.setText(contactosTipo.getCodigoContactosTipo());

        TextView tv2 = (TextView) view.findViewById(R.id.textView_listaContactosTipo_descripcion);
        tv2.setText(contactosTipo.getDescripcionContactosTipo());

        return view;

    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        ContactosTipo contactosTipo = objects.get(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.item_contactostipo, null);

        TextView tv = (TextView) view.findViewById(R.id.textView_listaContactosTipo_codigo);
        tv.setText(contactosTipo.getCodigoContactosTipo());

        TextView tv2 = (TextView) view.findViewById(R.id.textView_listaContactosTipo_descripcion);
        tv2.setText(contactosTipo.getDescripcionContactosTipo());

        return view;


    }
}
