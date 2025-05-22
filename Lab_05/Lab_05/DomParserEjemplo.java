import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.File;

public class DomParserEjemplo {
    public static void main(String[] args) {
        try {
            File archivoXML = new File("boeHeavy_100k.xml");

            long tiempoInicio = System.nanoTime(); // Tiempo inicial

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            dbFactory.setValidating(false); // Desactivamos validación para este ejemplo
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(archivoXML);
            doc.getDocumentElement().normalize();

            System.out.println("Elemento raiz: " + doc.getDocumentElement().getNodeName());

            NodeList lista = doc.getElementsByTagName("SECCION");

            for (int i = 0; i < lista.getLength(); i++) {
                Element seccion = (Element) lista.item(i);
                String titulo = seccion.getElementsByTagName("TIT-SECCION").item(0).getTextContent();
                System.out.println("Seccion: " + titulo);
            }

            long tiempoFin = System.nanoTime(); // Tiempo final
            long duracion = (tiempoFin - tiempoInicio) / 1_000_000; // en milisegundos

            System.out.println("Tiempo de ejecucion: " + duracion + " ms");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
