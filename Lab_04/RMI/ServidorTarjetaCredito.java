
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class ServidorTarjetaCredito {
    public static void main(String[] args) {
        try {
            //LocateRegistry.createRegistry(1099); // Inicia RMI Registry
            TarjetaCreditoImpl objeto = new TarjetaCreditoImpl();
            Naming.rebind("rmi://10.7.124.144/ServicioTarjeta", objeto);
            System.out.println("Servidor listo...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
