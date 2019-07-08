package com.isw.movingout.Objetos;

public class ArticuloCuarto {
    int id;
    String nombre;
    String descripcion;
    String cuarto;
    String etiqueta;

    public ArticuloCuarto() {
    }

    public ArticuloCuarto(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public ArticuloCuarto(String nombre, String descripcion, String cuarto, String etiqueta) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.cuarto = cuarto;
        this.etiqueta = etiqueta;
    }

    public ArticuloCuarto(int id, String nombre, String descripcion, String etiqueta) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.etiqueta = etiqueta;
    }

    public ArticuloCuarto(int id, String nombre, String descripcion, String cuarto, String etiqueta) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.cuarto = cuarto;
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

    public String getCuarto() {
        return cuarto;
    }

    public void setCuarto(String cuarto) {
        this.cuarto = cuarto;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }
}
