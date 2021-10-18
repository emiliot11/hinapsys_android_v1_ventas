package py.com.sshc.app;


import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class ClientesListFragment extends ListFragment {


    ProgressBar pb;
    List<Cliente> clientes;
    private Callbacks activity;
    ListView listaClientes;
    StringUtil stringUtil = new StringUtil();

    public ClientesListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_clientes_list, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        pb = (ProgressBar) getView().findViewById(R.id.progressBar_fragment_clientes_list);
        pb.setVisibility(View.INVISIBLE);

    }

    public void updateLista(List<Cliente> lista) {


        clientes = lista;
        ClientesAdapter adapter = new ClientesAdapter(getActivity(), R.layout.item_cliente, lista);
        setListAdapter(adapter);
        pb.setVisibility(View.INVISIBLE);

    }


    public interface Callbacks {
        public void onItemSelected(Cliente cliente);
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Cliente cliente = clientes.get(position);
        activity.onItemSelected(cliente);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (Callbacks) activity;
    }

    public void buscarClientes(String codigoBuscado, String descripcionBuscada) {

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(new PreferencesManager(getActivity()).getServidorConServicio())
                .build();

        ClientesAPI api = adapter.create(ClientesAPI.class);

        ClienteParametros clienteParametro = new ClienteParametros();
        clienteParametro.setEmpresa("01");
        clienteParametro.setCodigo(codigoBuscado);
        clienteParametro.setDescripcion(descripcionBuscada);
        clienteParametro.setCuenta(new PreferencesManager(getActivity()).getCuenta(getActivity()));


        pb.setVisibility(View.VISIBLE);
        api.getClientes(clienteParametro, new Callback<List<Cliente>>() {


            @Override
            public void success(List<Cliente> clientes, Response response) {
                if (getActivity() == null) { return;}
                if (clientes != null) {
                    if (clientes.size() > 0) {
                        updateLista(clientes);
                        Toast.makeText(getActivity(), "DATOS RECUPERADOS (" + clientes.size() + ")", Toast.LENGTH_SHORT).show();
                    } else {
                        pb.setVisibility(View.INVISIBLE);
                    }
                } else {
                    pb.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (getActivity() == null) { return;}

                String errorDescripcion = "";

                pb.setVisibility(View.INVISIBLE);
                error.printStackTrace();

                if (error.getResponse() != null) {
                    try {

                        errorDescripcion = stringUtil.convertStreamToString(error.getResponse().getBody().in());
                        if (errorDescripcion != null && error.getResponse().getStatus() == 500 ) {
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
