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

import com.isw.movingout.Daos.daoArticuloCaja;
import com.isw.movingout.Objetos.ArticuloCaja;
import com.isw.movingout.Objetos.Caja;
import com.isw.movingout.R;

import java.util.ArrayList;

public class AdaptadorArticulosCaja extends BaseAdapter {

    ArrayList<ArticuloCaja> listaArticulosCaja;
    ArrayList<Caja> listaCajas;
    daoArticuloCaja clsDaoArticuloCaja;
    ArticuloCaja articuloCaja;
    Activity activity;
    int id = 0;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public AdaptadorArticulosCaja(Activity a, ArrayList<ArticuloCaja> listaArticulosCaja, daoArticuloCaja dao, ArrayList<Caja> listaCajas)
    {
        this.listaArticulosCaja = listaArticulosCaja;
        this.listaCajas = listaCajas;
        this.activity = a;
        this.clsDaoArticuloCaja = dao;
    }

    @Override
    public int getCount() {
        if (listaArticulosCaja != null)
            return listaArticulosCaja.size();
        else
            return 0;
    }

    @Override
    public ArticuloCaja getItem(int i) {
        articuloCaja = listaArticulosCaja.get(i);
        return null;
    }

    @Override
    public long getItemId(int i) {
        articuloCaja = listaArticulosCaja.get(i);
        return articuloCaja.getId();
    }

    @Override
    public View getView(int posicion, View view, ViewGroup viewGroup) {
        View v = view;
        if (v == null)
        {
            LayoutInflater li = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = li.inflate(R.layout.articulo_caja, null);
        }
        articuloCaja = listaArticulosCaja.get(posicion);
        TextView nombre = (TextView)view.findViewById(R.id.textArticuloCajaNombre);
        TextView cantidad = (TextView) view.findViewById(R.id.textArticuloCajaCantidad);
        Button editar = (Button) view.findViewById(R.id.buttonEditArticuloCaja);
        nombre.setText(articuloCaja.getNombre());
        cantidad.setText(String.valueOf(articuloCaja.getCantidad()));
        editar.setTag(posicion);

        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = Integer.parseInt(view.getTag().toString());
                final Dialog dialogo = new Dialog(activity);
                dialogo.setTitle("Editar registro");
                dialogo.setCancelable(true);
                dialogo.setContentView(R.layout.crear_articulocaja);
                dialogo.show();
                final EditText nombre = (EditText) dialogo.findViewById(R.id.inputArticuloCajaNombre);
                final EditText cantidad = (EditText) dialogo.findViewById(R.id.inputArticuloCajaCantidad);
                Button guardar = (Button) dialogo.findViewById(R.id.buttonAddArticuloCaja);
                Button cancelar = (Button) dialogo.findViewById(R.id.buttonCancelArticuloCaja);
                Button eliminar = (Button) dialogo.findViewById(R.id.buttonEliminarArticuloCaja);
                Button mover = (Button) dialogo.findViewById(R.id.buttonMoverArticuloCaja);

                final Spinner dropdownCajas = (Spinner) dialogo.findViewById(R.id.dropdownMoverArticuloCaja);
                String[] spinnerArray = new String[listaCajas.size() + 1];
                final Integer[] idCajasArray = new Integer[listaCajas.size() + 1];
                spinnerArray[0] = "";
                for(int i = 1; i<listaCajas.size()+1; i++)
                {
                    spinnerArray[i] = listaCajas.get(i-1).getNombre();
                    idCajasArray[i] = listaCajas.get(i-1).getId();
                }
                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String> (dialogo.getContext(), android.R.layout.simple_spinner_item, spinnerArray);
                dropdownCajas.setAdapter(spinnerArrayAdapter);

                guardar.setText("Editar");
                articuloCaja = listaArticulosCaja.get(pos);
                setId(articuloCaja.getId());
                nombre.setText(articuloCaja.getNombre());
                cantidad.setText(String.valueOf(articuloCaja.getCantidad()));

                guardar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            if (nombre.getText().toString().isEmpty() || cantidad.getText().toString().isEmpty()) {
                                if (nombre.getText().toString().isEmpty())
                                    nombre.setError("Este campo es obligatorio");
                                if (cantidad.getText().toString().isEmpty())
                                    cantidad.setError("Este campo es obligatorio");
                            }
                            else {
                                articuloCaja = new ArticuloCaja(getId(), nombre.getText().toString(), Integer.parseInt(cantidad.getText().toString()));
                                clsDaoArticuloCaja.editar(articuloCaja);
                                listaArticulosCaja = clsDaoArticuloCaja.verTodos();
                                notifyDataSetChanged();
                                dialogo.dismiss();
                            }
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

                eliminar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Dialogo confirmar si / no
                        AlertDialog.Builder del = new AlertDialog.Builder(activity);
                        del.setMessage("¿Estas seguro de eliminar este articulo?");
                        del.setCancelable(false);
                        del.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                clsDaoArticuloCaja.eliminar(getId());
                                listaArticulosCaja = clsDaoArticuloCaja.verTodos();
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

                mover.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            if (dropdownCajas.getSelectedItemPosition() == 0)
                                Toast.makeText(activity, "Tiene que elegir una caja primero", Toast.LENGTH_SHORT).show();
                            else{
                                articuloCaja = new ArticuloCaja(getId(), idCajasArray[dropdownCajas.getSelectedItemPosition()]);
                                clsDaoArticuloCaja.mover(articuloCaja);
                                listaArticulosCaja = clsDaoArticuloCaja.verTodos();
                                notifyDataSetChanged();
                                dialogo.dismiss();
                            }
                        } catch (Exception e) {
                            Toast.makeText(activity, "ERROR", Toast.LENGTH_SHORT).show();
                        }
                    }
                });



            }
        });

        return view;

    }

}
