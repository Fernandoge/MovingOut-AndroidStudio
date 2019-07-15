package com.isw.movingout.Daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.isw.movingout.Objetos.Etiqueta;

import java.util.ArrayList;

public class daoEtiqueta {

    SQLiteDatabase cx;
    ArrayList<Etiqueta> lista = new ArrayList<Etiqueta>();
    Etiqueta etiqueta;
    Context contexto;
    String nombreBD = "BDEtiquetas";

    public daoEtiqueta(Context c)
    {
        this.contexto = c;
        cx = c.openOrCreateDatabase(nombreBD, Context.MODE_PRIVATE, null);
        cx.execSQL("create table if not exists etiqueta(id integer primary key autoincrement, nombre text)");

    }

    public boolean insertar(Etiqueta etiqueta)
    {
        ContentValues contenedor = new ContentValues();
        contenedor.put("nombre", etiqueta.getNombre());
        return (cx.insert("etiqueta", null, contenedor)) > 0;
    }

    public boolean eliminar(int id)
    {
        return (cx.delete("etiqueta", "id="+id,null)) > 0;
    }

    public boolean editar(Etiqueta etiqueta)
    {
        ContentValues contenedor = new ContentValues();
        contenedor.put("nombre", etiqueta.getNombre());
        return (cx.update("etiqueta", contenedor, "id="+etiqueta.getId(), null)) > 0;
    }

    public ArrayList<Etiqueta> verTodos()
    {
        lista.clear();
        Cursor cursor = cx.rawQuery("select * from etiqueta", null);
        if (cursor != null && cursor.getCount()>0){
            cursor.moveToFirst();
            do{
                lista.add(new Etiqueta(cursor.getInt(0),
                        cursor.getString(1)));

            }while (cursor.moveToNext());
        }
        return lista;
    }

    public Etiqueta verUno(int posicion)
    {
        Cursor cursor = cx.rawQuery("select * from etiqueta", null);
        cursor.moveToPosition(posicion);
        etiqueta = new Etiqueta(cursor.getInt(0),
                cursor.getString(1));
        return etiqueta;
    }

    public String[] obtenerNombreEtiquetas()
    {
        Cursor cursor = cx.rawQuery("select * from etiqueta", null);
        String[] array = new String[cursor.getCount()+1];
        array[0] = "";
        int i = 1;
        while(cursor.moveToNext()){
            String nombreEtiqueta = cursor.getString(cursor.getColumnIndex("nombre"));
            array[i] = nombreEtiqueta;
            i++;
        }

        return array;
    }

}
