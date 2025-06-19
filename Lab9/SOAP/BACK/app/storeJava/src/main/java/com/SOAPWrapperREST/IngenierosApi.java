package com.SOAPWrapperREST;

import java.io.PrintWriter;
import java.io.IOException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;

public class IngenierosApi extends HttpServlet{
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
        <tabla>Ingenieros</tabla>
      </lab:getDataTable>
    """;
    addHeaderCORS(response);
    response.setContentType("text/xml;charset=UTF-8");
    PrintWriter pw = response.getWriter();
    pw.write(SoapFetchStruct.fetchSOAP(data).body());
  }
}
