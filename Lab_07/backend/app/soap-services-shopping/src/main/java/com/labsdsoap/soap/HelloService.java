package com.labsdsoap.soap;

import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.jws.WebParam;

@WebService
public class HelloService {
  @WebMethod
  public String sayHello(@WebParam(name="name")String name){
    return "Hello "+name;
  }
}
