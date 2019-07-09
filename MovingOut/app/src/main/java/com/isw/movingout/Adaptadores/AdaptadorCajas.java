package com.isw.movingout.Adaptadores;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.isw.movingout.Activities.ActivityCaja;
import com.isw.movingout.Daos.daoCaja;
import com.isw.movingout.Objetos.Caja;
import com.isw.movingout.R;

import java.util.ArrayList;

public class AdaptadorCajas extends BaseAdapter {

    ArrayList<Caja> lista;
    daoCaja clsDaoCaja;
    Caja caja;
    Activity activity;
    int id = 0;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public AdaptadorCajas(Activity activity, ArrayList<Caja> lista, daoCaja clsDaoCaja) {
        this.lista = lista;
        this.clsDaoCaja = clsDaoCaja;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        if (lista != null)
            return lista.size();
        else
            return 0;
    }

    @Override
    public Caja getItem(int i) {
        caja = lista.get(i);
        return null;
    }

    @Override
    public long getItemId(int i) {
        caja = lista.get(i);
        return caja.getId();
    }


    @Override
    public View getView(int posicion, View view, ViewGroup viewGroup)
    {
        View v = view;
        if (v == null)
        {
            LayoutInflater li = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = li.inflate(R.layout.cajas, null);
        }

        caja = lista.get(posicion);
        TextView nombre = (TextView)view.findViewById(R.id.textCajaNombre);
        TextView descripcion = (TextView)view.findViewById(R.id.textCajaDescripcion);
        TextView estado = (TextView)view.findViewById(R.id.textCajaEstado);
        TextView tamanio = (TextView)view.findViewById(R.id.textCajaTamanio);
        TextView etiqueta = (TextView)view.findViewById(R.id.textCajaEtiqueta);
        TextView cuarto = (TextView)view.findViewById(R.id.textCajaCuarto);
        Button editar = (Button) view.findViewById(R.id.buttonEditCaja);
        Button eliminar = (Button) view.findViewById(R.id.buttonDeleteCaja);

        nombre.setText(caja.getNombre());
        etiqueta.setText(caja.getEtiqueta());
        cuarto.setText(caja.getCuarto());
        descripcion.setText(caja.getDescripcion());
        tamanio.setText(caja.getTamanio());
        estado.setText(caja.getEstado());
        nombre.setTag(posicion);
        editar.setTag(posicion);
        eliminar.setTag(posicion);

        nombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = Integer.parseInt(view.getTag().toString());
                caja = lista.get(pos);
                Intent intent = new Intent(view.getContext(), ActivityCaja.class);
                intent.putExtra("caja", caja.getNombre());
                view.getContext().startActivity(intent);
            }
        });




        return view;
    }

}
