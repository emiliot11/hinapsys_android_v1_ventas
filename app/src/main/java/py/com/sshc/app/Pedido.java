package py.com.sshc.app;

import java.util.List;

/**
 * Created by Emilio on 12/06/15.
 */
public class Pedido {

    private String empresa;
    private String codigoCliente;
    private Integer codigoCondicion;
    private String codigoSucursal;
    private String codigoPrecioLista;
    private List<PedidosArticulo> itemPedidos;
    private ClienteDato clienteDato;

    public Pedido(String empresa, String codigoCliente, Integer codigoCondicion, String codigoSucursal, String codigoPrecioLista, List<PedidosArticulo> itemPedidos, ClienteDato clienteDato) {
        this.empresa = empresa;
        this.codigoCliente = codigoCliente;
        this.codigoCondicion = codigoCondicion;
        this.codigoSucursal = codigoSucursal;
        this.codigoPrecioLista = codigoPrecioLista;
        this.itemPedidos = itemPedidos;
        this.clienteDato = clienteDato;
    }

    public Pedido(String empresa, String codigoCliente, Integer codigoCondicion, String codigoSucursal, String codigoPrecioLista, List<PedidosArticulo> itemPedidos) {
        this.empresa = empresa;
        this.codigoCliente = codigoCliente;
        this.codigoCondicion = codigoCondicion;
        this.codigoSucursal = codigoSucursal;
        this.codigoPrecioLista = codigoPrecioLista;
        this.itemPedidos = itemPedidos;
    }

    public Pedido() {
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(String codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    public Integer getCodigoCondicion() {
        return codigoCondicion;
    }

    public void setCodigoCondicion(Integer codigoCondicion) {
        this.codigoCondicion = codigoCondicion;
    }

    public String getCodigoSucursal() {
        return codigoSucursal;
    }

    public void setCodigoSucursal(String codigoSucursal) {
        this.codigoSucursal = codigoSucursal;
    }

    public String getCodigoPrecioLista() {
        return codigoPrecioLista;
    }

    public void setCodigoPrecioLista(String codigoPrecioLista) {
        this.codigoPrecioLista = codigoPrecioLista;
    }

    public List<PedidosArticulo> getItemPedidos() {
        return itemPedidos;
    }

    public void setItemPedidos(List<PedidosArticulo> itemPedidos) {
        this.itemPedidos = itemPedidos;
    }

    public ClienteDato getClienteDato() {
        return clienteDato;
    }

    public void setClienteDato(ClienteDato clienteDato) {
        this.clienteDato = clienteDato;
    }
}
