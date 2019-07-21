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
import android.widget.Toast;

import com.isw.movingout.Activities.ActivityArticulosYCajas;
import com.isw.movingout.Objetos.ArticuloCuarto;
import com.isw.movingout.Objetos.Cuarto;
import com.isw.movingout.R;
import com.isw.movingout.Daos.daoCuarto;

import java.util.ArrayList;


public class AdaptadorCuartos extends BaseAdapter
{
    ArrayList<Cuarto> lista;
    daoCuarto clsDaoCuarto;
    Cuarto cuarto;
    Activity activity;
    int id = 0;


    public AdaptadorCuartos(Activity a, ArrayList<Cuarto> lista, daoCuarto dao)
    {
        this.lista = lista;
        this.activity = a;
        this.clsDaoCuarto = dao;
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
    public ArticuloCuarto getItem(int i) {
        cuarto = lista.get(i);
        return null;
    }

    @Override
    public long getItemId(int i) {
        cuarto = lista.get(i);
        return cuarto.getId();
    }

    @Override
    public View getView(int posicion, View view, ViewGroup viewGroup) {
        View v = view;
        if (v == null)
        {
            LayoutInflater li = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = li.inflate(R.layout.cuartos, null);
        }
        cuarto = lista.get(posicion);
        Button nombre = (Button)view.findViewById(R.id.buttonCuarto);
        Button editar = (Button) view.findViewById(R.id.buttonEditItem);
        Button eliminar = (Button) view.findViewById(R.id.buttonDeleteItem);

        nombre.setText(cuarto.getNombre());
        nombre.setTag(posicion);
        editar.setTag(posicion);
        eliminar.setTag(posicion);

        nombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = Integer.parseInt(view.getTag().toString());
                cuarto = lista.get(pos);
                Intent intent = new Intent(view.getContext(), ActivityArticulosYCajas.class);
                intent.putExtra("cuartoID", cuarto.getId());
                intent.putExtra("cuartoNombre", cuarto.getNombre());
                view.getContext().startActivity(intent);
            }
        });

        editar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                //Dialogo de editar dialogo.xml
                int pos = Integer.parseInt(view.getTag().toString());
                final Dialog dialogo = new Dialog(activity);
                dialogo.setTitle("Editar registro");
                dialogo.setCancelable(true);
                dialogo.setContentView(R.layout.crear_cuarto);
                dialogo.show();
                final EditText nombre = (EditText) dialogo.findViewById(R.id.inputItemNombre);
                Button guardar = (Button) dialogo.findViewById(R.id.buttonAddCuarto);
                Button cancelar = (Button) dialogo.findViewById(R.id.buttonCancelCuarto);
                guardar.setText("Editar");
                cuarto = lista.get(pos);
                setId(cuarto.getId());
                nombre.setText(cuarto.getNombre());
                guardar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            cuarto = new Cuarto(getId(), nombre.getText().toString());
                            clsDaoCuarto.editar(cuarto);
                            lista = clsDaoCuarto.verTodos();
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

        eliminar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //Dialogo confirmar si / no
                int pos = Integer.parseInt(view.getTag().toString());
                cuarto = lista.get(pos);
                setId(cuarto.getId());
                AlertDialog.Builder del = new AlertDialog.Builder(activity);
                del.setMessage("¿Estas seguro de eliminar este cuarto?");
                del.setCancelable(false);
                del.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        clsDaoCuarto.eliminar(getId());
                        lista = clsDaoCuarto.verTodos();
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
