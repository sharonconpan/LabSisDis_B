package com.database;

import com.struct.Campo;
import com.struct.Registro;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import java.util.List;
import java.util.StringJoiner;
import java.util.ArrayList;
import java.lang.StringBuilder;

public class MysqlData{
  private String host;
  private String db;
  private String user;
  private String password;
  
  public MysqlData(String host, String database, String nameUser, String passwordUser){
    this.host = host;
    this.db = database;
    this.user = nameUser;
    this.password = passwordUser;
  }
  private Connection connectionDB() throws Exception{
    System.out.println("Conexion exitosa con la base de datos "+this.db);
    try{
      Connection cn = DriverManager.getConnection("jdbc:mysql://"+this.host+":3306/"+this.db, this.user, this.password);
      return cn;
    }catch(SQLException e){
      System.out.println(e);
      return null;
    }
  }

  public List<Registro> getDataTabla(String tabla) throws Exception{
    String query = "SELECT * FROM " + tabla;
    List<Registro> registros = new ArrayList<>();
    try{
      Connection cn = this.connectionDB();
      Statement st = cn.createStatement();
      ResultSet rs = st.executeQuery(query);
      ResultSetMetaData metaData = rs.getMetaData();
      int columnas = metaData.getColumnCount();
      while (rs.next()) {
        Registro registro = new Registro();
        for (int i = 1; i <= columnas; i++) {
          String nombreColumna = metaData.getColumnName(i);
          Object valor = rs.getObject(i);
          Campo campo = new Campo(nombreColumna, valor);
          registro.addCampo(campo);
        }
        registros.add(registro);
      }
    }catch (SQLException e) {
      System.out.println("Error al obtener datos: " + e.getMessage());
    }
    System.out.println(this.getNameColumn(tabla));
    System.out.println("Conexión cerrada con la base de datos " + this.db);
    return registros;
  }
  public ArrayList<String> getNameColumn(String table) throws Exception {
    ArrayList<String> columns = new ArrayList<>();

    try (Connection cn = this.connectionDB()) {
        DatabaseMetaData meta = cn.getMetaData();
        ResultSet rs = meta.getColumns(null, null, table, null);
        while (rs.next())
            columns.add(rs.getString("COLUMN_NAME"));
        rs.close();
    } catch (SQLException e) {
        System.out.println("Error al obtener las columnas de la tabla: " + e.getMessage());
        throw e; // opcionalmente relanza la excepción
    }
    return columns;
  }
  public String createDataTabla(String tabla, String[] data) throws Exception{
    StringJoiner columnas = new StringJoiner(",", "(", ")");
    int i = 0;
    for (String col : getNameColumn(tabla))
      if(i!=0)
        columnas.add(col);

    StringJoiner valores = new StringJoiner("','", "('", "')");
    for (int val = 0; val < data.length-1; val++)
        valores.add(data[val]);

    StringBuilder query = new StringBuilder("INSERT INTO ");
    query.append(tabla);
    query.append(" ");
    query.append(columnas.toString());
    query.append(" VALUES ");
    query.append(valores.toString());

    System.out.println(query.toString());

    try{
      Connection cn = this.connectionDB();
      Statement st = cn.createStatement();
      st.executeUpdate(query.toString());
      return "Registro Insertado Correctament en "+tabla;
    }catch(SQLException e){
      System.out.println("Error en la BD");
      return "No se pudo insertar el Registro en la base de datos\n"+e;
    }
  }
  public String updateDataTabla(String tabla, String[] data) throws Exception {
      try {
          ArrayList<String> columnas = getNameColumn(tabla);
          String columnaClave = columnas.get(0);  // Suponemos que la primera es la clave primaria
          String valorClave = data[0];            // Valor de esa clave (data[0])

          StringJoiner sets = new StringJoiner(", ");
          for (int i = 1; i < columnas.size(); i++) {
              sets.add(columnas.get(i) + " = ?");
          }
          String query = "UPDATE " + tabla + " SET " + sets + " WHERE " + columnaClave + " = ?";
          try (Connection cn = this.connectionDB(); PreparedStatement pst = cn.prepareStatement(query)) {
              int index = 1;
              // Insertamos los valores nuevos, desde data[1] en adelante
              for (int i = 1; i < data.length-1; i++) {
                  pst.setString(index++, data[i]);
              }
              // Valor de la clave para el WHERE
              pst.setString(index, valorClave);
              int filas = pst.executeUpdate();
              return filas + " fila(s) actualizada(s) correctamente.";
          }
      } catch (Exception e) {
          return "Error al actualizar datos: " + e.getMessage();
      }
  }
  public String deleteDataTabla(String tabla, String[] data) throws Exception{
    String query = "DELETE FROM " + tabla + " WHERE " + getNameColumn(tabla).get(0)+"='"+data[0]+"'";
    try (Connection cn = this.connectionDB(); Statement st = cn.createStatement()) {
      int filas = st.executeUpdate(query);
      return filas + " fila(s) eliminada(s) correctamente.";
    } catch (SQLException e) {
      return "Error al eliminar datos: " + e.getMessage();
    }
  }
  public String testRollbackCommit(String[] query){
    try(Connection cn = this.connectionDB()){
      cn.setAutoCommit(false);
      try(Statement st = cn.createStatement()){
        for (int i = 0; i < query.length-1; i++){
          st.executeUpdate(query[i]);
          System.out.println(query[i]);
        }
        cn.commit();
        return "Test ejecutada correctamente";
      }catch(SQLException e){
        cn.rollback();
        return "Error de carga de datos: " + e.getMessage();
      }
    }catch(Exception e){
      return "Error de prueba: " + e.getMessage();
    }
  }
}

