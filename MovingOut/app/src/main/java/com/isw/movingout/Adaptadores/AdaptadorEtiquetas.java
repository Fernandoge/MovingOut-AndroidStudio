package com.isw.movingout.Adaptadores;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.isw.movingout.Daos.daoEtiqueta;
import com.isw.movingout.Objetos.Etiqueta;
import com.isw.movingout.R;

import java.util.ArrayList;

public class AdaptadorEtiquetas extends BaseAdapter {

    ArrayList<Etiqueta> lista;
    daoEtiqueta clsDaoEtiqueta;
    Etiqueta etiqueta;
    Activity activity;
    int id = 0;

    public AdaptadorEtiquetas(Activity a, ArrayList<Etiqueta> lista, daoEtiqueta dao)
    {
        this.lista = lista;
        this.activity = a;
        this.clsDaoEtiqueta = dao;
    }

    @Override
    public int getCount() {
        if (lista != null)
            return lista.size();
        else
            return 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public Etiqueta getItem(int i) {
        etiqueta = lista.get(i);
        return null;
    }

    @Override
    public long getItemId(int i) {
        etiqueta = lista.get(i);
        return etiqueta.getId();
    }

    @Override
    public View getView(int posicion, View view, ViewGroup viewGroup) {
        View v = view;
        if (v == null)
        {
            LayoutInflater li = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = li.inflate(R.layout.etiquetas, null);
        }
        etiqueta = lista.get(posicion);
        TextView nombre = (TextView) view.findViewById(R.id.textNombreEtiqueta);
        Button editar = (Button) view.findViewById(R.id.buttonEditEtiqueta);

        nombre.setText(etiqueta.getNombre());
        editar.setTag(posicion);

        editar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                //Dialogo de editar dialogo.xml
                int pos = Integer.parseInt(view.getTag().toString());
                final Dialog dialogo = new Dialog(activity);
                dialogo.setTitle("Editar registro");
                dialogo.setCancelable(true);
                dialogo.setContentView(R.layout.crear_etiqueta);
                dialogo.show();
                final EditText nombre = (EditText) dialogo.findViewById(R.id.inputEtiquetaNombre);
                Button guardar = (Button) dialogo.findViewById(R.id.buttonAddEtiqueta);
                Button cancelar = (Button) dialogo.findViewById(R.id.buttonCancelEtiqueta);
                Button eliminar = (Button) dialogo.findViewById(R.id.buttonEliminarEtiqueta);
                guardar.setText("Editar");
                etiqueta = lista.get(pos);
                setId(etiqueta.getId());
                nombre.setText(etiqueta.getNombre());
                guardar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            if (nombre.getText().toString().isEmpty())
                                nombre.setError("Este campo es obligatorio");
                            else {
                                etiqueta = new Etiqueta(getId(), nombre.getText().toString());
                                clsDaoEtiqueta.editar(etiqueta);
                                lista = clsDaoEtiqueta.verTodos();
                                notifyDataSetChanged();
                                dialogo.dismiss();
                            }
                        }catch (Exception e){
                            Toast.makeText(activity, "ERROR", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                cancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogo.dismiss();
                    }
                });

                eliminar.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        AlertDialog.Builder del = new AlertDialog.Builder(activity);
                        del.setMessage("¿Estas seguro de eliminar este etiqueta?");
                        del.setCancelable(false);
                        del.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                clsDaoEtiqueta.eliminar(getId());
                                lista = clsDaoEtiqueta.verTodos();
                                notifyDataSetChanged();
                                dialogo.dismiss();
                            }
                        });
                        del.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        del.show();
                    }
                });
            }
        });

        return view;
    }
}
