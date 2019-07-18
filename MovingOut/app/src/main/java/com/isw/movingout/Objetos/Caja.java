package com.isw.movingout.Objetos;

public class Caja {
    int id;
    String nombre;
    String descripcion;
    String estado;
    String tamanio;
    String etiqueta;
    String etiqueta2;
    int cuartoID;

    public Caja() {
    }


    public Caja(int id, String nombre, String descripcion, String estado, String tamanio, String etiqueta, String etiqueta2, int cuartoID) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.estado = estado;
        this.tamanio = tamanio;
        this.etiqueta = etiqueta;
        this.etiqueta2 = etiqueta2;
        this.cuartoID = cuartoID;
    }

    public Caja(int id, String nombre, String descripcion, String estado, String tamanio) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.estado = estado;
        this.tamanio = tamanio;
    }

    public Caja(String nombre, String descripcion, String estado, String tamanio, int cuartoID) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.estado = estado;
        this.tamanio = tamanio;
        this.cuartoID = cuartoID;
    }

    public Caja(int id, String etiqueta, String etiqueta2) {
        this.id = id;
        this.etiqueta = etiqueta;
        this.etiqueta2 = etiqueta2;
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



