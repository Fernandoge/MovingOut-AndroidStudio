package com.isw.movingout.Objetos;

public class ArticuloCuarto {
    int id;
    String nombre;
    String etiqueta;
    String etiqueta2;
    int cuartoID;

    public ArticuloCuarto() {
    }

    public ArticuloCuarto(int id, String nombre, String etiqueta, int cuartoID) {
        this.id = id;
        this.nombre = nombre;
        this.etiqueta = etiqueta;
        this.etiqueta2 = etiqueta2;
        this.cuartoID = cuartoID;
    }

    public ArticuloCuarto(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public ArticuloCuarto(String nombre, int cuartoID) {
        this.nombre = nombre;
        this.cuartoID = cuartoID;
    }

    public ArticuloCuarto(int id, String etiqueta, String etiqueta2) {
        this.id = id;
        this.etiqueta = etiqueta;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public String getEtiqueta2() {
        return etiqueta2;
    }

    public void setEtiqueta2(String etiqueta2) {
        this.etiqueta2 = etiqueta2;
    }

    public int getCuartoID() {
        return cuartoID;
    }

    public void setCuartoID(int cuartoID) {
        this.cuartoID = cuartoID;
    }
}
