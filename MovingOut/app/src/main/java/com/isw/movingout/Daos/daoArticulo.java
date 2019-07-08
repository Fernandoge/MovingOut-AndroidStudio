package com.isw.movingout.Daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.isw.movingout.Objetos.ArticuloCuarto;

import java.util.ArrayList;

public class daoArticulo {
    SQLiteDatabase cx;
    ArrayList<ArticuloCuarto> lista = new ArrayList<ArticuloCuarto>();
    ArticuloCuarto articulo;
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

    public boolean insertar(ArticuloCuarto articulo)
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
        return (cx.delete("articulo", "id="+id,null)) > 0;
    }

    public boolean editar(ArticuloCuarto a)
    {
        ContentValues contenedor = new ContentValues();
        contenedor.put("nombre", a.getNombre());
        contenedor.put("descripcion", a.getDescripcion());
        contenedor.put("etiqueta", a.getEtiqueta());
        return (cx.update("articulo", contenedor, "id="+a.getId(), null)) > 0;
    }

    public ArrayList<ArticuloCuarto> verTodos()
    {

        lista.clear();
        Cursor cursor = cx.rawQuery("select * from articulo where cuarto = ?", args);
        if (cursor != null && cursor.getCount()>0){
            cursor.moveToFirst();
            do{
                lista.add(new ArticuloCuarto(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4)));

            }while (cursor.moveToNext());
        }
        return lista;
    }

    public ArticuloCuarto verUno(int posicion)
    {
        Cursor cursor = cx.rawQuery("select * from articulo where cuarto = ?", args);
        cursor.moveToPosition(posicion);
        articulo = new ArticuloCuarto(cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4));
        return articulo;
    }
}
