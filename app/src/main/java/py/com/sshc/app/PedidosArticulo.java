package py.com.sshc.app;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Emilio on 07/06/15.
 */

public class PedidosArticulo implements Parcelable {

    private Integer numero;
    private String codigo;
    private String descripcion;
    private Double cantidad;
    private Double precio;
    private Double porcDescuento;
    private Double total;

    public PedidosArticulo() {
    }

    public PedidosArticulo(Integer numero, String codigo, String descripcion, Double cantidad, Double precio, Double porcDescuento, Double total) {
        this.numero = numero;
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.precio = precio;
        this.porcDescuento = porcDescuento;
        this.total = total;
    }

    public PedidosArticulo(Parcel in){
        readFromParcel(in);
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Double getPorcDescuento() {
        return porcDescuento;
    }

    public void setPorcDescuento(Double pocDescuento) {
        this.porcDescuento = pocDescuento;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(numero);
        dest.writeString(codigo);
        dest.writeString(descripcion);
        dest.writeDouble(cantidad);
        dest.writeDouble(precio);
        dest.writeDouble(porcDescuento);
        dest.writeDouble(total);

    }

    private void readFromParcel(Parcel in) {

        numero = in.readInt();
        codigo = in.readString();
        descripcion = in.readString();
        cantidad = in.readDouble();
        precio = in.readDouble();
        porcDescuento = in.readDouble();
        total = in.readDouble();

    }

    public static final Parcelable.Creator<PedidosArticulo> CREATOR
            = new Parcelable.Creator<PedidosArticulo>() {
        public PedidosArticulo createFromParcel(Parcel in) {
            return new PedidosArticulo(in);
        }

        public PedidosArticulo[] newArray(int size) {
            return new PedidosArticulo[size];
        }
    };
}
