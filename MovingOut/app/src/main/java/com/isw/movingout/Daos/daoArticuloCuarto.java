package com.isw.movingout.Daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.isw.movingout.Objetos.ArticuloCuarto;

import java.util.ArrayList;

public class daoArticuloCuarto {
    SQLiteDatabase cx;
    ArrayList<ArticuloCuarto> lista = new ArrayList<ArticuloCuarto>();
    ArticuloCuarto articuloCuarto;
    Context contexto;
    int currentCuarto;
    String nombreBD = "BDArticulosCuarto";


    public daoArticuloCuarto(Context c, int cuartoID)
    {
        this.contexto = c;
        this.currentCuarto = cuartoID;
        cx = c.openOrCreateDatabase(nombreBD, Context.MODE_PRIVATE, null);
        cx.execSQL("create table if not exists articuloCuarto(id integer primary key autoincrement, nombre text, etiqueta text, cuartoid integer)");

    }

    public boolean insertar(ArticuloCuarto articuloCuarto)
    {
        ContentValues contenedor = new ContentValues();
        contenedor.put("nombre", articuloCuarto.getNombre());
        contenedor.put("cuartoid", articuloCuarto.getCuartoID());
        return (cx.insert("articuloCuarto", null, contenedor)) > 0;
    }

    public boolean eliminar(int id)
    {
        return (cx.delete("articuloCuarto", "id="+id,null)) > 0;
    }

    public boolean editar(ArticuloCuarto a)
    {
        ContentValues contenedor = new ContentValues();
        contenedor.put("nombre", a.getNombre());
        return (cx.update("articuloCuarto", contenedor, "id="+a.getId(), null)) > 0;
    }

    public boolean mover(ArticuloCuarto a)
    {
        ContentValues contenedor = new ContentValues();
        contenedor.put("cuartoid", a.getCuartoID());
        return (cx.update("articuloCuarto", contenedor, "id="+a.getId(), null)) > 0;
    }

    public boolean asignarEtiqueta(ArticuloCuarto articuloCuarto)
    {
        ContentValues contenedor = new ContentValues();
        contenedor.put("etiqueta", articuloCuarto.getEtiqueta());
        return (cx.update("articuloCuarto", contenedor, "id="+articuloCuarto.getId(), null)) > 0;
    }

    public ArrayList<ArticuloCuarto> verTodos()
    {

        lista.clear();
        Cursor cursor = cx.rawQuery("select * from articuloCuarto where cuartoid ="+ currentCuarto, null);
        if (cursor != null && cursor.getCount()>0){
            cursor.moveToFirst();
            do{
                lista.add(new ArticuloCuarto(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getInt(3)));

            }while (cursor.moveToNext());
        }
        return lista;
    }

    public ArticuloCuarto verUno(int posicion)
    {
        Cursor cursor = cx.rawQuery("select * from articuloCuarto where cuartoid ="+ currentCuarto, null);
        cursor.moveToPosition(posicion);
        articuloCuarto = new ArticuloCuarto(cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getInt(3));
        return articuloCuarto;
    }
}
