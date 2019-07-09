package com.isw.movingout.Objetos;

public class Caja {
    int id;
    String nombre;
    String descripcion;
    String estado;
    String tamanio;
    String etiqueta;
    String cuartoNombre;
    int cuartoID;

    public Caja() {
    }

    public Caja(int id, String nombre, String descripcion, String estado, String tamanio, String etiqueta, String cuartoNombre, int cuartoID) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.estado = estado;
        this.tamanio = tamanio;
        this.etiqueta = etiqueta;
        this.cuartoNombre = cuartoNombre;
        this.cuartoID = cuartoID;
    }

    public Caja(String nombre, String descripcion, String estado, String tamanio, String etiqueta, String cuartoNombre, int cuartoID) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.estado = estado;
        this.tamanio = tamanio;
        this.etiqueta = etiqueta;
        this.cuartoNombre = cuartoNombre;
        this.cuartoID = cuartoID;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTamanio() {
        return tamanio;
    }

    public void setTamanio(String tamanio) {
        this.tamanio = tamanio;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public String getCuartoNombre() {
        return cuartoNombre;
    }

    public void setCuartoNombre(String cuartoNombre) {
        this.cuartoNombre = cuartoNombre;
    }

    public int getCuartoID() {
        return cuartoID;
    }

    public void setCuartoID(int cuartoID) {
        this.cuartoID = cuartoID;
    }
}



