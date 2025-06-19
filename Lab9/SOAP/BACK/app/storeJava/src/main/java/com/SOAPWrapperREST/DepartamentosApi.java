package com.SOAPWrapperREST;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.FileReader;
public class DepartamentosApi extends HttpServlet {
    private String message;
    @Override
    public void init() throws ServletException {}

    private void addHeaderCORS(HttpServletResponse response){
        response.addHeader("Access-Control-Allow-Origin", "http://localhost");
        response.addHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS, DELETE");
        response.addHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        response.addHeader("Access-Control-Max-Age", "3600");
    }
    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        addHeaderCORS(response);
        response.setStatus(HttpServletResponse.SC_OK);
    }
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String tipo = request.getParameter("tipo");
        String data;
        if(tipo.equals("data"))
            data = """
            <lab:getColumnTable>
                <table>Departamentos</table>
            </lab:getColumnTable>
            """;
        else
            data = """
            <lab:getDataTable>
                <tabla>Departamentos</tabla>
            </lab:getDataTable>
            """;
        this.addHeaderCORS(response);
        response.setContentType("text/xml;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.write(SoapFetchStruct.fetchSOAP(data).body());
    }
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        this.addHeaderCORS(response);
        response.setContentType("text/html; charset=UTF-8");
        String contentText;
        StringBuilder body = new StringBuilder();
        BufferedReader br = request.getReader();
        while((contentText = br.readLine()) != null){
            body.append(contentText);
        }
        String tipo = request.getParameter("tipo");
        String data;
        if(tipo.equals("test")){
            data = """
            <lab:testDataTable>
                <tabla>Departamentos</tabla>
                <data>"""+body.toString()+"""
                </data>
            </lab:testDataTable>
            """;
        }
        else{
            data = """
            <lab:createDataTable>
                <tabla>Departamentos</tabla>
                <data>"""+body.toString()+"""
                </data>
            </lab:createDataTable>
            """;
        }
        PrintWriter out = response.getWriter();
        out.println(SoapFetchStruct.fetchSOAP(data).body());
    }
    @Override
    public void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
        this.addHeaderCORS(response);
        response.setContentType("text/html");
        String contentText;
        StringBuilder body = new StringBuilder();
        BufferedReader br = request.getReader();
        while((contentText = br.readLine()) != null)
            body.append(contentText);
        String data = """
            <lab:updateDataTable>
                <tabla>Departamentos</tabla>
                <data>"""+body.toString()+"""
                </data>
            </lab:updateDataTable>
        """;
        PrintWriter out = response.getWriter();
        out.write("<p>"+SoapFetchStruct.fetchSOAP(data).body()+"</p>");
    }
    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
        this.addHeaderCORS(response);
        response.setContentType("text/html");
        String contentText;
        StringBuilder body = new StringBuilder();
        BufferedReader br = request.getReader();
        while((contentText = br.readLine()) != null)
            body.append(contentText);
        String data = """
            <lab:deleteDataTable>
                <tabla>Departamentos</tabla>
                <data>"""+body.toString()+"""
                </data>
            </lab:deleteDataTable>
        """;
        PrintWriter out = response.getWriter();
        out.write(SoapFetchStruct.fetchSOAP(data).body());
    }
    @Override
    public void destroy() {
        // Aquí podrías cerrar conexiones o liberar recursos si fuera necesario
    }
}
