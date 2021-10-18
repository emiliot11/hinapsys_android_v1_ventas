package py.com.sshc.app;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A placeholder fragment containing a simple view.
 */
public class ClientesContactoActivityFragment extends Fragment {

    public final int REQUEST_CLIENTE = 100;


    StringUtil stringUtil = new StringUtil();

    Button elegirCliente;
    Button elegirAcciones;
    Spinner spinnerContactosTipo;
    EditText editTextContactado;
    EditText editTextComentario;
    EditText editTextAcciones;
    ProgressBar pb;

    String codigoCliente;
    List<ContactosTipo> listaContactosTipo;

    List<ClientesAccion> listaAcciones = null;

    public ClientesContactoActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_clientes_contacto, container, false);


        elegirCliente = (Button) vista.findViewById(R.id.button_crm_cliente);
        spinnerContactosTipo = (Spinner) vista.findViewById(R.id.spinnerContactosTipo_crm);
        editTextContactado = (EditText) vista.findViewById(R.id.editText_fragment_crm_nombre);
        editTextComentario = (EditText) vista.findViewById(R.id.editText_fragment_crm_comentario);
        pb = (ProgressBar) vista.findViewById(R.id.progressBar_fragmen_crm);
        elegirAcciones = (Button) vista.findViewById(R.id.button_crm_clientesaccion);
        editTextAcciones = (EditText) vista.findViewById(R.id.editText_fragment_crm_clientesAccion);

        pb.setVisibility(View.INVISIBLE);

        elegirCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getActivity(), ClientesActivity.class);
                startActivityForResult(i, REQUEST_CLIENTE);

            }
        });

        elegirAcciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ClientesAccionDialogFragment diagFrag = new ClientesAccionDialogFragment();

                if (listaAcciones != null) {
                    diagFrag.insertarDatos(listaAcciones);
                }

                diagFrag.show(getFragmentManager(), "acciones");

            }
        });
        return vista;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);



        if (requestCode == REQUEST_CLIENTE && resultCode == getActivity().RESULT_OK) {

            String cod = data.getStringExtra("COD");
            String des = data.getStringExtra("DES");

            elegirCliente.setText(cod + " " + des);

            codigoCliente = cod;


        }


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        buscarContactosTipo();

    }

    public void updateListaContactosTipo(List<ContactosTipo> lista) {

        ContactosTipoAdapter adapter = new ContactosTipoAdapter(getActivity(), R.layout.item_contactostipo, lista);

        spinnerContactosTipo.setAdapter(adapter);

        pb.setVisibility(View.INVISIBLE);

    }


    public void buscarContactosTipo() {

        NetworkUtility networkUtility = new NetworkUtility();
        if (networkUtility.comprobarConexionConMensaje(getActivity()) == false) {return;};

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(new PreferencesManager(getActivity()).getServidorConServicio())
                .build();

        ContactosTipoAPI api = adapter.create(ContactosTipoAPI.class);

        ContactosTipoParametros contactosTipoParametros = new ContactosTipoParametros();
        contactosTipoParametros.setEmpresa("01");
        contactosTipoParametros.setCuenta(new PreferencesManager(getActivity()).getCuenta(getActivity()));


        pb.setVisibility(View.VISIBLE);
        api.getContactosTipo(contactosTipoParametros, new Callback<List<ContactosTipo>>() {


            @Override
            public void success(List<ContactosTipo> contactosTipos, Response response) {
                if (getActivity() == null) {
                    return;
                }
                if (contactosTipos != null) {
                    if (contactosTipos.size() > 0) {
                        updateListaContactosTipo(contactosTipos);
                        //Toast.makeText(getActivity(), "DATOS RECUPERADOS (" + contactosTipos.size() + ")", Toast.LENGTH_SHORT).show();
                    } else {
                        pb.setVisibility(View.INVISIBLE);
                    }
                } else {
                    pb.setVisibility(View.INVISIBLE);
                }
                buscarClientesAccion();
            }

            @Override
            public void failure(RetrofitError error) {

                if (getActivity() == null) {
                    return;
                }

                String errorDescripcion = "";

                pb.setVisibility(View.INVISIBLE);
                error.printStackTrace();

                if (error.getResponse() != null) {
                    try {

                        errorDescripcion = stringUtil.convertStreamToString(error.getResponse().getBody().in());
                        if (errorDescripcion != null && error.getResponse().getStatus() == 500) {
                            Toast.makeText(getActivity(), errorDescripcion, Toast.LENGTH_SHORT).show();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getActivity(), "ERROR", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    public void buscarClientesAccion() {

        NetworkUtility networkUtility = new NetworkUtility();
        if (networkUtility.comprobarConexionConMensaje(getActivity()) == false) {return;};

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(new PreferencesManager(getActivity()).getServidorConServicio())
                .build();

        ClientesAccionAPI api = adapter.create(ClientesAccionAPI.class);

        ClientesAccionParametros clientesAccionParametros = new ClientesAccionParametros();
        clientesAccionParametros.setEmpresa("01");
        clientesAccionParametros.setCuenta(new PreferencesManager(getActivity()).getCuenta(getActivity()));


        pb.setVisibility(View.VISIBLE);
        api.getClientesAccion(clientesAccionParametros, new Callback<List<ClientesAccion>>() {


            @Override
            public void success(List<ClientesAccion> clientesAccions, Response response) {
                if (getActivity() == null) {
                    return;
                }
                if (clientesAccions != null) {
                    if (clientesAccions.size() > 0) {
                        listaAcciones = clientesAccions;
                        pb.setVisibility(View.INVISIBLE);
                        //Toast.makeText(getActivity(), "DATOS RECUPERADOS (" + contactosTipos.size() + ")", Toast.LENGTH_SHORT).show();
                    } else {
                        pb.setVisibility(View.INVISIBLE);
                    }
                } else {
                    pb.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void failure(RetrofitError error) {

                if (getActivity() == null) {
                    return;
                }

                String errorDescripcion = "";

                pb.setVisibility(View.INVISIBLE);
                error.printStackTrace();

                if (error.getResponse() != null) {
                    try {

                        errorDescripcion = stringUtil.convertStreamToString(error.getResponse().getBody().in());
                        if (errorDescripcion != null && error.getResponse().getStatus() == 500) {
                            Toast.makeText(getActivity(), errorDescripcion, Toast.LENGTH_SHORT).show();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getActivity(), "ERROR", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    public List<ClientesAccion> getListaAcciones() {
        return listaAcciones;
    }

    public void setListaAcciones(List<ClientesAccion> listaAcciones) {
        this.listaAcciones = listaAcciones;
    }


    public void actualizarAccionesDescripcion(){
        if (listaAcciones != null) {
            String descripcion = null;
           for (ClientesAccion clientesAccion : listaAcciones) {

               if (clientesAccion.getEstado()) {
                   if (listaAcciones.size() == 1) {
                       descripcion = clientesAccion.getDescripcionClientesAccion();
                   }
                   else
                   {
                       if (descripcion == null) {
                           descripcion = clientesAccion.getDescripcionClientesAccion();
                       }
                       else
                       {
                           descripcion = descripcion + "\n" + clientesAccion.getDescripcionClientesAccion();
                       }

                   }
               }

           }

            if (descripcion != null) {
                editTextAcciones.setText(descripcion);
            }
            else
            {
                editTextAcciones.setText("");
            }


        }
        else
        {
            editTextAcciones.setText("");
        }

    }


    public ClientesContacto crearCrm() {

        ClientesContacto clientesContacto = new ClientesContacto();
        List<ClientesAccion> acciones = new ArrayList<ClientesAccion>();

        for (ClientesAccion clientesAccion : listaAcciones) {
            if (clientesAccion.getEstado()) {
                acciones.add(clientesAccion);
            }
        }

        if (acciones.size() == 0){
            Toast.makeText(getActivity(), "Debe seleccionar al menos una acción", Toast.LENGTH_SHORT).show();
            return null;
        }

        if ("".equals(codigoCliente)){
            Toast.makeText(getActivity(), "Debe seleccionar un código de cliente", Toast.LENGTH_SHORT).show();
            return null;
        }

        clientesContacto.setCodigoCliente(codigoCliente);


        ContactosTipo contactosTipo = (ContactosTipo) spinnerContactosTipo.getAdapter().getItem(spinnerContactosTipo.getSelectedItemPosition());

        if (contactosTipo == null){
            Toast.makeText(getActivity(), "Debe seleccionar un tipo de contacto", Toast.LENGTH_SHORT).show();
            return null;
        }

        clientesContacto.setCodigoContactoTipo(contactosTipo.getCodigoContactosTipo());

        if ("".equals(editTextContactado.getText().toString()) ) {
            Toast.makeText(getActivity(), "Debe ingresar un contacto", Toast.LENGTH_SHORT).show();
            return null;
        }

        clientesContacto.setContacto(editTextContactado.getText().toString());

        if ("".equals(editTextComentario.getText().toString()) ) {
            Toast.makeText(getActivity(), "Debe ingresar un comentario.", Toast.LENGTH_SHORT).show();
            return null;
        }

        clientesContacto.setComentarios(editTextComentario.getText().toString());

        clientesContacto.setAcciones(acciones);

        return clientesContacto;

    }

    public void guardarCrm(){


        NetworkUtility networkUtility = new NetworkUtility();
        if (networkUtility.comprobarConexionConMensaje(getActivity()) == false) {return;};

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(new PreferencesManager(getActivity()).getServidorConServicio())
                .build();

        ClientesContacto clientesContacto = crearCrm();

        if (clientesContacto == null){
            return;
        }

        ClientesContactoAPI api = adapter.create(ClientesContactoAPI.class);

        ClientesContactoParametros clientesContactoParametros = new ClientesContactoParametros();
        clientesContactoParametros.setEmpresa("01");
        clientesContactoParametros.setClientesContacto(clientesContacto);
        clientesContactoParametros.setCuenta(new PreferencesManager(getActivity()).getCuenta(getActivity()));


        pb.setVisibility(View.VISIBLE);
        api.guardarContactosCliente(clientesContactoParametros, new Callback<List<ClienteContactoRESP>>() {


            @Override
            public void success(List<ClienteContactoRESP> clienteContactoRESP, Response response) {
                if (getActivity() == null) {
                    return;
                }
                if (clienteContactoRESP != null) {
                    Boolean guardado = clienteContactoRESP.get(0).getEstado();
                    String numeroCRM = clienteContactoRESP.get(0).getMensaje().trim();
                    pb.setVisibility(View.INVISIBLE);
                    if (guardado) {

                        Toast mensajeAlUsuario = Toast.makeText(getActivity(), "REGISTRO GUARDADO CRM.: " + numeroCRM.toString(), Toast.LENGTH_LONG);
                        mensajeAlUsuario.setGravity(Gravity.CENTER, 0, 0);
                        mensajeAlUsuario.show();
                        ((ClientesContactoActivity) getActivity()).limpiar();

                    }
                    else
                    {
                        Toast.makeText(getActivity(), "ERROR = " + clienteContactoRESP.get(0).getMensaje(), Toast.LENGTH_LONG).show();
                    }


                } else {
                    pb.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void failure(RetrofitError error) {

                if (getActivity() == null) {
                    return;
                }

                String errorDescripcion = "";

                pb.setVisibility(View.INVISIBLE);
                error.printStackTrace();

                if (error.getResponse() != null) {
                    try {
                        errorDescripcion = stringUtil.convertStreamToString(error.getResponse().getBody().in());
                        if (errorDescripcion != null && error.getResponse().getStatus() == 500) {
                            Toast.makeText(getActivity(), errorDescripcion, Toast.LENGTH_SHORT).show();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getActivity(), "ERROR", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }



}
