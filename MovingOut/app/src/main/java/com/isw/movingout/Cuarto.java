package com.isw.movingout;

public class Cuarto {

    int id;
    String nombre;

    public Cuarto() {
    }

    public Cuarto(String nombre) {
        this.nombre = nombre;
    }

    public Cuarto(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
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
}
