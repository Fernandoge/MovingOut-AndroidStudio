package com.isw.movingout.Daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.isw.movingout.Objetos.Caja;

import java.util.ArrayList;

public class daoCaja {

    SQLiteDatabase cx;
    ArrayList<Caja> lista = new ArrayList<Caja>();
    Caja caja;
    Context contexto;
    int currentCuarto;
    String nombreBD = "BDCajas";

    public daoCaja(Context c, int cuartoID)
    {
        this.contexto = c;
        this.currentCuarto = cuartoID;
        cx = c.openOrCreateDatabase(nombreBD, Context.MODE_PRIVATE, null);
        cx.execSQL("create table if not exists caja (id integer primary key autoincrement, nombre text, descripcion text, estado text, tamanio text, etiqueta text, cuartoid integer)");
    }


    public boolean insertar(Caja caja)
    {
        ContentValues contenedor = new ContentValues();
        contenedor.put("nombre", caja.getNombre());
        contenedor.put("descripcion", caja.getDescripcion());;
        contenedor.put("tamanio", caja.getTamanio());
        contenedor.put("cuartoid", caja.getCuartoID());
        return (cx.insert("caja", null, contenedor)) > 0;

    }

    public boolean eliminar(int id)
    {
        return (cx.delete("caja", "id="+id,null)) > 0;
    }

    public boolean editar(Caja caja)
    {
        ContentValues contenedor = new ContentValues();
        contenedor.put("nombre", caja.getNombre());
        contenedor.put("descripcion", caja.getDescripcion());
        contenedor.put("tamanio", caja.getTamanio());
        return (cx.update("caja", contenedor, "id="+caja.getId(), null)) > 0;
    }

    public boolean checkEstado(Caja caja)
    {
        ContentValues contenedor = new ContentValues();
        contenedor.put("estado", caja.getEstado());
        return (cx.update("caja", contenedor, "id="+caja.getId(), null)) > 0;
    }

    public boolean asignarEtiqueta(Caja caja)
    {
        ContentValues contenedor = new ContentValues();
        contenedor.put("etiqueta", caja.getEtiqueta());
        return (cx.update("caja", contenedor, "id="+caja.getId(), null)) > 0;
    }

    public ArrayList<Caja> verTodos()
    {
        lista.clear();
        Cursor cursor = cx.rawQuery("select * from caja where cuartoid ="+ currentCuarto, null);
        if (cursor != null && cursor.getCount()>0){
            cursor.moveToFirst();
            do{
                lista.add(new Caja(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getInt(6)));

            }while (cursor.moveToNext());
        }
        return lista;
    }

    public Caja verUno(int posicion)
    {
        Cursor cursor = cx.rawQuery("select * from caja where cuartoid ="+ currentCuarto, null);
        cursor.moveToPosition(posicion);
        caja = new Caja(cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5),
                cursor.getInt(6));
        return caja;
    }
}
