package py.com.sshc.app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class PedidosFragment extends Fragment  {

    public final int REQUEST_CLIENTE = 100;
    public final int REQUEST_CREDITO = 101;
    public final int REQUEST_ARTICULO = 102;

    private Callbacks activity;

    Button elegirClienteBoton;
    Button elegirCreditoBoton;
    Button elegirArticulosBoton;

    String codigoCliente = null;
    String codigoSucursal = null;
    String codigoCredito = null;
    String codigoPrecioLista = null;

    PreciosListaAdapter preciosListaAdapter;
    SucursalAdapter sucursalAdapter;

    Spinner spinnerPreciosLista;
    Spinner spinnerSucursal;

    ProgressBar pb;

    PreciosLista precioLista;


    EditText historial;
    TextView estadoAutorizacion;

    List<PreciosLista> preciosListaList;
    List<Sucursal> sucursalList;
    List<PedidosArticulo> pedidosArticulos;

    ArrayList<PedidosArticulo> pedidosArticulosArray;

    StringUtil stringUtil;
    String esVarios;
    private ClienteDato clienteDato;

    TextView tv_totalPedido;

    public PedidosFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //ABRIR EL LAYOUT
        View vista = inflater.inflate(R.layout.fragment_pedidos, container, false);

        historial = (EditText) vista.findViewById(R.id.editText_fragmen_pedidos_resultado);
        estadoAutorizacion = (TextView) vista.findViewById(R.id.textView_fragment_pedidos_estado_autorizacion);

        pb = (ProgressBar) vista.findViewById(R.id.progressBar_fragmen_pedidos);
        pb.setVisibility(View.INVISIBLE);

        //BOTON DE CLIENTES
        elegirClienteBoton = (Button) vista.findViewById(R.id.buttonElegirCliente);
        elegirClienteBoton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ClientesActivity.class);
                startActivityForResult(intent, REQUEST_CLIENTE);
            }
        });

        elegirClienteBoton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {


                if (codigoCliente != null && "S".equals(esVarios.trim())) {


                    abrirClientesDatos();


                    return true;


                }

                return false;


            }
        });

        //BOTON DE CREDITOS
        elegirCreditoBoton = (Button) vista.findViewById(R.id.buttonElegirCredito);
        elegirCreditoBoton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (codigoCliente != null) {
                    Intent intent = new Intent(getActivity(), CreditosActivity.class);

                    Bundle b = new Bundle();
                    b.putString(MainActivity.CLIENTE_CODIGO,codigoCliente);

                    intent.putExtra(MainActivity.CLIENTE_BUNDLE,b);

                    startActivityForResult(intent, REQUEST_CREDITO);
                } else {
                    Toast.makeText(getActivity(),"Debe elegir un cliente.",Toast.LENGTH_SHORT).show();
                }
            }
        });



        elegirArticulosBoton = (Button) vista.findViewById(R.id.buttonElegirArticulos);
        elegirArticulosBoton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (codigoPrecioLista == null) {
                    Toast.makeText(getActivity(),"Debe elegir una lista de precio antes de elegir articulos.",Toast.LENGTH_SHORT).show();
                    return;
                }

                if (codigoSucursal == null) {
                    Toast.makeText(getActivity(),"Debe elegir una sucursal antes de elegir articulos.",Toast.LENGTH_SHORT).show();
                    return;
                }


                Intent intent = new Intent(getActivity(), PedidosArticuloActivity.class);

                Bundle b = new Bundle();
                b.putString(MainActivity.PRECIOLISTA_CODIGO, codigoPrecioLista);
                b.putString(MainActivity.SUCURSAL_CODIGO, codigoSucursal);

                intent.putExtra(MainActivity.ELEGIR_ARTICULO_PEDIDO_BUNDLE, b);
                if (pedidosArticulos != null) {
                    intent.putParcelableArrayListExtra("ITEMS",pedidosArticulosArray);
                }

                pedidosArticulosArray = null;
                pedidosArticulos = null;

                startActivityForResult(intent, REQUEST_ARTICULO);


            }
        });

        spinnerPreciosLista = (Spinner) vista.findViewById(R.id.spinnerPreciosLista_pedidos);

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
                PreciosLista precioLista = preciosListaAdapter.getItem(position);
                // Here you can do the action you want to...
                //Toast.makeText(getActivity(), "ID: " + precioLista.getPrecioListaCodigo() + "\nName: " + precioLista.getPrecioListaDescripcion(),
                //        Toast.LENGTH_SHORT).show();

                codigoPrecioLista = precioLista.getPrecioListaCodigo();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapter) {
            }
        });


        spinnerSucursal = (Spinner) vista.findViewById(R.id.spinnerSucursal_pedidos);

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


        tv_totalPedido = (TextView) vista.findViewById(R.id.textView_formPedidosArticulo_total);

        return vista;

    }

    private void abrirClientesDatos() {

        ClientesDatosDialogFragment diagFrag;

        if (clienteDato == null) {
            diagFrag = new ClientesDatosDialogFragment();

        }
        else
        {
            diagFrag = new ClientesDatosDialogFragment();
            diagFrag = diagFrag.nuevaInstancia(clienteDato);
        }

        //if (listaAcciones != null) {
        //    diagFrag.insertarDatos(listaAcciones);
        //}

        diagFrag.show(getFragmentManager(), "datos");

    }

    public interface Callbacks {
        public void prepararNuevo();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (Callbacks) activity;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CLIENTE && resultCode == getActivity().RESULT_OK) {

            String cod = data.getStringExtra("COD");
            String des = data.getStringExtra("DES");
            String suc = data.getStringExtra("SUC");
            String varios = data.getStringExtra("VARIOS");

            colocarTituloCliente(cod, des);



            codigoCliente = cod;
            codigoSucursal = suc;
            esVarios = varios;


            buscarSucursal("", "");
            buscarPreciosLista();

            //Limpiar datos
            elegirCreditoBoton.setText("ELEGIR");
            codigoCredito = null;
            codigoPrecioLista = null;

        }
        if (requestCode == REQUEST_CREDITO && resultCode == getActivity().RESULT_OK) {

            String cod = data.getStringExtra("COD");
            String des = data.getStringExtra("DES");

            codigoCredito = cod;

            elegirCreditoBoton.setText(des);


        }


        if (requestCode == REQUEST_ARTICULO && resultCode == getActivity().RESULT_OK) {

            double totalPedido;
            pedidosArticulosArray = data.getParcelableArrayListExtra("ITEMS");

            if (pedidosArticulosArray != null) {
                pedidosArticulos = new ArrayList<PedidosArticulo>(pedidosArticulosArray);
                elegirArticulosBoton.setText("ARTICULOS (" + pedidosArticulosArray.size() + " ítem/s) ");
                totalPedido = sumarPedido();
                tv_totalPedido.setText(new Utiliario().formatearNumeroSinDecimales(totalPedido));
            }
            else
            {
                elegirArticulosBoton.setText("ARTICULOS" );
            }




        }

    }

    private void colocarTituloCliente(String cod, String des) {

        elegirClienteBoton.setText(cod + " " + des);

    }

    public void updateListaPrecio (List<PreciosLista> lista){


        preciosListaList = lista;

        preciosListaAdapter = new PreciosListaAdapter(getActivity(),
                android.R.layout.simple_spinner_item,
                preciosListaList);

        spinnerPreciosLista.setAdapter(preciosListaAdapter);


        Integer i;
        String predeterminado = "";
        for(i=0; i < preciosListaAdapter.getCount(); i++) {
            predeterminado = preciosListaAdapter.getItem(i).getPredeterminado();
            if (predeterminado.equals("S")) {
                spinnerPreciosLista.setSelection(i);
                break;
            }
        }



    }

    public void updateSucursales (List<Sucursal> lista){

        sucursalList = lista;
        sucursalAdapter = new SucursalAdapter(getActivity(),
                android.R.layout.simple_spinner_item,
                sucursalList);

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
        preciosListaParametro.setCliente(codigoCliente);
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
                if (getActivity() == null) { return;}
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

    public Pedido crearPedido(){

        if ("".equals(codigoCliente)){
            Toast.makeText(getActivity(),"Debe elegir un cliente antes de continuar.",Toast.LENGTH_SHORT).show();
            return null;
        }

        if (codigoCredito == null){
            Toast.makeText(getActivity(),"Debe elegir una condición antes de continuar.",Toast.LENGTH_SHORT).show();
            return null;
        }

        if ("".equals(codigoSucursal)){
            Toast.makeText(getActivity(),"Debe elegir una sucursal antes de continuar.",Toast.LENGTH_SHORT).show();
            return null;
        }

        if ("".equals(codigoPrecioLista)){
            Toast.makeText(getActivity(),"Debe elegir una lista de precio antes de continuar.",Toast.LENGTH_SHORT).show();
            return null;
        }

        if ("".equals(codigoPrecioLista)){
            Toast.makeText(getActivity(),"Debe elegir una lista de precio antes de continuar.",Toast.LENGTH_SHORT).show();
            return null;
        }

        if (pedidosArticulos != null) {
            if (pedidosArticulos.size() == 0) {
                pedidosArticulos = null;
                Toast.makeText(getActivity(),"Debe cargar al menos un articulo antes de continuar.",Toast.LENGTH_SHORT).show();
                return null;
            }
        } else
        {
            Toast.makeText(getActivity(),"Debe cargar al menos un articulo antes de continuar.",Toast.LENGTH_SHORT).show();
            return null;
        }

        Pedido pedido = new Pedido();

        pedido.setEmpresa("01");
        pedido.setCodigoCliente(codigoCliente);
        pedido.setCodigoCondicion(Integer.parseInt(codigoCredito));
        pedido.setCodigoSucursal(codigoSucursal);
        pedido.setCodigoPrecioLista(codigoPrecioLista);
        pedido.setItemPedidos(pedidosArticulos);


        if ("S".equals(esVarios)) {
            pedido.setClienteDato(clienteDato);
        }

        return  pedido;

    }

    public void enviarPedido() {



            RestAdapter adapter = new RestAdapter.Builder()
                    .setEndpoint(new PreferencesManager(getActivity()).getServidorConServicio())
                    .build();

            PedidoAPI api = adapter.create(PedidoAPI.class);

            PedidoParametro pedidoParametro = new PedidoParametro();

            Pedido pedido = crearPedido();

            if (pedido == null){
                return;
            }

            pedidoParametro.setPedido(pedido);
            pedidoParametro.setCuenta(new PreferencesManager(getActivity()).getCuenta(getActivity()));


            pb.setVisibility(View.VISIBLE);
            api.setPedido(pedidoParametro, new Callback<List<PedidoRESP>>() {


                @Override
                public void success(List<PedidoRESP> pedidoRESP, Response response) {
                    if (getActivity() == null) { return;}
                    if (pedidoRESP != null) {

                        Boolean guardado = pedidoRESP.get(0).getEstado();
                        String numeroPresupuesto = pedidoRESP.get(0).getMensaje().trim();
                        String estadoAutorizacionMensaje;
                        if (pedidoRESP.get(0).getEstadoAutorizacion() != null) {
                            estadoAutorizacionMensaje = pedidoRESP.get(0).getEstadoAutorizacion().toString().trim();
                        } else {
                            estadoAutorizacionMensaje = "";
                        }

                        String historialMensaje;
                        if (pedidoRESP.get(0).getHistorial() != null) {
                            historialMensaje = pedidoRESP.get(0).getHistorial().toString().trim();
                        } else {
                            historialMensaje = "";
                        }

                        if (guardado) {
                            Toast.makeText(getActivity(), "EL PEDIDO HA SIDO REGISTRADO CON EXITO", Toast.LENGTH_LONG).show();
                            estadoAutorizacion.setText(estadoAutorizacionMensaje + " (" + numeroPresupuesto + ")");

                            if ("AUTORIZADO".equals(estadoAutorizacionMensaje)) {
                                estadoAutorizacion.setTextColor(Color.rgb(0, 128, 0));
                            } else {
                                estadoAutorizacion.setTextColor(Color.RED);
                            }

                            historial.setText(historialMensaje);

                            activity.prepararNuevo();

                        } else {
                            Toast.makeText(getActivity(), "ERROR = " + pedidoRESP.get(0).getMensaje(), Toast.LENGTH_LONG).show();
                        }

                    } else {
                        pb.setVisibility(View.INVISIBLE);
                    }
                    pb.setVisibility(View.INVISIBLE);
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

    public ClienteDato getClienteDato() {
        return clienteDato;
    }

    public void setClienteDato(ClienteDato clienteDato) {
        this.clienteDato = clienteDato;

        colocarTituloCliente(codigoCliente,clienteDato.getNombre());


    }


    public double sumarPedido() {

        double total = 0;
        double articulo = 0;

        if (pedidosArticulosArray.size() > 0) {


            for (PedidosArticulo pedidosArticulo : pedidosArticulosArray) {
                articulo = pedidosArticulo.getTotal();
                total = total + articulo;
            }

            return total;

        } else {
            return 0;
        }



    }
}
