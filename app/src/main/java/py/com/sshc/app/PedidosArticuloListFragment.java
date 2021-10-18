package py.com.sshc.app;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * A placeholder fragment containing a simple view.
 */
public class PedidosArticuloListFragment extends ListFragment {

    public final int REQUEST_PEDIDO_ARTICULO = 100;
    List<PedidosArticulo> pedidosArticuloArray = new ArrayList<PedidosArticulo>();
    ListView listView;
    PedidosArticuloAdapter adapter;
    String codigoPreciosLista;
    String codigoSucursal;
    Bundle b;
    Integer posicionEnEdicion;

    List<PedidosArticulo> pedidosArticuloOrigen;

    private Callbacks activity;

    public PedidosArticuloListFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        b = getArguments();

        if (b != null) {
            codigoPreciosLista = b.getString(MainActivity.PRECIOLISTA_CODIGO);
            codigoSucursal = b.getString(MainActivity.SUCURSAL_CODIGO);
            pedidosArticuloOrigen = b.getParcelableArrayList("ITEMS");
        } else {
            Toast.makeText(getActivity(), "No se ha recibido todos los datos necesarios.", Toast.LENGTH_SHORT).show();
            getActivity().finish();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.fragment_pedidos_articulo_list, container, false);


        listView = (ListView) vista.findViewById(android.R.id.list);

        SwipeDismissListViewTouchListener touchListener =
                new SwipeDismissListViewTouchListener(
                        listView,
                        new SwipeDismissListViewTouchListener.DismissCallbacks() {
                            @Override
                            public boolean canDismiss(int position) {
                                return true;
                            }

                            @Override
                            public void onDismiss(ListView listView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
                                    adapter.remove(adapter.getItem(position));
                                    adapter.actualizarNumero();
                                }
                                adapter.notifyDataSetChanged();
                            }
                        });
        listView.setOnTouchListener(touchListener);
        // Setting this scroll listener is required to ensure that during ListView scrolling,
        // we don't look for swipes.
        listView.setOnScrollListener(touchListener.makeScrollListener());

        return vista;


    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        PedidosArticulo pedidosArticulo;

        if (pedidosArticuloOrigen != null) {
            pedidosArticulo = pedidosArticuloOrigen.get(position);
        }
        else
        {
            pedidosArticulo = pedidosArticuloArray.get(position);
        }
        Intent intent = new Intent(getActivity(),PedidosArticuloDetailActivity.class);

        intent.putExtra(MainActivity.ELEGIR_ARTICULO_PEDIDO_BUNDLE, b);
        intent.putExtra("ITEM", pedidosArticulo);

        posicionEnEdicion = position;
        startActivityForResult(intent, REQUEST_PEDIDO_ARTICULO);


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_PEDIDO_ARTICULO && resultCode == getActivity().RESULT_OK) {

            PedidosArticulo pedidosArticulo = data.getParcelableExtra("ITEM");

            if (posicionEnEdicion != null) {

                adapter.remove(adapter.getItem(posicionEnEdicion));
                adapter.insert(pedidosArticulo, posicionEnEdicion);
                posicionEnEdicion = null;

            }
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (pedidosArticuloOrigen != null) {
            adapter = new PedidosArticuloAdapter(getActivity(), R.layout.item_pedidos_articulo, pedidosArticuloOrigen);
            setListAdapter(adapter);
        } else {
            adapter = new PedidosArticuloAdapter(getActivity(), R.layout.item_pedidos_articulo, pedidosArticuloArray);
            setListAdapter(adapter);
            activity.agregarArticulo();
        }

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (Callbacks) activity;
    }

    public interface Callbacks {
        public void agregarArticulo();
    }
}
