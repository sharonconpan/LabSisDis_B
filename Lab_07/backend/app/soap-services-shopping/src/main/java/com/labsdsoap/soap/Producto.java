package com.labsdsoap.soap;

import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(propOrder = {"code", "name", "cantidad"})
public class Producto{
  private String name;
  private int cantidad;
  private String code;
  // Setters of class features
  public void setName(String name){
    this.name = name;
  }
  public void setCantidad(int cantidad){
    this.cantidad = cantidad;
  }
  public void setCode(String code){
    this.code = code;
  }
  // Getters of class features
  public String getName(){
    return this.name;
  }
  public int getCantidad(){
    return this.cantidad;
  }
  public String getCode(){
    return this.code;
  }
}
