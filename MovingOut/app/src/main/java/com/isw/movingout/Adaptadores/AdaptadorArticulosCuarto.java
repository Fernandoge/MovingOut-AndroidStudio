package com.isw.movingout.Adaptadores;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.isw.movingout.Daos.daoEtiqueta;
import com.isw.movingout.Objetos.ArticuloCuarto;
import com.isw.movingout.R;
import com.isw.movingout.Daos.daoArticuloCuarto;

import java.util.ArrayList;


public class AdaptadorArticulosCuarto extends BaseAdapter
{
    ArrayList<ArticuloCuarto> lista;
    daoArticuloCuarto clsDaoArticuloCuarto;
    daoEtiqueta clsDaoEtiqueta;
    ArticuloCuarto articulo;
    Activity activity;
    int id = 0;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public AdaptadorArticulosCuarto(Activity a, ArrayList<ArticuloCuarto> lista, daoArticuloCuarto daoArticuloCuarto, daoEtiqueta daoEtiqueta)
    {
        this.lista = lista;
        this.activity = a;
        this.clsDaoArticuloCuarto = daoArticuloCuarto;
        this.clsDaoEtiqueta = daoEtiqueta;
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
        Button etiqueta = (Button)view.findViewById(R.id.textItemEtiqueta);
        Button editar = (Button) view.findViewById(R.id.buttonEditItem);
        Button eliminar = (Button) view.findViewById(R.id.buttonDeleteItem);

        nombre.setText(articulo.getNombre());
        etiqueta.setText(articulo.getEtiqueta());
        etiqueta.setTag(posicion);
        editar.setTag(posicion);
        eliminar.setTag(posicion);

        etiqueta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = Integer.parseInt(view.getTag().toString());
                final Dialog dialogo = new Dialog(activity);
                dialogo.setTitle("Asignar etiqueta 1 articuloCuarto");
                dialogo.setCancelable(true);
                dialogo.setContentView(R.layout.asignar_etiqueta);
                dialogo.show();

                final Spinner dropdownEtiqueta = (Spinner) dialogo.findViewById(R.id.dropdownAssignEtiqueta);
                String[] spinnerArray = clsDaoEtiqueta.obtenerNombreEtiquetas();
                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String> (dialogo.getContext(), android.R.layout.simple_spinner_item, spinnerArray);
                dropdownEtiqueta.setAdapter(spinnerArrayAdapter);

                Button asignar = (Button) dialogo.findViewById(R.id.buttonAssignEtiqueta);
                Button cancelar = (Button) dialogo.findViewById(R.id.buttonCancelAssignEtiqueta);

                articulo = lista.get(pos);
                setId(articulo.getId());

                asignar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            articulo = new ArticuloCuarto(getId(), dropdownEtiqueta.getSelectedItem().toString(), null);
                            clsDaoArticuloCuarto.asignarEtiqueta(articulo);
                            lista = clsDaoArticuloCuarto.verTodos();
                            notifyDataSetChanged();
                            dialogo.dismiss();
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
            }
        });

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
                  Button guardar = (Button) dialogo.findViewById(R.id.buttonAddCuarto);
                  Button cancelar = (Button) dialogo.findViewById(R.id.buttonCancelCuarto);
                  guardar.setText("Editar");
                  articulo = lista.get(pos);
                  setId(articulo.getId());
                  nombre.setText(articulo.getNombre());
                  guardar.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View v) {
                          try {
                              articulo = new ArticuloCuarto(getId(), nombre.getText().toString());
                              clsDaoArticuloCuarto.editar(articulo);
                              lista = clsDaoArticuloCuarto.verTodos();
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
                        clsDaoArticuloCuarto.eliminar(getId());
                        lista = clsDaoArticuloCuarto.verTodos();
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
