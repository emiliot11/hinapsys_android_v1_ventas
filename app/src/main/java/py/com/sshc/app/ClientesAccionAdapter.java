package py.com.sshc.app;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;

import java.util.List;

/**
 * Created by Emilio on 14/06/15.
 */
public class ClientesAccionAdapter extends ArrayAdapter<ClientesAccion> {


    private Context context;
    private List<ClientesAccion> objects;

    public ClientesAccionAdapter(Context context, int resource, List<ClientesAccion> objects) {
        super(context, resource, objects);

        this.context = context;
        this.objects = objects;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ClientesAccion clientesAccion = objects.get(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.item_clientes_accion, null);

        CheckedTextView tv = (CheckedTextView) view.findViewById(R.id.checkedTextView_item_clientes_accion_dato);
        tv.setText(clientesAccion.getDescripcionClientesAccion());

        tv.setChecked(clientesAccion.getEstado());


        return view;
    }

    public List<ClientesAccion> getObjects() {
        return objects;
    }
}
