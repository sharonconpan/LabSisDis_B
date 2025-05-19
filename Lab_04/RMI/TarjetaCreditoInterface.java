

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface TarjetaCreditoInterface extends Remote {
    boolean autorizar(String numeroTarjeta, double monto) throws RemoteException;
    double obtenerSaldo(String numeroTarjeta) throws RemoteException;
    void cobrar(String numeroTarjeta, double monto) throws RemoteException;
}
