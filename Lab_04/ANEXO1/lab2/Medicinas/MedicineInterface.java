package Medicinas;
import java.rmi.Remote;
public interface MedicineInterface extends Remote {
public Medicine getMedicine( int amount ) throws Exception;
public int getStock() throws Exception;
public String print() throws Exception;
}
