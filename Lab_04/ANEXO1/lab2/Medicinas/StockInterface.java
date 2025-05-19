
package Medicinas;
import java.rmi.Remote;
import java.util.ArrayList;
import java.util.*;
public interface StockInterface extends Remote {
public HashMap<String, MedicineInterface> getStockProducts() throws Exception;
public void addMedicine( String name, float price, int stock) throws Exception;
public MedicineInterface buyMedicine(String name, int amount) throws Exception;
}
