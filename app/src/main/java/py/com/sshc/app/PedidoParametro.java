package py.com.sshc.app;

/**
 * Created by Emilio on 12/06/15.
 */
public class PedidoParametro {

    private Pedido pedido;
    private ClientAccountOnServer cuenta;

    public PedidoParametro() {
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public ClientAccountOnServer getCuenta() {
        return cuenta;
    }

    public void setCuenta(ClientAccountOnServer cuenta) {
        this.cuenta = cuenta;
    }
}
