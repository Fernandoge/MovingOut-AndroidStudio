package com.isw.movingout.Objetos;

public class ArticuloCuarto {
    int id;
    String nombre;
    String descripcion;
    String etiqueta;
    int cuartoID;

    public ArticuloCuarto() {
    }

    public ArticuloCuarto(int id, String nombre, String descripcion, String etiqueta, int cuartoID) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.etiqueta = etiqueta;
        this.cuartoID = cuartoID;
    }

    public ArticuloCuarto(String nombre, String descripcion, String etiqueta, int cuartoID) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.etiqueta = etiqueta;
        this.cuartoID = cuartoID;
    }

    public ArticuloCuarto(int id, String nombre, String descripcion, String etiqueta) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public int getCuartoID() {
        return cuartoID;
    }

    public void setCuartoID(int cuartoID) {
        this.cuartoID = cuartoID;
    }
}
