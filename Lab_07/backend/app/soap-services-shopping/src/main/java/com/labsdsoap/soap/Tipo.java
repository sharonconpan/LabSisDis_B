package com.labsdsoap.soap;

import java.util.ArrayList;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlElement;

@XmlRootElement
public class Tipo{
  private String name;
  private ArrayList<Producto>storage = new ArrayList<>();

  // Setters
  public void setName(String name){
    this.name = name;
  }
  public void setStorage(ArrayList<Producto> storage){
    this.storage = storage;
  }
  // Getters
  public String getName(){
    return this.name;
  }
  @XmlElementWrapper(name = "productos")
  @XmlElement(name = "producto")
  public ArrayList<Producto> getStorage(){
    return this.storage;
  }
  // Functions
  public String giveMeProducto(int cantidad, String code){
    Producto product = searchProducto(code);
    if(0 > product.getCantidad()-cantidad)
      return "Producto fuera de stock";
    product.setCantidad(product.getCantidad()-cantidad);
    return cantidad+" "+product.getName()+" registrado";
  }
  Producto searchProducto(String code){
    //Falta logica
    for(Producto p : this.storage)
      if(p.getCode().equals(code))
        return p;
    return null;
  }
}
