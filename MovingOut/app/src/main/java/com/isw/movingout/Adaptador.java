package com.isw.movingout;

import android.app.Activity;
import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;


public class Adaptador extends BaseAdapter
{
    ArrayList<Articulo> lista;
    daoArticulo dao;
    Articulo articulo;
    Activity activity;

    public Adaptador(Activity a, ArrayList<Articulo> lista, daoArticulo dao)
    {
        this.lista = lista;
        this.activity = a;
        this.dao = dao;
    }

    @Override
    public int getCount() {
        if (lista != null)
            return lista.size();
        else
            return 0;
    }

    @Override
    public Articulo getItem(int i) {
        articulo = lista.get(i);
        return null;
    }

    @Override
    public long getItemId(int i) {
        articulo = lista.get(i);
        return articulo.getId();
    }

    @Override
    public View getView(int posicion, View view, ViewGroup viewGroup) {
        View v = view;
        if (v != null)
        {
            LayoutInflater li = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = li.inflate(R.layout.item_list, null);
        }
        articulo = lista.get(posicion);
        TextView nombre = (TextView)view.findViewById(R.id.textItemNombre);
        TextView descripcion = (TextView) view.findViewById(R.id.textItemDescripcion);
        TextView cuarto = (TextView) view.findViewById(R.id.textItemCuarto);
        TextView etiqueta = (TextView) view.findViewById(R.id.textItemEtiqueta);
        Button editar = (Button) view.findViewById(R.id.buttonEditItem);
        Button eliminar = (Button) view.findViewById(R.id.buttonDeleteItem);

        nombre.setText(articulo.getNombre());
        etiqueta.setText(articulo.getEtiqueta());
        cuarto.setText(articulo.getCuarto());
        descripcion.setText(articulo.getDescripcion());
        editar.setTag(posicion);
        eliminar.setTag(posicion);

        editar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //Dialogo de editar dialogo.xml
            }
        });

        eliminar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //Dialogo confirmar si / no
            }
        });



        return view;
    }

}
