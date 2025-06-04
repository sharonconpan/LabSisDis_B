package com.labsdsoap.soap;

public class Producto{
  String name;
  Int cantidad;
  String code;
  // Setters of class features
  void setName(String name){
    this.name = name;
  }
  void setCantidad(int cantidad){
    this.cantidad = cantidad;
  }
  void setCode(String code){
    this.code = code;
  }
  // Getters of class features
  String getName(){
    return this.name;
  }
  Int getCantidad(){
    return this.cantidad;
  }
  String getCode(){
    return this.code
  }
}
