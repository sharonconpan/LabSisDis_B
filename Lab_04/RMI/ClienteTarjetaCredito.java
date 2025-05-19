
import java.rmi.Naming;

public class ClienteTarjetaCredito {
 public static void main(String[] args) {
     try {
         TarjetaCreditoInterface tarjeta = (TarjetaCreditoInterface) Naming.lookup("rmi://10.7.124.144/ServicioTarjeta");

         String numero = "123456";
         double monto = 200.0;

         if (tarjeta.autorizar(numero, monto)) {
             System.out.println("Autorizado. Realizando cobro de " + monto);
             tarjeta.cobrar(numero, monto);
             System.out.println("Nuevo saldo: " + tarjeta.obtenerSaldo(numero));
         } else {
             System.out.println("Fondos insuficientes.");
         }

     } catch (Exception e) {
         e.printStackTrace();
     }
 }
}

