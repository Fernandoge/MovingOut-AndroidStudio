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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.isw.movingout.Activities.ActivityArticulosCaja;
import com.isw.movingout.Daos.daoArticuloCaja;
import com.isw.movingout.Daos.daoCaja;
import com.isw.movingout.Daos.daoEtiqueta;
import com.isw.movingout.Objetos.ArticuloCaja;
import com.isw.movingout.Objetos.Caja;
import com.isw.movingout.Objetos.Cuarto;
import com.isw.movingout.R;

import java.util.ArrayList;

public class AdaptadorCajas extends BaseAdapter {

    ArrayList<Caja> listaCajas;
    ArrayList<Caja> listaTotalCajas;
    ArrayList<Cuarto> listaCuartos;
    ArrayList<ArticuloCaja> listaArticulosCaja;
    daoArticuloCaja clsDaoArticuloCaja;
    daoCaja clsDaoCaja;
    daoEtiqueta clsDaoEtiqueta;
    Caja caja;
    ArticuloCaja articuloCaja;
    Activity activity;
    int id = 0;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public AdaptadorCajas(Activity activity, ArrayList<Caja> listaCajas, daoCaja clsDaoCaja, daoEtiqueta clsDaoEtiqueta, ArrayList<Cuarto> listaCuartos) {
        this.listaCajas = listaCajas;
        this.clsDaoCaja = clsDaoCaja;
        this.clsDaoEtiqueta = clsDaoEtiqueta;
        this.activity = activity;
        this.listaCuartos = listaCuartos;
    }

    @Override
    public int getCount() {
        if (listaCajas != null)
            return listaCajas.size();
        else
            return 0;
    }

    @Override
    public Caja getItem(int i) {
        caja = listaCajas.get(i);
        return null;
    }

