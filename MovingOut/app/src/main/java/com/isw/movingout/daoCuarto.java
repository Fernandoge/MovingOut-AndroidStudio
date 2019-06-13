package com.isw.movingout;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class daoCuarto {

    SQLiteDatabase cx;
    ArrayList<Cuarto> lista = new ArrayList<Cuarto>();
    Cuarto cuarto;
    Context contexto;
    String nombreBD = "BDCuartos";


    public daoCuarto(Context c)
    {
        this.contexto = c;
        cx = c.openOrCreateDatabase(nombreBD, Context.MODE_PRIVATE, null);
        cx.execSQL("create table if not exists cuarto(id integer primary key autoincrement, nombre text)");

    }

    public boolean insertar(Cuarto c)
    {
        ContentValues contenedor = new ContentValues();
        contenedor.put("nombre", c.getNombre());
        return (cx.insert("cuarto", null, contenedor)) > 0;
    }

    public boolean eliminar(int id)
    {
        return (cx.delete("cuarto", "id="+id,null)) > 0;
    }

    public boolean editar(Cuarto c)
    {
        ContentValues contenedor = new ContentValues();
        contenedor.put("nombre", c.getNombre());
        return (cx.update("cuarto", contenedor, "id="+c.getId(), null)) > 0;
    }

    public ArrayList<Cuarto> verTodos()
    {
        lista.clear();
        Cursor cursor = cx.rawQuery("select * from cuarto", null);
        if (cursor != null && cursor.getCount()>0){
            cursor.moveToFirst();
            do{
                lista.add(new Cuarto(cursor.getInt(0),
                        cursor.getString(1)));

            }while (cursor.moveToNext());
        }
        return lista;
    }

    public Cuarto verUno(int posicion)
    {
        Cursor cursor = cx.rawQuery("select * from cuarto", null);
        cursor.moveToPosition(posicion);
        cuarto = new Cuarto(cursor.getInt(0),
                cursor.getString(1));
        return cuarto;
    }

}
