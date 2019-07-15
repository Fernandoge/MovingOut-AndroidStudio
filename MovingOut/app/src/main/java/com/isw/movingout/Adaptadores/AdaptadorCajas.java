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
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.isw.movingout.Activities.ActivityArticulosCaja;
import com.isw.movingout.Daos.daoCaja;
import com.isw.movingout.Daos.daoEtiqueta;
import com.isw.movingout.Objetos.Caja;
import com.isw.movingout.R;

import java.util.ArrayList;

public class AdaptadorCajas extends BaseAdapter {

    ArrayList<Caja> lista;
    daoCaja clsDaoCaja;
    daoEtiqueta clsDaoEtiqueta;
    Caja caja;
    Activity activity;
    int id = 0;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public AdaptadorCajas(Activity activity, ArrayList<Caja> lista, daoCaja clsDaoCaja, daoEtiqueta clsDaoEtiqueta) {
        this.lista = lista;
        this.clsDaoCaja = clsDaoCaja;
        this.clsDaoEtiqueta = clsDaoEtiqueta;
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
        Button editar = (Button) view.findViewById(R.id.buttonEditCaja);
        Button eliminar = (Button) view.findViewById(R.id.buttonDeleteCaja);

        nombre.setText(caja.getNombre());
        etiqueta.setText(caja.getEtiqueta());
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
                Intent intent = new Intent(view.getContext(), ActivityArticulosCaja.class);
                intent.putExtra("cajaID", caja.getNombre());
                view.getContext().startActivity(intent);
            }
        });

        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = Integer.parseInt(view.getTag().toString());
                final Dialog dialogo = new Dialog(activity);
                dialogo.setTitle("Editar registro");
                dialogo.setCancelable(true);
                dialogo.setContentView(R.layout.crear_caja);
                dialogo.show();
                final EditText nombre = (EditText) dialogo.findViewById(R.id.inputCajaNombre);
                final EditText descripcion = (EditText) dialogo.findViewById(R.id.inputCajaDescripcion);
                final EditText estado = (EditText) dialogo.findViewById(R.id.inputCajaEstado);
                final EditText tamanio = (EditText) dialogo.findViewById(R.id.inputCajaTamanio);
                final Spinner dropdownEtiqueta = (Spinner) dialogo.findViewById(R.id.dropdownCajaEtiqueta);
                String[] spinnerArray = clsDaoEtiqueta.obtenerNombreEtiquetas();
                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String> (dialogo.getContext(), android.R.layout.simple_spinner_item, spinnerArray);
                dropdownEtiqueta.setAdapter(spinnerArrayAdapter);
                Button guardar = (Button) dialogo.findViewById(R.id.buttonAddCaja);
                Button cancelar = (Button) dialogo.findViewById(R.id.buttonCancelCaja);
                guardar.setText("Editar");
                caja = lista.get(pos);
                setId(caja.getId());
                nombre.setText(caja.getNombre());
                descripcion.setText(caja.getDescripcion());
                estado.setText(caja.getEstado());
                tamanio.setText(caja.getTamanio());
                guardar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            caja = new Caja(getId(), nombre.getText().toString(),
                                    descripcion.getText().toString(),
                                    estado.getText().toString(),
                                    tamanio.getText().toString(),
                                    dropdownEtiqueta.getSelectedItem().toString());
                            clsDaoCaja.editar(caja);
                            lista = clsDaoCaja.verTodos();
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
                caja = lista.get(pos);
                setId(caja.getId());
                AlertDialog.Builder del = new AlertDialog.Builder(activity);
                del.setMessage("¿Estas seguro de eliminar esta caja?");
                del.setCancelable(false);
                del.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        clsDaoCaja.eliminar(getId());
                        lista = clsDaoCaja.verTodos();
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
