
package Medicinas;
import java.rmi.*;
public class ServerSide {
public static void main(String [] args) throws Exception {
Stock pharmacy = new Stock();
pharmacy.addMedicine("Paracetamol", 3.2f, 10);
pharmacy.addMedicine("Mejoral", 2.0f, 20);
pharmacy.addMedicine("Amoxilina", 1.0f, 30);
pharmacy.addMedicine("Aspirina", 5.0f, 40);
// Naming.rebind("PHARMACY", pharmacy);
System.setProperty("java.rmi.server.hostname", "server");
Naming.rebind("rmi://server:1099/PHARMACY", pharmacy);
System.out.println("Server ready");
}
}
