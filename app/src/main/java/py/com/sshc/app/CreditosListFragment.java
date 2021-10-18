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


public class CreditosListFragment extends ListFragment {

    ProgressBar pb;
    List<Credito> creditos;
    private Callbacks activity;
    ListView listaCreditos;
    StringUtil stringUtil = new StringUtil();

    private String codigoCliente = "";

    public CreditosListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle b = getArguments();

        if (b != null) {
            codigoCliente = b.getString(MainActivity.CLIENTE_CODIGO);
        } else {
            Toast.makeText(getActivity(),"No se ha iniciado el codigo del cliente.",Toast.LENGTH_SHORT).show();
            getActivity().finish();
        }



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_creditos_list, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        pb = (ProgressBar) getView().findViewById(R.id.progressBar_fragment_creditos_list);
        pb.setVisibility(View.INVISIBLE);

        buscarCreditos(codigoCliente);

    }

    public void updateLista(List<Credito> lista) {

        creditos = lista;
        CreditosAdapter adapter = new CreditosAdapter(getActivity(), R.layout.item_credito, lista);
        setListAdapter(adapter);
        pb.setVisibility(View.INVISIBLE);

    }


    public interface Callbacks {
        public void onItemSelected(Credito credito);
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Credito credito = creditos.get(position);
        activity.onItemSelected(credito);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (Callbacks) activity;
    }

    public void buscarCreditos(String codigoBuscado) {

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(new PreferencesManager(getActivity()).getServidorConServicio())
                .build();

        CreditosAPI api = adapter.create(CreditosAPI.class);

        CreditoParametros creditoParametro = new CreditoParametros();
        creditoParametro.setEmpresa("01");
        creditoParametro.setCodigo(codigoBuscado);
        creditoParametro.setCuenta(new PreferencesManager(getActivity()).getCuenta(getActivity()));


        pb.setVisibility(View.VISIBLE);
        api.getCreditos(creditoParametro, new Callback<List<Credito>>() {


            @Override
            public void success(List<Credito> creditos, Response response) {
                if (getActivity() == null) { return;}
                if (creditos != null) {
                    if (creditos.size() > 0) {
                        updateLista(creditos);
                        Toast.makeText(getActivity(), "DATOS RECUPERADOS (" + creditos.size() + ")", Toast.LENGTH_SHORT).show();
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
