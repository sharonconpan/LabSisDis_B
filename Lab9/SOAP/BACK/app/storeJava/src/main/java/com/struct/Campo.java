package com.struct;

import jakarta.xml.bind.annotation.*;

/**
 * Representa un campo genérico con nombre y valor.
 * Compatible con JAXB para uso en servicios SOAP.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Campo", propOrder = {"nombre", "valor"})
public class Campo {

    @XmlElement(required = true)
    private String nombre;

    @XmlElement
    private String valor;

    public Campo() {}

    public Campo(String nombre, Object valor) {
        this.nombre = nombre;
        this.valor = valor != null ? valor.toString() : null;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
}
