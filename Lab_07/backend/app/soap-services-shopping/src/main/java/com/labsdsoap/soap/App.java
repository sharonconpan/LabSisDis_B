package com.labsdsoap.soap;

import jakarta.xml.ws.Endpoint;

public class App 
{
    public static void main( String[] args ){
        String url = "http://0.0.0.0:8080/hello";
        Endpoint.publish(url, new HelloService());
        System.out.println( "Listen to :" + url );
    }
}
