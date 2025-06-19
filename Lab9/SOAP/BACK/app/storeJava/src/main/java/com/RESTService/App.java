package com.RESTService;

import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.Context;

import com.SOAPWrapperREST.DepartamentosApi;
import com.SOAPWrapperREST.IngenierosApi;
import com.SOAPWrapperREST.ProyectosApi;

public class App {
    public void init() throws Exception {
        Tomcat tomcat = new Tomcat();
        tomcat.getConnector().setProperty("address", "0.0.0.0");
        tomcat.setPort(8080);

        // Crear una aplicación web básica (necesita un contexto)
        Context ctx = tomcat.addContext("", null);

        // Registrar nuestro servlet
        Tomcat.addServlet(ctx, "DepartamentosServlet", new DepartamentosApi());
        Tomcat.addServlet(ctx, "IngenierosServlet", new IngenierosApi());
        Tomcat.addServlet(ctx, "ProyectosServlet", new ProyectosApi());
        ctx.addServletMappingDecoded("/Departamentos", "DepartamentosServlet");
        ctx.addServletMappingDecoded("/Ingenieros", "IngenierosServlet");
        ctx.addServletMappingDecoded("/Proyectos", "ProyectosServlet");

        // Iniciar
        tomcat.start();
        tomcat.getServer().await();
    }
}
