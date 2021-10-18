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
public class CreditosAdapter extends ArrayAdapter<Credito> {

    private Context context;
    private List<Credito> objects;

    public CreditosAdapter(Context context, int resource, List<Credito> objects) {
        super(context, resource, objects);
        this.context = context;
        this.objects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Credito credito = objects.get(position);
        LayoutInflater inflater =
                (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_credito, null);

        TextView tv2 = (TextView) view.findViewById(R.id.textView_listaCredito_descripcion);
        tv2.setText(credito.getCreditoDescripcion());

        return view;

    }
}
