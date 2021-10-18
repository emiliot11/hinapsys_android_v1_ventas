package py.com.sshc.app;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


/**
 * A placeholder fragment containing a simple view.
 */
public class SucursalListFragment extends ListFragment {

    StringUtil stringUtil = new StringUtil();
    List<Sucursal> sucursales;
    private Callbacks activity;

    public SucursalListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sucursal_list, container, false);
    }


    public void updateLista(List<Sucursal> lista) {

        sucursales = lista;
        SucursalAdapter adapter = new SucursalAdapter(getActivity(), R.layout.item_sucursal, lista);
        setListAdapter(adapter);

    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        buscarSucursal("", "");

    }

    public interface Callbacks {
        public void onItemSelected(Sucursal sucursal);
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Sucursal sucursal = sucursales.get(position);
        activity.onItemSelected(sucursal);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (Callbacks) activity;
    }

    public void buscarSucursal(String codigoBuscado, String descripcionBuscada) {

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(new PreferencesManager(getActivity()).getServidorConServicio())
                .build();

        SucursalAPI api = adapter.create(SucursalAPI.class);

        SucursalParametros sucursalParametro = new SucursalParametros();
        sucursalParametro.setEmpresa("01");
        sucursalParametro.setCodigo(codigoBuscado);
        sucursalParametro.setDescripcion(descripcionBuscada);
        sucursalParametro.setCuenta(new PreferencesManager(getActivity()).getCuenta(getActivity()));


        api.getSucursales(sucursalParametro, new Callback<List<Sucursal>>() {


            @Override
            public void success(List<Sucursal> sucursales, Response response) {
                if (getActivity() == null) { return;}
                if (sucursales != null) {
                    if (sucursales.size() > 0) {
                        updateLista(sucursales);
                        Toast.makeText(getActivity(), "DATOS RECUPERADOS (" + sucursales.size() + ")", Toast.LENGTH_SHORT).show();
                    } else {
                        //pb.setVisibility(View.INVISIBLE);
                    }
                } else {
                    //pb.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void failure(RetrofitError error) {

                if (getActivity() == null) { return;}

                String errorDescripcion = "";

                //pb.setVisibility(View.INVISIBLE);
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
