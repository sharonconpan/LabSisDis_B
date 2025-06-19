package com.ServicesMain;

import com.RESTService.App;
import com.SOAPService.SoapServe;

public class RunServices{
  public static void main(String[]args) throws Exception{
    App app = new App();
    SoapServe soap = new SoapServe();
    soap.init();
    app.init();
  }
}
