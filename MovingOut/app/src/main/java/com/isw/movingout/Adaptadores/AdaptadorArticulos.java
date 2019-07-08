package com.isw.movingout.Adaptadores;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.isw.movingout.Objetos.ArticuloCuarto;
import com.isw.movingout.R;
import com.isw.movingout.Daos.daoArticulo;

import java.util.ArrayList;


public class AdaptadorArticulos extends BaseAdapter
{
    ArrayList<ArticuloCuarto> lista;
    daoArticulo clsDaoArticulo;
    ArticuloCuarto articulo;
    Activity activity;
    int id = 0;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public AdaptadorArticulos(Activity a, ArrayList<ArticuloCuarto> lista, daoArticulo dao)
    {
        this.lista = lista;
        this.activity = a;
        this.clsDaoArticulo = dao;
    }

    @Override
    public int getCount() {
        if (lista != null)
            return lista.size();
        else
            return 0;
    }

    @Override
    public ArticuloCuarto getItem(int i) {
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
        if (v == null)
        {
            LayoutInflater li = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = li.inflate(R.layout.articulo_cuarto, null);
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

        editar.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  int pos = Integer.parseInt(view.getTag().toString());
                  final Dialog dialogo = new Dialog(activity);
                  dialogo.setTitle("Editar registro");
                  dialogo.setCancelable(true);
                  dialogo.setContentView(R.layout.crear_articulocuarto);
                  dialogo.show();
                  final EditText nombre = (EditText) dialogo.findViewById(R.id.inputItemNombre);
                  final EditText descripcion = (EditText) dialogo.findViewById(R.id.inputItemDescripcion);
                  final EditText etiqueta = (EditText) dialogo.findViewById(R.id.inputItemEtiqueta);
                  Button guardar = (Button) dialogo.findViewById(R.id.buttonAddCuarto);
                  Button cancelar = (Button) dialogo.findViewById(R.id.buttonCancelCuarto);
                  guardar.setText("Editar");
                  articulo = lista.get(pos);
                  setId(articulo.getId());
                  nombre.setText(articulo.getNombre());
                  descripcion.setText(articulo.getDescripcion());
                  etiqueta.setText(articulo.getEtiqueta());
                  guardar.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View v) {
                          try {
                              articulo = new ArticuloCuarto(getId(), nombre.getText().toString(), descripcion.getText().toString(), etiqueta.getText().toString());
                              clsDaoArticulo.editar(articulo);
                              lista = clsDaoArticulo.verTodos();
                              notifyDataSetChanged();
                              dialogo.dismiss();
                          } catch (Exception e) {
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

              }
          });

        eliminar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //Dialogo confirmar si / no
                int pos = Integer.parseInt(view.getTag().toString());
                articulo = lista.get(pos);
                setId(articulo.getId());
                AlertDialog.Builder del = new AlertDialog.Builder(activity);
                del.setMessage("¿Estas seguro de eliminar este articulo?");
                del.setCancelable(false);
                del.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        clsDaoArticulo.eliminar(getId());
                        lista = clsDaoArticulo.verTodos();
                        notifyDataSetChanged();
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



        return view;
    }

}
