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
 * Created by Emilio on 16/09/2014.
 */
public class ClientesAdapter extends ArrayAdapter<Cliente> {

    private Context context;
    private List<Cliente> objects;

    public ClientesAdapter(Context context, int resource, List<Cliente> objects) {
        super(context, resource, objects);
        this.context = context;
        this.objects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Cliente cliente = objects.get(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.item_cliente, null);

        TextView tv = (TextView) view.findViewById(R.id.textView_listaCliente_codigo);
        tv.setText(cliente.getClienteCodigo());

        TextView tv2 = (TextView) view.findViewById(R.id.textView_listaCliente_descripcion);
        tv2.setText(cliente.getClienteDescripcion());

        return view;

    }
}
