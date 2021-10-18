package py.com.sshc.app;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Emilio on 05/07/15.
 */
public class Encuestas {

    private String cEmpresa;
    private Integer numero;
    private String cCliente;
    private String fecha;
    private String observaciones;
    private List<EncuentasPregunta> encuestaspregunta;
    private String cargado_por;

    public Encuestas() {

        cEmpresa = "01";
        numero = 0;
        fecha = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        observaciones = "Generado via Android";


    }

    public String getcCliente() {
        return cCliente;
    }

    public void setcCliente(String cCliente) {
        this.cCliente = cCliente;
    }

    public List<EncuentasPregunta> getEncuestaspregunta() {
        return encuestaspregunta;
    }

    public void setEncuestaspregunta(List<EncuentasPregunta> encuestaspregunta) {
        this.encuestaspregunta = encuestaspregunta;
    }

    public String getCargado_por() {
        return cargado_por;
    }

    public void setCargado_por(String cargado_por) {
        this.cargado_por = cargado_por;
    }

    public String getcEmpresa() {
        return cEmpresa;
    }

    public void setcEmpresa(String cEmpresa) {
        this.cEmpresa = cEmpresa;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public class EncuentasPregunta {
        private String cEmpresa;
        private Integer numero;
        private Integer linea;
        private String cPregunta;
        private String respuestaTexto;
        private Integer respuestaNumero;
        private List<EncuestasPreguntaLista> encuestasPreguntaLista;

        public EncuentasPregunta() {

            cEmpresa = "01";
            numero = 0;
            respuestaTexto = null;
            respuestaNumero = null;

        }

        public String getcEmpresa() {
            return cEmpresa;
        }

        public void setcEmpresa(String cEmpresa) {
            this.cEmpresa = cEmpresa;
        }

        public Integer getNumero() {
            return numero;
        }

        public void setNumero(Integer numero) {
            this.numero = numero;
        }

        public Integer getLinea() {
            return linea;
        }

        public void setLinea(Integer linea) {
            this.linea = linea;
        }

        public String getcPregunta() {
            return cPregunta;
        }

        public void setcPregunta(String cPregunta) {
            this.cPregunta = cPregunta;
        }

        public String getRespuestaTexto() {
            return respuestaTexto;
        }

        public void setRespuestaTexto(String respuestaTexto) {
            this.respuestaTexto = respuestaTexto;
        }

        public Integer getRespuestaNumero() {
            return respuestaNumero;
        }

        public void setRespuestaNumero(Integer respuestaNumero) {
            this.respuestaNumero = respuestaNumero;
        }

        public List<EncuestasPreguntaLista> getEncuestasPreguntaLista() {
            return encuestasPreguntaLista;
        }

        public void setEncuestasPreguntaLista(List<EncuestasPreguntaLista> encuestasPreguntaLista) {
            this.encuestasPreguntaLista = encuestasPreguntaLista;
        }
    }

    public class EncuestasPreguntaLista {

        private String cEmpresa;
        private Integer numero;
        private Integer linea;
        private String cPregunta;
        private Integer lineaLista;
        private String respuestaOtro;


        public EncuestasPreguntaLista() {

            cEmpresa = "01";
            numero = 0;
            respuestaOtro = null;

        }

        public String getcEmpresa() {
            return cEmpresa;
        }

        public void setcEmpresa(String cEmpresa) {
            this.cEmpresa = cEmpresa;
        }

        public Integer getNumero() {
            return numero;
        }

        public void setNumero(Integer numero) {
            this.numero = numero;
        }

        public String getcPregunta() {
            return cPregunta;
        }

        public void setcPregunta(String cPregunta) {
            this.cPregunta = cPregunta;
        }

        public Integer getLineaLista() {
            return lineaLista;
        }

        public void setLineaLista(Integer lineaLista) {
            this.lineaLista = lineaLista;
        }

        public String getRespuestaOtro() {
            return respuestaOtro;
        }

        public void setRespuestaOtro(String respuestaOtro) {
            this.respuestaOtro = respuestaOtro;
        }

        public Integer getLinea() {
            return linea;
        }

        public void setLinea(Integer linea) {
            this.linea = linea;
        }
    }

    public EncuentasPregunta generarItem(){
        return new EncuentasPregunta();
    }
    public EncuestasPreguntaLista generarItemLista(){
        return new EncuestasPreguntaLista();
    }

}
