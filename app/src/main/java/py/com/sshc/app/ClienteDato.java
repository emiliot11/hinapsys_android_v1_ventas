package py.com.sshc.app;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Emilio on 25/06/15.
 */
public class ClienteDato implements Parcelable {

    private String nombre;
    private String ruc;
    private String direccion;
    private String telefono;
    private String email;

    public ClienteDato(){

    }

    public ClienteDato(Parcel in){
        readFromParcel(in);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(nombre);
        dest.writeString(ruc);
        dest.writeString(direccion);
        dest.writeString(telefono);
        dest.writeString(email);


    }

    private void readFromParcel(Parcel in) {

        nombre = in.readString();
        ruc = in.readString();
        direccion = in.readString();
        telefono = in.readString();
        email = in.readString();


    }

    public static final Parcelable.Creator<ClienteDato> CREATOR
            = new Parcelable.Creator<ClienteDato>() {
        public ClienteDato createFromParcel(Parcel in) {
            return new ClienteDato(in);
        }

        public ClienteDato[] newArray(int size) {
            return new ClienteDato[size];
        }
    };
}
