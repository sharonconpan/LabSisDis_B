package com.labsdsoap.soap;

import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.jws.WebParam;

import java.util.ArrayList;

@WebService
public class StoreService{

  private ArrayList<Tipo>tipos = new ArrayList<>();

  public StoreService(){}

  @WebMethod
  public String addTipo(@WebParam(name = "tipo") Tipo tipo) {
    this.tipos.add(tipo);
    return "categoria "+tipo.getName()+" agregada";
  }

  @WebMethod
  public String addProducto(@WebParam(name = "nameTipo") String nameTipo, @WebParam(name = "prod") Producto prod){
    this.searchTipoProducto(nameTipo).getStorage().add(prod);
    return "Producto "+prod.getName()+" en la categoria "+nameTipo;
  }

  @WebMethod
  public ArrayList<Producto> getMeAllProductos(){
    ArrayList<Producto>stockProduct = new ArrayList<>();
    for(Tipo t : this.tipos)
      stockProduct.addAll(t.getStorage());
    return stockProduct;
  }
  
  // Functions
  public String giveMeTipoProducto(int cantidad, String code, String tipoName){
    Tipo tipo = searchTipoProducto(tipoName);
    return tipo.giveMeProducto(cantidad, code);
  }
  public Tipo searchTipoProducto(String tipo){
    for(Tipo t : this.tipos)
      if(t.getName().equals(tipo))
        return t;
    return null;
  }
}

