package com.SOAPWrapperREST;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ProyectosApi extends HttpServlet{
  private void addHeaderCORS(HttpServletResponse response){
      response.addHeader("Access-Control-Allow-Origin", "http://localhost:8081");
      response.addHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
      response.addHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
  }
  @Override
  protected void doOptions(HttpServletRequest request, HttpServletResponse response) 
          throws ServletException, IOException {
      addHeaderCORS(response);
      response.setStatus(HttpServletResponse.SC_OK);
  }
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
    String data = """
      <lab:getDataTable>
        <tabla>Proyectos</tabla>
      </lab:getDataTable>
    """;
    this.addHeaderCORS(response);
    response.setContentType("text/xml;charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.write(SoapFetchStruct.fetchSOAP(data).body());
  }
}
