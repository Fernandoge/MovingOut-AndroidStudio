package com.isw.movingout.Objetos;

public class ArticuloCaja {
    int id;
    String nombre;
    int cantidad;
    int cajaID;

    public ArticuloCaja() {
    }

    public ArticuloCaja(int id, String nombre, int cantidad, int cajaID) {
        this.id = id;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.cajaID = cajaID;
    }

    public ArticuloCaja(String nombre, int cantidad, int cajaID) {
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.cajaID = cajaID;
    }

    public ArticuloCaja(int id, String nombre, int cantidad) {
        this.id = id;
        this.nombre = nombre;
        this.cantidad = cantidad;
    }

    public ArticuloCaja(int id, int cajaID) {
        this.id = id;
        this.cajaID = cajaID;
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

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getCajaID() {
        return cajaID;
    }

    public void setCajaID(int cajaID) {
        this.cajaID = cajaID;
    }
}

