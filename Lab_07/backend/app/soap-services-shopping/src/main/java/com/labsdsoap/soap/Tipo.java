package com.labsdsoap.soap;

public class Tipo{
  String name;
  Producto[]storage;

  // Setters
  void setName(String name){
    this.name = name;
  }
  void setStorage(Producto[] storage){
    this.storage = storage;
  }
  // Getters
  String getName(){
    return this.name;
  }
  Producto[]storage getStorage(){
    return this.storage;
  }
  // Functions
  String giveMeProducto(Int cantidad, String code){
    Producto product = searchProducto(code);
    if(0 > product.cantidad-cantidad)
      return "Producto fuera de stock";
    producto.setCantidad(producto.getCantidad()-cantidad);
    return cantidad+" "+producto.getName()+" registrado";
  }
  Producto searchProducto(String code){
    //Falta logica
  }
}
