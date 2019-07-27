package com.isw.movingout.Daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.isw.movingout.Objetos.ArticuloCaja;

import java.util.ArrayList;

public class daoArticuloCaja
{
    SQLiteDatabase cx;
    ArrayList<ArticuloCaja> lista = new ArrayList<ArticuloCaja>();
    ArticuloCaja articuloCaja;
    Context contexto;
    int currentCaja;
    String nombreBD = "BDArticulosCaja";

    public daoArticuloCaja(Context c, int cajaID)
    {
        this.contexto = c;
        this.currentCaja = cajaID;
        cx = c.openOrCreateDatabase(nombreBD, Context.MODE_PRIVATE, null);
        cx.execSQL("create table if not exists articuloCaja(id integer primary key autoincrement, nombre text, cantidad integer, cajaid integer)");

    }

    public boolean insertar(ArticuloCaja articuloCaja)
    {
        ContentValues contenedor = new ContentValues();
        contenedor.put("nombre", articuloCaja.getNombre());
        contenedor.put("cajaid", articuloCaja.getCajaID());
        contenedor.put("cantidad", articuloCaja.getCantidad());
        return (cx.insert("articuloCaja", null, contenedor)) > 0;
    }

    public boolean eliminar(int id)
    {
        return (cx.delete("articuloCaja", "id="+id,null)) > 0;
    }

    public boolean editar(ArticuloCaja a)
    {
        ContentValues contenedor = new ContentValues();
        contenedor.put("nombre", a.getNombre());
        contenedor.put("cantidad", a.getCantidad());
        return (cx.update("articuloCaja", contenedor, "id="+a.getId(), null)) > 0;
    }

    public boolean mover(ArticuloCaja a)
    {
        ContentValues contenedor = new ContentValues();
        contenedor.put("cajaid", a.getCajaID());
        return (cx.update("articuloCaja", contenedor, "id="+a.getId(), null)) > 0;
    }

    public ArrayList<ArticuloCaja> verTodos()
    {

        lista.clear();
        Cursor cursor = cx.rawQuery("select * from articuloCaja where cajaid ="+ currentCaja, null);
        if (cursor != null && cursor.getCount()>0){
            cursor.moveToFirst();
            do{
                lista.add(new ArticuloCaja(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getInt(2),
                        cursor.getInt(3)));

            }while (cursor.moveToNext());
        }
        return lista;
    }

    public ArticuloCaja verUno(int posicion)
    {
        Cursor cursor = cx.rawQuery("select * from articuloCaja where cajaid ="+ currentCaja, null);
        cursor.moveToPosition(posicion);
        articuloCaja = (new ArticuloCaja(cursor.getInt(0),
                cursor.getString(1),
                cursor.getInt(2),
                cursor.getInt(3)));
        return articuloCaja;
    }

}
