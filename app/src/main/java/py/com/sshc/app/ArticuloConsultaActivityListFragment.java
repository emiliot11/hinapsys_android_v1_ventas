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

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import java.io.IOException;
import java.util.List;


/**
 * A placeholder fragment containing a simple view.
 */
public class ArticuloConsultaActivityListFragment extends ListFragment {

    List<ArticuloConsulta> articuloConsultas;
    StringUtil stringUtil = new StringUtil();
    ProgressBar pb;
    String codigoPrecioLista;
    String codigoSucursal;
    private Callbacks activity;


    public ArticuloConsultaActivityListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle b = getArguments();

        if (b != null) {
            codigoPrecioLista = b.getString(MainActivity.PRECIOLISTA_CODIGO);
            codigoSucursal = b.getString(MainActivity.SUCURSAL_CODIGO);
        } else {
            Toast.makeText(getActivity(), "DATOS DE ORIGEN NO EXISTENTES", Toast.LENGTH_SHORT).show();
            getActivity().finish();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_articulo_consulta_list, container, false);

        pb = (ProgressBar) vista.findViewById(R.id.progressBar_fragment_articulos_consulta_list);


        return vista;
    }

    public interface Callbacks {
        public void onItemSelected(ArticuloConsulta articuloConsulta);
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        ArticuloConsulta articuloConsulta = articuloConsultas.get(position);
        activity.onItemSelected(articuloConsulta);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (Callbacks) activity;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        pb.setVisibility(View.INVISIBLE);

    }

    public void updateLista(List<ArticuloConsulta>  lista) {

        if (lista == null) { return; }

        articuloConsultas = lista;
        ArticuloConsultaAdapter adapter = new ArticuloConsultaAdapter(getActivity(), R.layout.item_articulo_consulta, lista);
        setListAdapter(adapter);
        pb.setVisibility(View.INVISIBLE);

    }

    public void buscarArticuloConsulta(String codigoBuscado, String descripcionBuscada) {

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(new PreferencesManager(getActivity()).getServidorConServicio())
                .build();

        ArticuloConsultaAPI api = adapter.create(ArticuloConsultaAPI.class);

        ArticuloConsultaParametros articuloConsultaParametros = new ArticuloConsultaParametros();
        articuloConsultaParametros.setEmpresa("01");
        articuloConsultaParametros.setCodigoArticulo(codigoBuscado);
        articuloConsultaParametros.setDescripcionArticulo(descripcionBuscada);
        articuloConsultaParametros.setCodigoPrecioLista(codigoPrecioLista);
        articuloConsultaParametros.setCodigoSucursal(codigoSucursal);
        articuloConsultaParametros.setCuenta(new PreferencesManager(getActivity()).getCuenta(getActivity()));


        pb.setVisibility(View.VISIBLE);
        api.getArticuloConsulta(articuloConsultaParametros, new Callback<List<ArticuloConsulta>>() {


            @Override
            public void success(List<ArticuloConsulta> articuloConsultas, Response response) {

                if (articuloConsultas != null) {
                    if (articuloConsultas.size() > 0) {
                        if (getActivity() != null) {
                            updateLista(articuloConsultas);
                            Toast.makeText(getActivity(), "DATOS RECUPERADOS (" + articuloConsultas.size() + ")", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (getActivity() != null) {
                            updateLista(articuloConsultas);
                            pb.setVisibility(View.INVISIBLE);
                        }
                    }
                } else {
                    if (getActivity() != null) {
                        pb.setVisibility(View.INVISIBLE);
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {

                if (getActivity() != null) { return;}

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
