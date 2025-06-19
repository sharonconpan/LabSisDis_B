package com.SOAPService;

import com.database.MysqlData;
import com.struct.Registro;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;

import java.util.List;
import java.util.ArrayList;

@WebService
public class StoreService{
  @WebMethod
  public ArrayList<String> getColumnTable(@WebParam(name="table") String tabla) throws Exception{
    MysqlData dtb = new MysqlData("database","EmpresaDB","root","root");
    return dtb.getNameColumn(tabla);
  }
  @WebMethod
  public List<Registro> getDataTable(@WebParam(name="tabla") String tabla) throws Exception{
    MysqlData dtb = new MysqlData("database","EmpresaDB","root","root");
    System.out.println("Reviza la consola :)");
    return dtb.getDataTabla(tabla);
  }
  @WebMethod
  public String createDataTable(@WebParam(name="tabla")String tabla,@WebParam(name="data")String data) throws Exception{
    MysqlData dtb = new MysqlData("database","EmpresaDB","root","root");
    String[] dataGroup = data.split("-");
    return dtb.createDataTabla(tabla, dataGroup);
  }
  @WebMethod
  public String updateDataTable(@WebParam(name="tabla")String tabla, @WebParam(name="data")String data) throws Exception{
    MysqlData dtb = new MysqlData("database","EmpresaDB","root","root");
    String[] dataGroup = data.split("-");
    return dtb.updateDataTabla(tabla, dataGroup);
  }
  @WebMethod
  public String deleteDataTable(@WebParam(name="tabla")String tabla, @WebParam(name="data")String data) throws Exception{
    MysqlData dtb = new MysqlData("database","EmpresaDB","root","root");
    String[] dataGroup = data.split("-");
    return dtb.deleteDataTabla(tabla, dataGroup);
  }
  @WebMethod
  public String testDataTable(@WebParam(name="tabla")String tabla, @WebParam(name="data")String data) throws Exception{
    MysqlData dtb = new MysqlData("database","EmpresaDB","root","root");
    String[] dataGroup = data.split("-");
    return dtb.testRollbackCommit(dataGroup);
  }
}
