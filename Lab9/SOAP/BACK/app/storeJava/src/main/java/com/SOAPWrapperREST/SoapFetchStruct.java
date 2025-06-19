package com.SOAPWrapperREST;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest;
import java.net.URI;

public class SoapFetchStruct{
  public static String getSoapStruct(String content){
    String soapRequest = """
        <?xml version="1.0" encoding="UTF-8"?>
        <soapenv:Envelope 
          xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
          xmlns:lab="http://SOAPService.com/">
          <soapenv:Header/>
          <soapenv:Body>
          """ + content +
          """
          </soapenv:Body>
        </soapenv:Envelope>
        """;
    return soapRequest;
  }
  public static HttpResponse<String> fetchSOAP(String content) throws IOException{
    try{
      String url = "http://localhost:8081/soap";
      String data = getSoapStruct(content);
      HttpClient cliente = HttpClient.newHttpClient();
      HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(url))
        .header("Content-Type","text/xml;charset=UTF-8")
        .header("SOAPAction","")
        .POST(HttpRequest.BodyPublishers.ofString(data))
        .build();
      HttpResponse<String> response = cliente.send(request, HttpResponse.BodyHandlers.ofString());
      return response;
    }catch(InterruptedException e){
      System.out.println("Error de Peticion!!");
      return null;
    }
  }
}
