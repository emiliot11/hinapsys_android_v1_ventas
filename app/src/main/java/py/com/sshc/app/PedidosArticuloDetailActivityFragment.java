package py.com.sshc.app;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.ParseException;


/**
 * A placeholder fragment containing a simple view.
 */
public class PedidosArticuloDetailActivityFragment extends Fragment {

    public final int REQUEST_ARTICULO_CONSULTA = 100;

    NumberPicker numberPicker_cantidad;
    EditText editText_precio;
    EditText editText_porcDescuento;
    TextView textView_total;
    String codigoPrecioLista;
    String codigoSucursal;
    EditText editText_codigoArticulo;
    TextView textView_numero;
    Bundle b;
    Bundle b2;
    Button button_articulo;

    Integer cantidadActual = 1;
    Double porcDescuentoActual = 0.00;
    Double precioActual = 0.00;

    Integer nroItems;

    private DecimalFormat df;
    private DecimalFormat dfnd;
    private boolean hasFractionalPart;
    private EditText et;
    private EditText et_descuento;

    PedidosArticulo pedidosArticuloOrigen;

    public PedidosArticuloDetailActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        b = getArguments();

        if (b != null) {
            codigoPrecioLista = b.getString(MainActivity.PRECIOLISTA_CODIGO);
            codigoSucursal = b.getString(MainActivity.SUCURSAL_CODIGO);
            pedidosArticuloOrigen = b.getParcelable("ITEM");
            nroItems = b.getInt("NRO_ITEMS");
            if (nroItems == null) {
                nroItems = 0;
            }
            if (nroItems > 0) {
                nroItems++;
            }
            if (nroItems == 0) {
                nroItems = 1;
            }

        } else {
            Toast.makeText(getActivity(), "DATOS DE ORIGEN NO EXISTENTES", Toast.LENGTH_SHORT).show();
            getActivity().finish();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_pedidos_articulo_detail, container, false);

        numberPicker_cantidad = (NumberPicker) vista.findViewById(R.id.numberPicker_pedidosArticuloDetail_cantidad);

        textView_total = (TextView) vista.findViewById(R.id.textView_formPedidosArticuloDetail_total);

        editText_precio = (EditText) vista.findViewById(R.id.editText_pedidosArticuloDetail_precio);
        editText_porcDescuento = (EditText) vista.findViewById(R.id.editText_pedidosArticuloDetail_porcentaje);

        editText_codigoArticulo = (EditText) vista.findViewById(R.id.editText_formPedidosArticuloDetail_codigo);
        textView_numero = (TextView) vista.findViewById(R.id.textView_formPedidosArticuloDetail_numero);


        textView_numero.setText(nroItems.toString());

        numberPicker_cantidad.setValue(1);
        numberPicker_cantidad.setMinValue(1);
        numberPicker_cantidad.setMaxValue(1000);

        editText_porcDescuento.setText("0");

        df = new DecimalFormat("#,###.##");
        df.setDecimalSeparatorAlwaysShown(true);
        dfnd = new DecimalFormat("#,###");

        et = editText_precio;
        et_descuento = editText_porcDescuento;

        hasFractionalPart = false;