    @Override
    public long getItemId(int i) {
        caja = listaCajas.get(i);
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

        caja = listaCajas.get(posicion);
        TextView nombre = (TextView)view.findViewById(R.id.textCajaNombre);
        TextView descripcion = (TextView)view.findViewById(R.id.textCajaDescripcion);
        TextView tamanio = (TextView)view.findViewById(R.id.textCajaTamanio);
        Button etiqueta = (Button) view.findViewById(R.id.textCajaEtiqueta);
        Button editar = (Button) view.findViewById(R.id.buttonEditCaja);
        final CheckBox checkboxEstado = (CheckBox) view.findViewById(R.id.checkBoxEstado);

        nombre.setText(caja.getNombre());
        etiqueta.setText(caja.getEtiqueta());
        descripcion.setText(caja.getDescripcion());
        tamanio.setText(caja.getTamanio());
        nombre.setTag(posicion);
        checkboxEstado.setTag(posicion);
        etiqueta.setTag(posicion);
        editar.setTag(posicion);

        if (caja.getEstado() != null && caja.getEstado().equals("Embalada"))
            checkboxEstado.setChecked(true);
        else
            checkboxEstado.setChecked(false);

        nombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = Integer.parseInt(view.getTag().toString());
                caja = listaCajas.get(pos);
                Intent intent = new Intent(view.getContext(), ActivityArticulosCaja.class);
                intent.putExtra("cajaID", caja.getId());
                intent.putExtra("cajaNombre", caja.getNombre());
                view.getContext().startActivity(intent);
            }
        });

        checkboxEstado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = Integer.parseInt(view.getTag().toString());
                caja = listaCajas.get(pos);
                setId(caja.getId());

                if (checkboxEstado.isChecked())
                {
                    try {
                        caja = new Caja(getId(), "Embalada");
                        clsDaoCaja.checkEstado(caja);
                        listaCajas = clsDaoCaja.verTodos();
                        notifyDataSetChanged();
                    } catch (Exception e) {
                        Toast.makeText(activity, "ERROR", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    try {
                        caja = new Caja(getId(), "Desembalada");
                        clsDaoCaja.checkEstado(caja);
                        listaCajas = clsDaoCaja.verTodos();
                        notifyDataSetChanged();
                    } catch (Exception e) {
                        Toast.makeText(activity, "ERROR", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        etiqueta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = Integer.parseInt(view.getTag().toString());
                final Dialog dialogo = new Dialog(activity);
                dialogo.setTitle("Asignar etiqueta 1 caja");
                dialogo.setCancelable(true);
                dialogo.setContentView(R.layout.asignar_etiqueta);
                dialogo.show();

                final Spinner dropdownEtiqueta = (Spinner) dialogo.findViewById(R.id.dropdownAssignEtiqueta);
                String[] spinnerArray = clsDaoEtiqueta.obtenerNombreEtiquetas();
                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String> (dialogo.getContext(), android.R.layout.simple_spinner_item, spinnerArray);
                dropdownEtiqueta.setAdapter(spinnerArrayAdapter);

                Button asignar = (Button) dialogo.findViewById(R.id.buttonAssignEtiqueta);
                Button cancelar = (Button) dialogo.findViewById(R.id.buttonCancelAssignEtiqueta);

                caja = listaCajas.get(pos);
                setId(caja.getId());

                asignar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            caja = new Caja(getId(), dropdownEtiqueta.getSelectedItem().toString(), null);
                            clsDaoCaja.asignarEtiqueta(caja);
                            listaCajas = clsDaoCaja.verTodos();
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
                dialogo.setContentView(R.layout.crear_caja);
                dialogo.show();
                final EditText nombre = (EditText) dialogo.findViewById(R.id.inputCajaNombre);
                final EditText descripcion = (EditText) dialogo.findViewById(R.id.inputCajaDescripcion);

                //Spinner Tamanio
                final Spinner dropdownTamanio = (Spinner) dialogo.findViewById(R.id.dropdownCajaTamanio);
                String[] spinnerTamanioArray = {"", "Pequeña", "Mediana", "Grande", "Muy Grande"};
                ArrayAdapter<String> spinnerTamanioArrayAdapter = new ArrayAdapter<String> (dialogo.getContext(), android.R.layout.simple_spinner_item, spinnerTamanioArray);
                dropdownTamanio.setAdapter(spinnerTamanioArrayAdapter);

                Button guardar = (Button) dialogo.findViewById(R.id.buttonAddCaja);
                Button cancelar = (Button) dialogo.findViewById(R.id.buttonCancelCaja);
                Button eliminar = (Button) dialogo.findViewById(R.id.buttonEliminarCaja);
                Button mover = (Button) dialogo.findViewById(R.id.buttonMoverCaja);

                final Spinner dropdownCuartos = (Spinner) dialogo.findViewById(R.id.dropdownMoverCaja);
                String[] spinnerArray = new String[listaCuartos.size() + 1];
                final Integer[] idCuartosArray = new Integer[listaCuartos.size() + 1];
                spinnerArray[0] = "";
                for(int i = 1; i<listaCuartos.size()+1; i++)
                {
                    spinnerArray[i] = listaCuartos.get(i-1).getNombre();
                    idCuartosArray[i] = listaCuartos.get(i-1).getId();
                }
                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String> (dialogo.getContext(), android.R.layout.simple_spinner_item, spinnerArray);
                dropdownCuartos.setAdapter(spinnerArrayAdapter);

                guardar.setText("Editar");
                caja = listaCajas.get(pos);
                setId(caja.getId());
                nombre.setText(caja.getNombre());
                descripcion.setText(caja.getDescripcion());
                dropdownTamanio.setSelection(spinnerTamanioArrayAdapter.getPosition(caja.getTamanio()));
                guardar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            if (nombre.getText().toString().isEmpty() || descripcion.getText().toString().isEmpty() || dropdownTamanio.getSelectedItemPosition() == 0) {
                                if (nombre.getText().toString().isEmpty())
                                    nombre.setError("Este campo es obligatorio");
                                if (descripcion.getText().toString().isEmpty())
                                    descripcion.setError("Este campo es obligatorio");
                                if (dropdownTamanio.getSelectedItemPosition() == 0)
                                    Toast.makeText(activity, "Seleccione un tamaño para la caja", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                caja = new Caja(getId(), nombre.getText().toString(),
                                        descripcion.getText().toString(),
                                        dropdownTamanio.getSelectedItem().toString());
                                clsDaoCaja.editar(caja);
                                listaCajas = clsDaoCaja.verTodos();
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
                        final Dialog dialogoEliminar = new Dialog(activity);
                        dialogoEliminar.setTitle("Eliminar Caja");
                        dialogoEliminar.setCancelable(true);
                        dialogoEliminar.setContentView(R.layout.eliminar_caja);
                        dialogoEliminar.show();
                        dialogo.dismiss();
                        listaTotalCajas = clsDaoCaja.obtenerCajas(getId());

                        Button moverYEliminar = (Button) dialogoEliminar.findViewById(R.id.buttonMoveryEliminar);
                        Button eliminarTodo = (Button) dialogoEliminar.findViewById(R.id.buttonEliminarTodo);
                        Button cancelarEliminarCaja = (Button) dialogoEliminar.findViewById(R.id.buttonCancelarEliminarCaja);
                        //Spinner Cajas
                        final Spinner dropdownListaCajas = (Spinner) dialogoEliminar.findViewById(R.id.dropdownListaCajas);
                        String[] spinnerArrayCajas = new String[listaTotalCajas.size() + 1];
                        final Integer[] idCajasArray = new Integer[listaTotalCajas.size() + 1];
                        spinnerArrayCajas[0] = "";
                        for(int i = 1; i<listaTotalCajas.size()+1; i++)
                        {
                            spinnerArrayCajas[i] = listaTotalCajas.get(i-1).getNombre();
                            idCajasArray[i] = listaTotalCajas.get(i-1).getId();
                        }
                        ArrayAdapter<String> spinnerListaCajasArrayAdapter = new ArrayAdapter<String> (dialogoEliminar.getContext(), android.R.layout.simple_spinner_item, spinnerArrayCajas);
                        dropdownListaCajas.setAdapter(spinnerListaCajasArrayAdapter);

                        moverYEliminar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AlertDialog.Builder del = new AlertDialog.Builder(activity);
                                del.setMessage("¿Estas seguro de eliminar esta caja y mover sus articulos a la caja seleccionada?");
                                del.setCancelable(false);
                                del.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        try {
                                            if (dropdownListaCajas.getSelectedItemPosition() == 0)
                                                Toast.makeText(activity, "Tiene que elegir una caja primero", Toast.LENGTH_SHORT).show();
                                            else {
                                                clsDaoArticuloCaja = new daoArticuloCaja(activity, getId());
                                                listaArticulosCaja = clsDaoArticuloCaja.verTodos();
                                                for (int i = 0; i < listaArticulosCaja.size(); i++) {
                                                    articuloCaja = new ArticuloCaja(listaArticulosCaja.get(i).getId(), idCajasArray[dropdownListaCajas.getSelectedItemPosition()]);
                                                    clsDaoArticuloCaja.mover(articuloCaja);
                                                }
                                                clsDaoCaja.eliminar(getId());
                                                listaCajas = clsDaoCaja.verTodos();
                                                notifyDataSetChanged();
                                                dialogoEliminar.dismiss();
                                                dialogo.dismiss();
                                            }
                                        } catch (Exception e) {
                                            Toast.makeText(activity, "ERROR", Toast.LENGTH_SHORT).show();
                                        }
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

                        eliminarTodo.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AlertDialog.Builder del = new AlertDialog.Builder(activity);
                                del.setMessage("¿Estas seguro de eliminar esta caja y sus articulos?");
                                del.setCancelable(false);
                                del.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        clsDaoCaja.eliminar(getId());
                                        listaCajas = clsDaoCaja.verTodos();
                                        notifyDataSetChanged();
                                        dialogo.dismiss();
                                        dialogoEliminar.dismiss();
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

                        cancelarEliminarCaja.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialogoEliminar.dismiss();
                                dialogo.show();
                            }
                        });

                    }
                });

                mover.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            if (dropdownCuartos.getSelectedItemPosition() == 0)
                                Toast.makeText(activity, "Tiene que elegir un cuarto primero", Toast.LENGTH_SHORT).show();
                            else{
                                caja = new Caja(getId(), idCuartosArray[dropdownCuartos.getSelectedItemPosition()]);
                                clsDaoCaja.mover(caja);
                                listaCajas = clsDaoCaja.verTodos();
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
