import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;

public class SaxParserEjemplo {
    public static void main(String[] args) {
        try {
            File archivoXML = new File("boeHeavy_100k.xml");

            long tiempoInicio = System.nanoTime(); // Tiempo inicial

            SAXParserFactory factory = SAXParserFactory.newInstance();
            factory.setValidating(false); // Desactivamos validación para este ejemplo
            SAXParser saxParser = factory.newSAXParser();

            DefaultHandler manejador = new DefaultHandler() {
                boolean dentroTitulo = false;

                public void startElement(String uri, String localName, String qName, Attributes attributes) {
                    if (qName.equalsIgnoreCase("TIT-SECCION")) {
                        dentroTitulo = true;
                    }
                }

                public void characters(char[] ch, int start, int length) {
                    if (dentroTitulo) {
                        System.out.println("Seccion: " + new String(ch, start, length));
                        dentroTitulo = false;
                    }
                }

                public void endElement(String uri, String localName, String qName) {
                    // No necesitamos acciones aquí por ahora
                }
            };

            saxParser.parse(archivoXML, manejador);

            long tiempoFin = System.nanoTime(); // Tiempo final
            long duracion = (tiempoFin - tiempoInicio) / 1_000_000; // en milisegundos

            System.out.println("Tiempo de ejecucion: " + duracion + " ms");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
