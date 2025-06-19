package com.struct;

import jakarta.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa un registro que contiene múltiples campos.
 * Compatible con JAXB para ser retornado en servicios SOAP.
 */
@XmlRootElement(name = "Registro")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Registro", propOrder = {"campos"})
public class Registro {

    @XmlElement(name = "campo")
    private List<Campo> campos = new ArrayList<>();

    public Registro() {}

    public void addCampo(Campo campo) {
        campos.add(campo);
    }

    public List<Campo> getCampos() {
        return campos;
    }

    public void setCampos(List<Campo> campos) {
        this.campos = campos;
    }
}
