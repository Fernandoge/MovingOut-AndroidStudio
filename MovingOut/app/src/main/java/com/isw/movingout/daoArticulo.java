package com.isw.movingout;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class daoArticulo {
    SQLiteDatabase cx;
    ArrayList<Articulo> lista = new ArrayList<Articulo>();
    Articulo articulo;
    Context contexto;
    String currentCuarto;
    String nombreBD = "BDArticulos";
    String[] args;


    public daoArticulo(Context c, String cuarto)
    {
        this.contexto = c;
        this.currentCuarto = cuarto;
        args = new String[] {currentCuarto};
        cx = c.openOrCreateDatabase(nombreBD, Context.MODE_PRIVATE, null);
        cx.execSQL("create table if not exists articulo(id integer primary key autoincrement, nombre text, descripcion text, etiqueta text, cuarto text)");

    }

    public boolean insertar(Articulo articulo)
    {
        ContentValues contenedor = new ContentValues();
        contenedor.put("nombre", articulo.getNombre());
        contenedor.put("descripcion", articulo.getDescripcion());
        contenedor.put("etiqueta", articulo.getEtiqueta());
        contenedor.put("cuarto", currentCuarto);
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

        lista.clear();
        Cursor cursor = cx.rawQuery("select * from articulo where cuarto = ?", args);
        if (cursor != null && cursor.getCount()>0){
            cursor.moveToFirst();
            do{
                lista.add(new Articulo(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4)));

            }while (cursor.moveToNext());
        }
        return lista;
    }

    public Articulo verUno(int posicion)
    {
        Cursor cursor = cx.rawQuery("select * from articulo where cuarto = ?", args);
        cursor.moveToPosition(posicion);
        articulo = new Articulo(cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4));
        return articulo;
    }
}
