package com.isw.movingout;

public class Articulo {
    int id;
    String nombre;
    String descripcion;
    String cuarto;
    String etiqueta;

    public Articulo() {
    }

    public Articulo(String nombre, String descripcion, String cuarto, String etiqueta) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.cuarto = cuarto;
        this.etiqueta = etiqueta;
    }

    public Articulo(int id, String nombre, String descripcion, String cuarto, String etiqueta) {
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
