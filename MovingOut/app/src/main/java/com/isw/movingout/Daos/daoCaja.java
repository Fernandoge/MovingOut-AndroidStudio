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
    String currentCuarto;
    String nombreBD = "BDCajas";
    String[] args;

    public daoCaja(Context c, String cuarto)
    {
        this.contexto = c;
        this.currentCuarto = cuarto;
        args = new String[] {currentCuarto};
        cx = c.openOrCreateDatabase(nombreBD, Context.MODE_PRIVATE, null);
        cx.execSQL("create table if not exists caja (id integer primary key autoincrement, nombre text, descripcion text, estado text, tamanio text, etiqueta text, cuarto text)");
    }


    public boolean insertar(Caja caja)
    {
        ContentValues contenedor = new ContentValues();
        contenedor.put("nombre", caja.getNombre());
        contenedor.put("descripcion", caja.getDescripcion());
        contenedor.put("estado", caja.getEstado());
        contenedor.put("tamanio", caja.getTamanio());
        contenedor.put("etiqueta", caja.getEtiqueta());
        contenedor.put("cuarto", caja.getCuarto());
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
        contenedor.put("estado", caja.getEstado());
        contenedor.put("tamanio", caja.getTamanio());
        contenedor.put("etiqueta", caja.getEtiqueta());
        return (cx.update("caja", contenedor, "id="+caja.getId(), null)) > 0;
    }

    public ArrayList<Caja> verTodos()
    {
        lista.clear();
        Cursor cursor = cx.rawQuery("select * from caja where cuarto = ?", args);
        if (cursor != null && cursor.getCount()>0){
            cursor.moveToFirst();
            do{
                lista.add(new Caja(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6)));

            }while (cursor.moveToNext());
        }
        return lista;
    }

    public Caja verUno(int posicion)
    {
        Cursor cursor = cx.rawQuery("select * from caja where cuarto = ?", args);
        cursor.moveToPosition(posicion);
        caja = new Caja(cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5),
                cursor.getString(6));
        return caja;
    }
}
