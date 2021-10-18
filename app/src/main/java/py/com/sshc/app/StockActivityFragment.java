package py.com.sshc.app;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
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
public class StockActivityFragment extends Fragment {

    ProgressBar pb;
    Spinner spinnerSucursal;
    Spinner spinnerPreciosLista;
    ImageButton botonBuscarArticulo;
    EditText editText_codigo;
    EditText editText_descripcion;

    SucursalAdapter sucursalAdapter;

    String codigoSucursal = "00";
    String codigoPrecioLista;

    List<Sucursal> sucursalList;
    List<PreciosLista> preciosListaList;
    List<ArticuloConsulta> articuloConsultas;

    StringUtil stringUtil;
    PreciosListaAdapter preciosListaAdapter;

    ListView listView;

    public StockActivityFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_stock, container, false);

        pb = (ProgressBar) vista.findViewById(R.id.progressBar_fragmen_stock);

        spinnerSucursal = (Spinner) vista.findViewById(R.id.spinnerSucursal_stock);

        spinnerSucursal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Sucursal sucursal = sucursalAdapter.getItem(position);
                codigoSucursal = sucursal.getSucursalCodigo();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerPreciosLista = (Spinner) vista.findViewById(R.id.spinnerPreciosLista_stock);

        // Set the custom adapter to the spinner
        // You can create an anonymous listener to handle the event when is selected an spinner item

        /*spinnerPreciosLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });*/
        spinnerPreciosLista.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                // Here you get the current item (a User object) that is selected by its position
                if (preciosListaAdapter != null) {
                    PreciosLista precioLista = preciosListaAdapter.getItem(position);
                    // Here you can do the action you want to...
                    //Toast.makeText(getActivity(), "ID: " + precioLista.getPrecioListaCodigo() + "\nName: " + precioLista.getPrecioListaDescripcion(),
                    //        Toast.LENGTH_SHORT).show();

                    codigoPrecioLista = precioLista.getPrecioListaCodigo();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapter) {
            }
        });


        listView = (ListView) vista.findViewById(R.id.listView_stock_resultado);

        botonBuscarArticulo = (ImageButton) vista.findViewById(R.id.button_stock_buscar);


        botonBuscarArticulo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                buscarDatos();



            }
        });

        editText_codigo = (EditText) vista.findViewById(R.id.editText_fragment_stock_codigo_articulo);
        editText_codigo.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    buscarDatos();

                    return true;
                }
                return false;
            }
        });
        editText_descripcion = (EditText) vista.findViewById(R.id.editText_fragment_stock_descripcion_articulo);
        editText_descripcion.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    buscarDatos();

                    return true;
                }
                return false;
            }
        });

        return vista;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState == null) {
            buscarSucursal("", "");
        }
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

        pb.setVisibility(View.VISIBLE);

        api.getSucursales(sucursalParametro, new Callback<List<Sucursal>>() {


            @Override
            public void success(List<Sucursal> sucursales, Response response) {
                if (getActivity() == null) {
                    return;
                }
                if (sucursales != null) {
                    if (sucursales.size() > 0) {
                        updateSucursales(sucursales);
                        //Toast.makeText(getActivity(), "DATOS RECUPERADOS (" + sucursales.size() + ")", Toast.LENGTH_SHORT).show();
                        pb.setVisibility(View.INVISIBLE);
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

    private void updateSucursales(List<Sucursal> lista) {


        sucursalList = lista;
        sucursalAdapter = new SucursalAdapter(getActivity(),
                android.R.layout.simple_spinner_item,
                sucursalList,true);

        spinnerSucursal.setAdapter(sucursalAdapter);

        Integer i;
        String cod = "";
        for(i=0; i < sucursalAdapter.getCount(); i++) {
            cod = sucursalAdapter.getItem(i).getSucursalCodigo();
            if (codigoSucursal.equals(cod)) {
                spinnerSucursal.setSelection(i);
                break;
            }
        }


        buscarPreciosLista();


    }

    public void buscarPreciosLista() {


        NetworkUtility networkUtility = new NetworkUtility();
        if (networkUtility.comprobarConexionConMensaje(getActivity()) == false) {return;};

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(new PreferencesManager(getActivity()).getServidorConServicio())
                .build();

        PreciosListaAPI api = adapter.create(PreciosListaAPI.class);

        PreciosListaParametros preciosListaParametro = new PreciosListaParametros();
        preciosListaParametro.setEmpresa("01");
        preciosListaParametro.setCliente(null);
        preciosListaParametro.setCuenta(new PreferencesManager(getActivity()).getCuenta(getActivity()));

        pb.setVisibility(View.VISIBLE);
        api.getPreciosLista(preciosListaParametro, new Callback<List<PreciosLista>>() {


            @Override
            public void success(List<PreciosLista> preciosLista, Response response) {
                if (getActivity() == null) {
                    return;
                }
                if (preciosLista != null) {
                    if (preciosLista.size() > 0) {
                        updateListaPrecio(preciosLista);
                        pb.setVisibility(View.INVISIBLE);
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
                            //Toast.makeText(getActivity(), errorDescripcion, Toast.LENGTH_SHORT).show();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    //Toast.makeText(getActivity(), "ERROR", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void updateListaPrecio(List<PreciosLista> lista) {

        preciosListaList = lista;

        preciosListaAdapter = new PreciosListaAdapter(getActivity(),
                android.R.layout.simple_spinner_item,
                preciosListaList,true);

        spinnerPreciosLista.setAdapter(preciosListaAdapter);

        Integer i;
        String cod = "";
        for(i=0; i < preciosListaAdapter.getCount(); i++) {
            cod = preciosListaAdapter.getItem(i).getPrecioListaCodigo();
            if (cod.equals("01")) {
                spinnerPreciosLista.setSelection(i);
                break;
            }
        }


    }

    public void updateLista(List<ArticuloConsulta>  lista) {

        if (lista == null) { return; }

        articuloConsultas = lista;
        ArticuloConsultaAdapter adapter = new ArticuloConsultaAdapter(getActivity(), R.layout.item_articulo_consulta, lista);
        listView.setAdapter(adapter);
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

                if (getActivity() != null) {
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

    public void buscarDatos(){

        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText_codigo.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(editText_descripcion.getWindowToken(), 0);

        buscarArticuloConsulta(editText_codigo.getText().toString(),editText_descripcion.getText().toString());


    }


    @Override
    public void onResume() {
        super.onResume();

        if (articuloConsultas != null){
            updateLista(articuloConsultas);
        }

    }
}
