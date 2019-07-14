package com.isw.movingout.Objetos;

public class ArticuloCaja {
    int id;
    String nombre;
    int cajaID;

    public ArticuloCaja() {
    }

    public ArticuloCaja(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public ArticuloCaja(String nombre, int cajaID) {
        this.nombre = nombre;
        this.cajaID = cajaID;
    }

    public ArticuloCaja(int id, String nombre, int cajaID) {
        this.id = id;
        this.nombre = nombre;
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

    public int getCajaID() {
        return cajaID;
    }

    public void setCajaID(int cajaID) {
        this.cajaID = cajaID;
    }
}

