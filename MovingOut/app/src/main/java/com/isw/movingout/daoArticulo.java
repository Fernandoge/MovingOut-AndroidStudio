package com.isw.movingout;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class daoArticulo {
    SQLiteDatabase cx;
    ArrayList<Articulo> lista;
    Articulo articulo;
    Context contexto;
    String nombreBD = "BDArticulos";


    public daoArticulo(Context c)
    {
        this.contexto = c;
        cx = c.openOrCreateDatabase(nombreBD, Context.MODE_PRIVATE, null);
        cx.execSQL("drop table articulo");
        cx.execSQL("create table if not exists articulo(id integer primary key autoincrement, nombre text, descripcion text, etiqueta text, cuarto text)");

    }

    public boolean insertar(Articulo articulo)
    {
        ContentValues contenedor = new ContentValues();
        contenedor.put("nombre", articulo.getNombre());
        contenedor.put("descripcion", articulo.getDescripcion());
        contenedor.put("etiqueta", articulo.getEtiqueta());
        contenedor.put("cuarto", articulo.getCuarto());
        return (cx.insert("articulo", null, contenedor)) > 0;
    }

    public boolean eliminar(int id)
    {
        return true;
    }

    public boolean editar(Articulo a)
    {
        return true;
    }

    public ArrayList<Articulo> verTodos()
    {
        return lista;
    }

    public Articulo verUno(int id)
    {
        return articulo;
    }
}
