package py.com.sshc.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class ClientesDatosDialogFragment extends DialogFragment {


    private Callbacks activity;

    EditText editText_nombre;
    EditText editText_ruc;
    EditText editText_direccion;
    EditText editText_telefono;
    EditText editText_email;

    ClienteDato clienteDato;


    public static ClientesDatosDialogFragment nuevaInstancia(ClienteDato clienteDato) {
        ClientesDatosDialogFragment f = new ClientesDatosDialogFragment();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putParcelable("DATOS", clienteDato);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            this.clienteDato = getArguments().getParcelable("DATOS");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //return super.onCreateDialog(savedInstanceState);


        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialoglayout = inflater.inflate(R.layout.fragment_clientes_datos_dialog, null);


        editText_nombre = (EditText) dialoglayout.findViewById(R.id.editText_fragment_clientedatos_nombre);
        editText_ruc = (EditText) dialoglayout.findViewById(R.id.editText_fragment_clientedatos_ruc);
        editText_direccion = (EditText) dialoglayout.findViewById(R.id.editText_fragment_clientedatos_direccion);
        editText_telefono = (EditText) dialoglayout.findViewById(R.id.editText_fragment_clientedatos_telefono);
        editText_email = (EditText) dialoglayout.findViewById(R.id.editText_fragment_clientedatos_email);

        if (clienteDato != null) {
            editText_nombre.setText(clienteDato.getNombre());
            editText_ruc.setText(clienteDato.getRuc());
            editText_direccion.setText(clienteDato.getDireccion());
            editText_telefono.setText(clienteDato.getTelefono());
            editText_email.setText(clienteDato.getEmail());
        }

        final AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                //.setIcon(R.drawable.ic_launcher)
                .setTitle("DATOS DEL CLIENTE")
                .setPositiveButton("OK",null
                )
                .setNegativeButton("CANCELAR",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                //((ClientesContactoActivity)getActivity()).doNegativeClick();
                            }
                        }
                )
                .setView(dialoglayout).create();

        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button boton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                boton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (enviarDatos()) {

                            alertDialog.dismiss();

                        }

                    }
                });
            }
        });

        return alertDialog;
    }

    private boolean enviarDatos() {

        ClienteDato clienteDato = new ClienteDato();

        String nombre = editText_nombre.getText().toString();
        String ruc = editText_ruc.getText().toString();
        String direccion = editText_direccion.getText().toString();
        String telefono = editText_telefono.getText().toString();
        String email = editText_email.getText().toString();


        if ("".equals(nombre)  ){
            Toast.makeText(getActivity(),"Debe completar el nombre de la persona.",Toast.LENGTH_SHORT).show();
            return false;
        }
        if ("".equals(telefono)  ){
            Toast.makeText(getActivity(),"Debe completar el numero de telefono.",Toast.LENGTH_SHORT).show();
            return false;
        }

        clienteDato.setNombre(nombre);
        clienteDato.setRuc(ruc);
        clienteDato.setDireccion(direccion);
        clienteDato.setTelefono(telefono);
        clienteDato.setEmail(email);

        activity.onDatosCargados(clienteDato);
        return true;

    }


    public interface Callbacks {
        public void onDatosCargados(ClienteDato clienteDato);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (Callbacks) activity;
    }

}
