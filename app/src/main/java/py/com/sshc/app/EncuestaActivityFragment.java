package py.com.sshc.app;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableRow;
import android.widget.TextView;
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
public class EncuestaActivityFragment extends Fragment {

    public final int REQUEST_CLIENTE = 100;

    FrameLayout frameCargando;
    LinearLayout listapreguntas;
    Button elegirClienteBoton;

    List<Respuesta> respuestas = new ArrayList<>();


    private String codigoCliente;
    private String codigoSucursal;
    private String esVarios;
    View vista;
    StringUtil stringUtil = new StringUtil();




    public EncuestaActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        vista =  inflater.inflate(R.layout.fragment_encuesta, container, false);
        return vista;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CLIENTE && resultCode == getActivity().RESULT_OK) {

            String cod = data.getStringExtra("COD");
            String des = data.getStringExtra("DES");
            String suc = data.getStringExtra("SUC");
            String varios = data.getStringExtra("VARIOS");


            elegirClienteBoton.setText(cod + " " + des);

            codigoCliente = cod;
            codigoSucursal = suc;
            esVarios = varios;

        }
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



        buscarPreguntas();

    }

    public void aplicarEncuesta(List<Pregunta> preguntaList){

        int optionId = R.layout.fragment_encuesta;

        ViewGroup parent = (ViewGroup) vista.getParent();
        int index = parent.indexOfChild(vista);
        parent.removeView(vista);

        vista = getActivity().getLayoutInflater().inflate(optionId, parent, false);

        elegirClienteBoton = (Button) vista.findViewById(R.id.buttonElegirCliente);
        elegirClienteBoton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ClientesActivity.class);
                startActivityForResult(intent, REQUEST_CLIENTE);
            }
        });

        listapreguntas = (LinearLayout) vista.findViewById(R.id.linearlayout_listapreguntas);

        aplicarPreguntas(preguntaList);

        frameCargando = (FrameLayout) vista.findViewById(R.id.frameEncuesta_cargando);
        frameCargando.setVisibility(View.GONE);

        parent.addView(vista, index);

    }


    public void aplicarPreguntas(List<Pregunta> preguntaList){

        for(Pregunta pregunta : preguntaList){


            switch (pregunta.getTipo()){

                case "T"://TEXTO

                    configurarPreguntaTexto(pregunta);

                    break;
                case "N"://NUMERO

                    configurarPreguntaNumero(pregunta);

                    break;
                case "D"://DECIMAL

                    //A IMPLEMENTAR

                    break;
                case "B"://SI/NO

                    configurarPreguntaBoolean(pregunta);

                    break;
                case "L"://LISTA


                    configurarPreguntaLista(pregunta);

                    break;
                case "V"://LISTA MULTIPLE


                    configurarPreguntaListaMultiple(pregunta);

                    break;
                default:
                    break;


            }



        }



    }

    private void configurarPreguntaListaMultiple(Pregunta pregunta) {

        TextView preguntaDescripcion;
        LinearLayout linearLayout;
        List<CheckBox> respuestasCheckbox = new ArrayList<>();
        List<EditText> respuestasEditText = new ArrayList<>();

        View vistaPregunta = getActivity().getLayoutInflater().inflate(R.layout.item_pregunta_lista_multiple, null, false);
        preguntaDescripcion = (TextView) vistaPregunta.findViewById(R.id.textView_pregunta);
        preguntaDescripcion.setText(pregunta.getNumero().toString() + ". " + pregunta.getDescripcion());


        Respuesta respuesta = new Respuesta();
        respuesta.setNumero(pregunta.getNumero());
        respuesta.setTipo(pregunta.getTipo());


        linearLayout = (LinearLayout) vistaPregunta.findViewById(R.id.linearLayout_pregunta_lista_multiple);

        List<Pregunta.lista> listas = pregunta.getListas();

        if (listas.size() > 0) {

            for (Pregunta.lista lista : listas) {

                if (!lista.getEsOtros()) {


                    CheckBox checkBox = new CheckBox(getActivity());

                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                    layoutParams.setMargins(15, 15, 15, 15);

                    checkBox.setTextAppearance(getActivity(), android.R.style.TextAppearance_Large);


                    checkBox.setText(lista.getDescripcionLista());
                    checkBox.setTag(lista.getLineaLista().toString());

                    checkBox.setGravity(Gravity.LEFT);
                    checkBox.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

                    respuestasCheckbox.add(checkBox);

                    linearLayout.addView(checkBox,layoutParams);

                } else {

                    EditText editText_otros;
                    editText_otros = new EditText(getActivity());
                    editText_otros.setGravity(Gravity.LEFT);
                    editText_otros.setHint(lista.getDescripcionLista());
                    editText_otros.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                    editText_otros.setTag(lista.getLineaLista().toString());

                    respuesta.setRespuestaOtros(editText_otros);

                    respuestasEditText.add(editText_otros);

                    linearLayout.addView(editText_otros);


                }
            }
        }

        respuesta.setCodigoRespuesta(pregunta.getCodigoPregunta());
        respuesta.setRespuestasListaMultiple(respuestasCheckbox);
        respuesta.setRespuestasListaMultipleOtros(respuestasEditText);
        respuestas.add(respuesta);

        listapreguntas.addView(vistaPregunta);


    }


    private void configurarPreguntaLista(Pregunta pregunta) {

        TextView preguntaDescripcion;
        RadioGroup radioGroup;

        View vistaPregunta = getActivity().getLayoutInflater().inflate(R.layout.item_pregunta_lista, null, false);
        preguntaDescripcion = (TextView) vistaPregunta.findViewById(R.id.textView_pregunta);
        preguntaDescripcion.setText(pregunta.getNumero().toString() + ". " + pregunta.getDescripcion());

        radioGroup = (RadioGroup) vistaPregunta.findViewById(R.id.radioGroup_pregunta_lista);


        Respuesta respuesta = new Respuesta();
        respuesta.setNumero(pregunta.getNumero());
        respuesta.setTipo(pregunta.getTipo());
        respuesta.setRespuestaLista(radioGroup);



        List<Pregunta.lista> listas = pregunta.getListas();

        List<EditText> respuestaListaOtros = new ArrayList<EditText>();
        List<RadioButton> respuestaListaOtrosRadioButton  = new ArrayList<RadioButton>();

        if (listas.size() > 0) {



            for (Pregunta.lista lista : listas){



                if (!lista.getEsOtros()) {
                    RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(
                            RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT);

                    layoutParams.setMargins(15, 15, 15, 15);

                    RadioButton radioButton = new RadioButton(getActivity());

                    radioButton.setText(lista.getDescripcionLista());
                    radioButton.setTag(lista.getLineaLista().toString());
                    radioButton.setGravity(Gravity.LEFT);


                    radioButton.setTextAppearance(getActivity(), android.R.style.TextAppearance_Large);

                    radioGroup.addView(radioButton,layoutParams);
                } else
                {
                    RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT);
                    RadioGroup.LayoutParams layoutParams2 = new RadioGroup.LayoutParams(
                            RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT);

                    layoutParams.setMargins(15, 15, 15, 15);
                    layoutParams2.setMargins(15, 15, 15, 15);

                    RadioButton radioButton = new RadioButton(getActivity());

                    radioButton.setTextAppearance(getActivity(), android.R.style.TextAppearance_Large);

                    radioButton.setText(lista.getDescripcionLista());
                    radioButton.setGravity(Gravity.LEFT);
                    radioButton.setTag(lista.getLineaLista().toString());

                    radioGroup.addView(radioButton,layoutParams);


                    EditText editText_otros;
                    editText_otros = new EditText(getActivity());
                    editText_otros.setHint(lista.getDescripcionLista());
                    editText_otros.setGravity(Gravity.LEFT);

                    respuesta.setRespuestaOtros(editText_otros);

                    radioGroup.addView(editText_otros, layoutParams2);


                    respuestaListaOtros.add(editText_otros);
                    respuestaListaOtrosRadioButton.add(radioButton);


                }


            }
        }
        respuesta.setCodigoRespuesta(pregunta.getCodigoPregunta());
        respuesta.setRespuestaListaOtros(respuestaListaOtros);
        respuesta.setRespuestaListaOtrosRadioButton(respuestaListaOtrosRadioButton);

        respuestas.add(respuesta);
        listapreguntas.addView(vistaPregunta);

    }

    private void configurarPreguntaBoolean(Pregunta pregunta) {

        CheckBox preguntaDescripcion;

        View vistaPregunta = getActivity().getLayoutInflater().inflate(R.layout.item_pregunta_boolean, null, false);
        preguntaDescripcion = (CheckBox) vistaPregunta.findViewById(R.id.checkBox_pregunta_boolean);
        preguntaDescripcion.setText(pregunta.getNumero().toString() + ". " + pregunta.getDescripcion());
        preguntaDescripcion.setChecked(false);
        listapreguntas.addView(vistaPregunta);

        Respuesta respuesta = new Respuesta();
        respuesta.setNumero(pregunta.getNumero());
        respuesta.setTipo(pregunta.getTipo());
        respuesta.setRespuestaBoolean(preguntaDescripcion);
        respuesta.setCodigoRespuesta(pregunta.getCodigoPregunta());

        respuestas.add(respuesta);

    }

    private void configurarPreguntaNumero(Pregunta pregunta) {

        NumberPicker numero;
        TextView preguntaDescripcion;

        View vistaPregunta = getActivity().getLayoutInflater().inflate(R.layout.item_pregunta_numero, null, false);
        preguntaDescripcion = (TextView) vistaPregunta.findViewById(R.id.textView_pregunta);
        preguntaDescripcion.setText(pregunta.getNumero().toString() + ". " +  pregunta.getDescripcion());

        numero = (NumberPicker) vistaPregunta.findViewById(R.id.numberPicker_pregunta_numero);
        numero.setValue(0);
        numero.setMinValue(0);
        numero.setMaxValue(20);

        listapreguntas.addView(vistaPregunta);

        Respuesta respuesta = new Respuesta();
        respuesta.setNumero(pregunta.getNumero());
        respuesta.setTipo(pregunta.getTipo());
        respuesta.setRespuestaNumero(numero);
        respuesta.setCodigoRespuesta(pregunta.getCodigoPregunta());

        respuestas.add(respuesta);

    }

    private void configurarPreguntaTexto(Pregunta pregunta) {

        TextView preguntaDescripcion;
        EditText preguntaRespuesta;

        View vistaPregunta = getActivity().getLayoutInflater().inflate(R.layout.item_pregunta_texto, null, false);
        preguntaDescripcion = (TextView) vistaPregunta.findViewById(R.id.textView_pregunta);
        preguntaDescripcion.setText(pregunta.getNumero().toString() + ". " + pregunta.getDescripcion());

        preguntaRespuesta = (EditText) vistaPregunta.findViewById(R.id.editText_respuesta_texto);
        //preguntaRespuesta.setTag(pregunta);

        listapreguntas.addView(vistaPregunta);

        Respuesta respuesta = new Respuesta();
        respuesta.setNumero(pregunta.getNumero());
        respuesta.setTipo(pregunta.getTipo());
        respuesta.setRespuestaTexto(preguntaRespuesta);
        respuesta.setCodigoRespuesta(pregunta.getCodigoPregunta());

        respuestas.add(respuesta);

    }

    public void buscarPreguntas() {

        NetworkUtility networkUtility = new NetworkUtility();
        if (networkUtility.comprobarConexionConMensaje(getActivity()) == false) {return;};

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(new PreferencesManager(getActivity()).getServidorConServicio())
                .build();

        PreguntaAPI api = adapter.create(PreguntaAPI.class);

        PreguntaParametros preguntaParametros = new PreguntaParametros();
        preguntaParametros.setEmpresa("01");
        preguntaParametros.setCuenta(new PreferencesManager(getActivity()).getCuenta(getActivity()));

        api.getPreguntas(preguntaParametros, new Callback<List<Pregunta>>() {


            @Override
            public void success(List<Pregunta> preguntas, Response response) {
                if (getActivity() == null) {
                    return;
                }
                if (preguntas != null) {
                    if (preguntas.size() > 0) {
                        aplicarEncuesta(preguntas);

                    } else {
                        comunicarError();
                    }
                } else {
                    comunicarError();
                }
            }

            @Override
            public void failure(RetrofitError error) {

                if (getActivity() == null) {
                    return;
                }

                String errorDescripcion = "";

                error.printStackTrace();

                if (error.getResponse() != null) {
                    try {

                        errorDescripcion = stringUtil.convertStreamToString(error.getResponse().getBody().in());
                        if (errorDescripcion != null && error.getResponse().getStatus() == 500) {
                            Toast.makeText(getActivity(), errorDescripcion, Toast.LENGTH_SHORT).show();
                            comunicarError();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        comunicarError();
                    }
                } else {
                    Toast.makeText(getActivity(), "ERROR", Toast.LENGTH_SHORT).show();
                    comunicarError();
                }
            }
        });


    }


    public void comunicarError() {

        Toast.makeText(getActivity(), "LA ENCUENTA NO PUDO SER CARGADA", Toast.LENGTH_LONG).show();

    }


    public void guardarEncuesta(){

        Encuestas encuestas = null;

        try {
            encuestas = crearRespuestaEncuesta();
        } catch (Exception e){

            Toast.makeText(getActivity(), "Debe llenar todos los datos", Toast.LENGTH_SHORT).show();
            return;

        }

        NetworkUtility networkUtility = new NetworkUtility();
        if (networkUtility.comprobarConexionConMensaje(getActivity()) == false) {return;};

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(new PreferencesManager(getActivity()).getServidorConServicio())
                .build();

        EncuestaAPI api = adapter.create(EncuestaAPI.class);

        EncuestaParametros encuestaParametros = new EncuestaParametros();
        encuestaParametros.setEmpresa("01");


        encuestaParametros.setEncuestas(encuestas);
        encuestaParametros.setCuenta(new PreferencesManager(getActivity()).getCuenta(getActivity()));


        frameCargando.setVisibility(View.VISIBLE);
        api.setRespuestaEncuesta(encuestaParametros, new Callback<List<EncuestaRESP>>() {


            @Override
            public void success(List<EncuestaRESP> encuestaRESPs, Response response) {
                if (getActivity() == null) {
                    return;
                }
                if (encuestaRESPs != null) {
                    if (encuestaRESPs.size() > 0) {
                        EncuestaRESP encuestaRESP = encuestaRESPs.get(0);

                        if (encuestaRESP.getEstado()) {
                            frameCargando.setVisibility(View.INVISIBLE);
                            Toast.makeText(getActivity(), "ENCUESTA GUARDADA", Toast.LENGTH_SHORT).show();

                            ((EncuestaActivity) getActivity()).limpiar();

                        }
                        else
                        {
                            Toast.makeText(getActivity(), "ERROR " + encuestaRESP.getMensaje(), Toast.LENGTH_SHORT).show();
                            frameCargando.setVisibility(View.INVISIBLE);
                        }


                    } else {
                        frameCargando.setVisibility(View.INVISIBLE);
                    }
                } else {
                    frameCargando.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void failure(RetrofitError error) {

                if (getActivity() == null) {
                    return;
                }

                String errorDescripcion = "";

                frameCargando.setVisibility(View.INVISIBLE);
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

    public Encuestas crearRespuestaEncuesta() throws Exception{

        Integer lineaPregunta = 0;
        String codigoPregunta;

        Encuestas resultado = new Encuestas();

        resultado.setcCliente(codigoCliente);

        if (codigoCliente == null) {
            throw new Exception("falta");
        }
        if ("".equals(codigoCliente)) {
            throw new Exception("falta");
        }

        resultado.setCargado_por(new PreferencesManager(getActivity()).getCuenta(getActivity()).getUsuario());

        List<Encuestas.EncuentasPregunta> encuentasPreguntaList = encuentasPreguntaList = new ArrayList<>();;

        Encuestas.EncuentasPregunta encuentasPregunta;


        for (Respuesta respuesta : respuestas){

            lineaPregunta = lineaPregunta + 1;

            encuentasPregunta = resultado.generarItem();



            switch (respuesta.getTipo()){

                case "T":



                    codigoPregunta = respuesta.getCodigoRespuesta();

                    encuentasPregunta.setLinea(lineaPregunta);

                    encuentasPregunta.setcPregunta(codigoPregunta);

                    encuentasPregunta.setRespuestaTexto(respuesta.getRespuestaTexto().getText().toString());

                    encuentasPreguntaList.add(encuentasPregunta);

                    if (respuesta.getRespuestaTexto().getText().toString().length() == 0) {
                        throw new Exception("falta");
                    }

                    break;

                case "N":

                    codigoPregunta = respuesta.getCodigoRespuesta();

                    encuentasPregunta.setLinea(lineaPregunta);

                    encuentasPregunta.setcPregunta(codigoPregunta);
                    encuentasPregunta.setRespuestaNumero(respuesta.getRespuestaNumero().getValue());

                    encuentasPreguntaList.add(encuentasPregunta);

                    break;

                case "B":

                    codigoPregunta = respuesta.getCodigoRespuesta();

                    encuentasPregunta.setLinea(lineaPregunta);

                    encuentasPregunta.setcPregunta(codigoPregunta);
                    if (respuesta.getRespuestaBoolean().isChecked()) {
                        encuentasPregunta.setRespuestaTexto("SI");
                    }
                    else {
                        encuentasPregunta.setRespuestaTexto("NO");
                    };

                    encuentasPreguntaList.add(encuentasPregunta);


                    break;
                case "L":

                    RadioGroup listaRadioButton = new RadioGroup(getActivity());

                    List<Encuestas.EncuestasPreguntaLista> itemListas2 = new ArrayList<Encuestas.EncuestasPreguntaLista>();

                    Encuestas.EncuestasPreguntaLista itemLista2 = resultado.generarItemLista();

                    encuentasPregunta.setLinea(lineaPregunta);

                    codigoPregunta = respuesta.getCodigoRespuesta();
                    encuentasPregunta.setcPregunta(codigoPregunta);

                    listaRadioButton = respuesta.getRespuestaLista();

                    int selected = listaRadioButton.getCheckedRadioButtonId();

                    if (selected < 0 ) {
                        throw new Exception("falta");
                    }

                    RadioButton radioLista = (RadioButton) getActivity().findViewById(selected);

                    itemLista2.setcPregunta(codigoPregunta);
                    itemLista2.setLinea(lineaPregunta);
                    itemLista2.setLineaLista(Integer.valueOf(radioLista.getTag().toString()));

                    EditText et_otros = new EditText(getActivity());

                    for ( RadioButton radioButtonOtros : respuesta.getRespuestaListaOtrosRadioButton()  ) {

                        if (radioButtonOtros == radioLista )  {

                            et_otros = respuesta.getRespuestaListaOtros().get(respuesta.getRespuestaListaOtrosRadioButton().indexOf(radioButtonOtros));

                            if (et_otros.getText().toString().length() == 0 ) {
                                throw new Exception("falta");
                            } else
                            {
                                itemLista2.setRespuestaOtro(et_otros.getText().toString());
                            }
                        }
                    }



                    itemListas2.add(itemLista2);

                    encuentasPregunta.setEncuestasPreguntaLista(itemListas2);




                    encuentasPreguntaList.add(encuentasPregunta);

                    break;
                case "V":


                    List<CheckBox> listaCheckBox = new ArrayList<CheckBox>();
                    List<EditText> listaEditText = new ArrayList<EditText>();

                    List<Encuestas.EncuestasPreguntaLista> itemListas = new ArrayList<Encuestas.EncuestasPreguntaLista>();
                    Encuestas.EncuestasPreguntaLista itemLista;

                    encuentasPregunta.setLinea(lineaPregunta);

                    codigoPregunta = respuesta.getCodigoRespuesta();

                    encuentasPregunta.setcPregunta(codigoPregunta);

                    listaCheckBox = respuesta.getRespuestasListaMultiple();
                    listaEditText = respuesta.getRespuestasListaMultipleOtros();


                    for (CheckBox checkLista : listaCheckBox) {

                        if (checkLista.isChecked()) {

                            itemLista = resultado.generarItemLista();

                            Integer lineaLista = Integer.valueOf(checkLista.getTag().toString());

                            itemLista.setcPregunta(codigoPregunta);
                            itemLista.setLinea(lineaPregunta);
                            itemLista.setLineaLista(lineaLista) ;
                            itemListas.add(itemLista);

                        }

                    };

                    for (EditText editTextLista : listaEditText) {

                        if (editTextLista.getText().length() > 0) {

                            itemLista = resultado.generarItemLista();

                            Integer lineaLista = Integer.valueOf(editTextLista.getTag().toString());

                            itemLista.setcPregunta(codigoPregunta);
                            itemLista.setLinea(lineaPregunta);
                            itemLista.setLineaLista(lineaLista) ;
                            itemLista.setRespuestaOtro(editTextLista.getText().toString());
                            itemListas.add(itemLista);

                        }

                    };

                    if (itemListas.size() == 0 ) {
                        throw new Exception("falta");
                    }

                    encuentasPregunta.setEncuestasPreguntaLista(itemListas);

                    encuentasPreguntaList.add(encuentasPregunta);

                    break;
            }

            resultado.setEncuestaspregunta(encuentasPreguntaList);

        }

        return  resultado;
    }

    public class Respuesta {


        private Integer numero;
        private String tipo;
        private EditText respuestaTexto;
        private NumberPicker respuestaNumero;
        private CheckBox respuestaBoolean;
        private RadioGroup respuestaLista;
        private List<EditText> respuestaListaOtros;
        private List<RadioButton> respuestaListaOtrosRadioButton;
        private List<CheckBox> respuestasListaMultiple;
        private List<EditText> respuestasListaMultipleOtros;
        private EditText respuestaOtros;
        private String codigoRespuesta;

        public Respuesta() {
        }

        public List<RadioButton> getRespuestaListaOtrosRadioButton() {
            return respuestaListaOtrosRadioButton;
        }

        public void setRespuestaListaOtrosRadioButton(List<RadioButton> respuestaListaOtrosRadioButton) {
            this.respuestaListaOtrosRadioButton = respuestaListaOtrosRadioButton;
        }

        public List<EditText> getRespuestaListaOtros() {
            return respuestaListaOtros;
        }

        public void setRespuestaListaOtros(List<EditText> respuestaListaOtros) {
            this.respuestaListaOtros = respuestaListaOtros;
        }

        public List<EditText> getRespuestasListaMultipleOtros() {
            return respuestasListaMultipleOtros;
        }

        public void setRespuestasListaMultipleOtros(List<EditText> respuestasListaMultipleOtros) {
            this.respuestasListaMultipleOtros = respuestasListaMultipleOtros;
        }

        public String getCodigoRespuesta() {
            return codigoRespuesta;
        }

        public void setCodigoRespuesta(String codigoRespuesta) {
            this.codigoRespuesta = codigoRespuesta;
        }

        public Integer getNumero() {
            return numero;
        }

        public void setNumero(Integer numero) {
            this.numero = numero;
        }

        public String getTipo() {
            return tipo;
        }

        public void setTipo(String tipo) {
            this.tipo = tipo;
        }

        public EditText getRespuestaTexto() {
            return respuestaTexto;
        }

        public void setRespuestaTexto(EditText respuestaTexto) {
            this.respuestaTexto = respuestaTexto;
        }

        public NumberPicker getRespuestaNumero() {
            return respuestaNumero;
        }

        public void setRespuestaNumero(NumberPicker respuestaNumero) {
            this.respuestaNumero = respuestaNumero;
        }

        public CheckBox getRespuestaBoolean() {
            return respuestaBoolean;
        }

        public void setRespuestaBoolean(CheckBox respuestaBoolean) {
            this.respuestaBoolean = respuestaBoolean;
        }

        public RadioGroup getRespuestaLista() {
            return respuestaLista;
        }

        public void setRespuestaLista(RadioGroup respuestaLista) {
            this.respuestaLista = respuestaLista;
        }

        public List<CheckBox> getRespuestasListaMultiple() {
            return respuestasListaMultiple;
        }

        public void setRespuestasListaMultiple(List<CheckBox> respuestasListaMultiple) {
            this.respuestasListaMultiple = respuestasListaMultiple;
        }

        public EditText getRespuestaOtros() {
            return respuestaOtros;
        }

        public void setRespuestaOtros(EditText respuestaOtros) {
            this.respuestaOtros = respuestaOtros;
        }
    }



}
