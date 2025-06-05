package com.labsdsoap.soap;

import jakarta.xml.ws.Endpoint;
import java.util.ArrayList;

public class App 
{
    public static void main( String[] args ){
        StoreService store = new StoreService();

        // Cargar datos ficticios
        Producto p1 = new Producto(); p1.setName("Película A"); p1.setCantidad(5); p1.setCode("A01");
        Producto p2 = new Producto(); p2.setName("Película B"); p2.setCantidad(3); p2.setCode("B01");

        Tipo tipo = new Tipo();
        tipo.setName("Películas");
        ArrayList<Producto> lista = new ArrayList<>();
        lista.add(p1); lista.add(p2);
        tipo.setStorage(lista);

        store.addTipo(tipo);

        // Publicar
        String url = "http://0.0.0.0:8080/soap/store";
        Endpoint.publish(url, store);
        System.out.println("Servicio Store escuchando en: " + url);
    }
}
