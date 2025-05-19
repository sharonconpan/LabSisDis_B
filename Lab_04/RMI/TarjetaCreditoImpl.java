

import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.util.HashMap;

public class TarjetaCreditoImpl extends UnicastRemoteObject implements TarjetaCreditoInterface {
 private HashMap<String, Double> saldos;

 public TarjetaCreditoImpl() throws RemoteException {
     saldos = new HashMap<>();
     saldos.put("123456", 1500.0); // Tarjeta con saldo inicial
 }

 public boolean autorizar(String numeroTarjeta, double monto) throws RemoteException {
     return saldos.containsKey(numeroTarjeta) && saldos.get(numeroTarjeta) >= monto;
 }

 public double obtenerSaldo(String numeroTarjeta) throws RemoteException {
     return saldos.getOrDefault(numeroTarjeta, 0.0);
 }

 public void cobrar(String numeroTarjeta, double monto) throws RemoteException {
     if (autorizar(numeroTarjeta, monto)) {
         saldos.put(numeroTarjeta, saldos.get(numeroTarjeta) - monto);
     }
 }
}

