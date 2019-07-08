package com.isw.movingout.Objetos;

public class Caja {
    int id;
    String nombre;
    String descripcion;
    String estado;
    String tamanio;
    String etiqueta;
    String cuarto;

    public Caja() {
    }

    public Caja(int id, String nombre, String descripcion, String estado, String tamanio, String etiqueta, String cuarto) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.estado = estado;
        this.tamanio = tamanio;
        this.etiqueta = etiqueta;
        this.cuarto = cuarto;
    }

    public Caja(String nombre, String descripcion, String estado, String tamanio, String etiqueta, String cuarto) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.estado = estado;
        this.tamanio = tamanio;
        this.etiqueta = etiqueta;
        this.cuarto = cuarto;
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

    public String getCuarto() {
        return cuarto;
    }

    public void setCuarto(String cuarto) {
        this.cuarto = cuarto;
    }
}



