package py.com.sshc.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckedTextView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Emilio on 14/06/15.
 */
public class ClientesAccionDialogFragment extends DialogFragment {

    private Callbacks activity;
    ClientesAccionAdapter clientesAccionAdapter;
    private List<ClientesAccion> listaClientesAccionOrigen;


    public ClientesAccionDialogFragment() {

    }



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        ClientesAccion clientesAccion1 = new ClientesAccion("01","VENDER");
        ClientesAccion clientesAccion2 = new ClientesAccion("02","COBRAR");

        List<ClientesAccion> clientesAccionList = new ArrayList<ClientesAccion>();

        clientesAccionList.add(clientesAccion1);
        clientesAccionList.add(clientesAccion2);

        if (listaClientesAccionOrigen != null) {
            clientesAccionAdapter = new ClientesAccionAdapter(getActivity(),R.layout.item_clientes_accion,listaClientesAccionOrigen);
        }
        else
        {
            clientesAccionAdapter = new ClientesAccionAdapter(getActivity(),R.layout.item_clientes_accion,clientesAccionList);
        }

        AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                //.setIcon(R.drawable.ic_launcher)
                .setTitle("Acciones Realizadas")
                .setAdapter(clientesAccionAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                activity.doPositiveClick(clientesAccionAdapter.getObjects());
                            }
                        }
                )
                /*.setNegativeButton("CANCELAR",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                //((ClientesContactoActivity)getActivity()).doNegativeClick();
                            }
                        }
                )*/
                .create();


        alertDialog.getListView().setItemsCanFocus(false);
        alertDialog.getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        alertDialog.getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // Manage selected items here
                ClientesAccion clientesAccion = clientesAccionAdapter.getItem(position);
                System.out.println("clicked" + position);
                CheckedTextView textView = (CheckedTextView) view.findViewById(R.id.checkedTextView_item_clientes_accion_dato);
                if (textView.isChecked()) {
                    System.out.println("CHECKED");
                    textView.setChecked(false);
                    clientesAccion.setEstado(false);

                } else {
                    System.out.println("UN-CHECKED");
                    textView.setChecked(true);
                    clientesAccion.setEstado(true);
                }
            }
        });

        return alertDialog;

    }

    public interface Callbacks {
        public void doPositiveClick(List<ClientesAccion> clientesAccions);
    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (Callbacks) activity;
    }


    public void insertarDatos(List<ClientesAccion> lista){
        this.listaClientesAccionOrigen = lista;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);

        ((ClientesContactoActivity)getActivity()).actualizarAccionesDescripcion();

    }
}
