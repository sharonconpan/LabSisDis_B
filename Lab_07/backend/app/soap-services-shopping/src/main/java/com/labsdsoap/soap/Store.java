package com.labsdsoap.soap

public class Store{
  Tipo[] tipos;
  
  // Functions
  String giveMeTipoProducto(Int cantidad, String code, String tipo){
    Tipo tipo = searchTipoProducto(code);
    return tipo.giveMeProducto(cantidad, code);
  }
  Tipo searchTipoProducto(String tipo){
    for(int i = 0; i < tipo.lenght(); i++)
      if(tipos[i].getName == tipo)
        return tipos[i];
  }
}
