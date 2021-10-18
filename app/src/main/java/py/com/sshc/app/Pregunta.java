package py.com.sshc.app;

import java.util.List;

/**
 * Created by Emilio on 04/07/15.
 */
public class Pregunta {

    private Integer numero;
    private String descripcion;
    private String tipo;
    private String codigoPregunta;
    private List<lista> listas;

    public Pregunta() {
    }

    public Pregunta(Integer numero, String descripcion, String tipo, String codigoPregunta, List<lista> listas) {
        this.numero = numero;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.codigoPregunta = codigoPregunta;
        this.listas = listas;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public List<lista> getListas() {
        return listas;
    }

    public void setListas(List<lista> listas) {
        this.listas = listas;
    }

    public lista crearLista(Integer numeroLista, String descripcionLista, Boolean esOtros){

        return new lista(numeroLista,descripcionLista,esOtros);
    }

    public String getCodigoPregunta() {
        return codigoPregunta;
    }

    public void setCodigoPregunta(String codigoPregunta) {
        this.codigoPregunta = codigoPregunta;
    }

    public class lista {

        private Integer lineaLista;
        private Integer numeroLista;
        private String descripcionLista;
        private Boolean esOtros;

        public lista(Integer numeroLista, String descripcionLista, Boolean esOtros) {
            this.numeroLista = numeroLista;
            this.descripcionLista = descripcionLista;
            this.esOtros = esOtros;
        }

        public Integer getNumeroLista() {
            return numeroLista;
        }

        public Integer getLineaLista() {
            return lineaLista;
        }

        public void setLineaLista(Integer lineaLista) {
            this.lineaLista = lineaLista;
        }

        public void setNumeroLista(Integer numeroLista) {
            this.numeroLista = numeroLista;
        }

        public String getDescripcionLista() {
            return descripcionLista;
        }

        public void setDescripcionLista(String descripcionLista) {
            this.descripcionLista = descripcionLista;
        }

        public Boolean getEsOtros() {
            return esOtros;
        }

        public void setEsOtros(Boolean esOtros) {
            this.esOtros = esOtros;
        }
    }

}