        editText_porcDescuento.addTextChangedListener(new TextWatcher() {


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.toString().contains(String.valueOf(df.getDecimalFormatSymbols().getDecimalSeparator())))
                {
                    hasFractionalPart = true;
                } else {
                    hasFractionalPart = false;
                }

                if(!s.equals("") )
                {
                    porcDescuentoActual = 0.00;
                    calcular();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

                et_descuento.removeTextChangedListener(this);

                try {
                    int inilen, endlen;
                    inilen = et_descuento.getText().length();
                    if (inilen==0){
                        et_descuento.setText("0");
                        inilen = et_descuento.getText().length();
                    }

                    String v = s.toString().replace(String.valueOf(df.getDecimalFormatSymbols().getGroupingSeparator()), "");
                    Number n = df.parse(v);

                    porcDescuentoActual = n.doubleValue();
                    calcular();

                    int cp = et_descuento.getSelectionStart();
                    if (hasFractionalPart) {
                        et_descuento.setText(df.format(n));
                    } else {
                        et_descuento.setText(dfnd.format(n));
                    }
                    endlen = et_descuento.getText().length();
                    int sel = (cp + (endlen - inilen));
                    if (sel > 0 && sel <= et_descuento.getText().length()) {
                        et_descuento.setSelection(sel);
                    } else {
                        // place cursor at the end?
                        et_descuento.setSelection(et_descuento.getText().length() - 1);
                    }
                } catch (NumberFormatException nfe) {
                    // do nothing?
                } catch (ParseException e) {
                    // do nothing?
                }

                et_descuento.addTextChangedListener(this);



            }
        });

        editText_precio.addTextChangedListener(new TextWatcher() {


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.toString().contains(String.valueOf(df.getDecimalFormatSymbols().getDecimalSeparator())))
                {
                    hasFractionalPart = true;
                } else {
                    hasFractionalPart = false;
                }

                if(!s.equals("") )
                {
                    precioActual = 0.00;
                    calcular();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

                et.removeTextChangedListener(this);

                try {
                    int inilen, endlen;
                    inilen = et.getText().length();

                    String v = s.toString().replace(String.valueOf(df.getDecimalFormatSymbols().getGroupingSeparator()), "");
                    Number n = df.parse(v);

                    precioActual = n.doubleValue();
                    calcular();

                    int cp = et.getSelectionStart();
                    if (hasFractionalPart) {
                        et.setText(df.format(n));
                    } else {
                        et.setText(dfnd.format(n));
                    }
                    endlen = et.getText().length();
                    int sel = (cp + (endlen - inilen));
                    if (sel > 0 && sel <= et.getText().length()) {
                        et.setSelection(sel);
                    } else {
                        // place cursor at the end?
                        et.setSelection(et.getText().length() - 1);
                    }
                } catch (NumberFormatException nfe) {
                    // do nothing?
                } catch (ParseException e) {
                    // do nothing?
                }

                et.addTextChangedListener(this);



            }
        });

        numberPicker_cantidad.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

                cantidadActual = newVal;
                calcular();

            }
        });


        button_articulo = (Button) vista.findViewById(R.id.button_formPedidosArticuloDetail_articulo);

        button_articulo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(getActivity(), ArticuloConsultaActivity.class);
                intent.putExtra(MainActivity.ELEGIR_ARTICULO_PEDIDO_BUNDLE,b);
                startActivityForResult(intent, REQUEST_ARTICULO_CONSULTA);


            }
        });

        return vista;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        reconstruirDetalle();

    }

    private void reconstruirDetalle() {

        if (pedidosArticuloOrigen != null) {

            Utiliario utiliario = new Utiliario();

            numberPicker_cantidad.setValue(pedidosArticuloOrigen.getCantidad().intValue());
            editText_porcDescuento.setText(pedidosArticuloOrigen.getPorcDescuento().toString());

            editText_codigoArticulo.setText(pedidosArticuloOrigen.getCodigo().toString());
            textView_numero.setText(pedidosArticuloOrigen.getNumero().toString());
            button_articulo.setText(pedidosArticuloOrigen.getDescripcion().toString());

            editText_precio.setText(utiliario.formatearNumeroSinDecimales(pedidosArticuloOrigen.getPrecio()));
            textView_total.setText(utiliario.formatearNumeroSinDecimales(pedidosArticuloOrigen.getTotal()));

        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);



        if (requestCode == REQUEST_ARTICULO_CONSULTA && resultCode == getActivity().RESULT_OK) {

            String cod = data.getStringExtra("COD");
            String des = data.getStringExtra("DES");
            Double precio = data.getDoubleExtra("PRECIO", 0);

            editText_codigoArticulo.setText(cod);

            editText_precio.setText(new Utiliario().formatearNumeroSinDecimales(precio));

            button_articulo.setText(des);



        }
    }

    public void calcular (Integer cantidad, Double porcDescuento, Double precio) {

        Double total = (cantidad * precio) * (1.00 - (porcDescuento/100));
        textView_total.setText(new Utiliario().formatearNumeroSinDecimales(total));

    }

    public void calcular (){

        calcular(cantidadActual,porcDescuentoActual,precioActual);

    }

}
