package com.isw.movingout.Activities;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.isw.movingout.Adaptadores.AdaptadorArticulosCuarto;
import com.isw.movingout.Adaptadores.AdaptadorCajas;
import com.isw.movingout.Daos.daoCuarto;
import com.isw.movingout.Daos.daoEtiqueta;
import com.isw.movingout.Objetos.ArticuloCuarto;
import com.isw.movingout.Objetos.Caja;
import com.isw.movingout.Objetos.Cuarto;
import com.isw.movingout.R;
import com.isw.movingout.Daos.daoArticuloCuarto;
import com.isw.movingout.Daos.daoCaja;

import java.util.ArrayList;

public class ActivityArticulosYCajas extends AppCompatActivity {

    daoEtiqueta daoEtiqueta;
    daoArticuloCuarto daoArticuloCuarto;
    daoCaja daoCaja;
    daoCuarto daoCuarto;
    AdaptadorArticulosCuarto adapterArticulosCuarto;
    AdaptadorCajas adapterCajas;
    ArrayList<ArticuloCuarto> listaArticulos;
    ArrayList<Caja> listaCajas;
    ArrayList<Cuarto> listaCuartos;
    ArticuloCuarto articulo;
    Caja caja;
    int currentCuartoID;
    String currentCuartoNombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_articulosycajas);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            currentCuartoID = extras.getInt("cuartoID");
            currentCuartoNombre = extras.getString("cuartoNombre");
        }

        setTitle("Cuarto actual: " + currentCuartoNombre);
        daoEtiqueta = new daoEtiqueta(this);

        daoCuarto = new daoCuarto(this);
        listaCuartos = daoCuarto.verTodos(currentCuartoID);

        daoArticuloCuarto = new daoArticuloCuarto(this, currentCuartoID);
        listaArticulos = daoArticuloCuarto.verTodos();
        adapterArticulosCuarto = new AdaptadorArticulosCuarto(this, listaArticulos, daoArticuloCuarto, daoEtiqueta, listaCuartos);

        daoCaja = new daoCaja(this, currentCuartoID);
        listaCajas = daoCaja.verTodos();
        adapterCajas = new AdaptadorCajas(this, listaCajas, daoCaja, daoEtiqueta, listaCuartos);

        ListView listArticulos = (ListView) findViewById(R.id.listBoxArticulos);
        ListView listCajas = (ListView) findViewById(R.id.listBoxCajas);
        Button agregarCaja = (Button) findViewById(R.id.buttonAddCaja);
        Button agregarArticulo = (Button) findViewById(R.id.buttonAddArticuloCuarto);
        listArticulos.setAdapter(adapterArticulosCuarto);
        listArticulos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Dialogo para ver vista previa de registro vista.xml
            }
        });
        listCajas.setAdapter(adapterCajas);
        listCajas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });


        agregarCaja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialogo = new Dialog(ActivityArticulosYCajas.this);
                dialogo.setTitle("Nuevo registro caja");
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
                final Spinner dropdownCajas = (Spinner) dialogo.findViewById(R.id.dropdownMoverCaja);
                Button mover = (Button) dialogo.findViewById(R.id.buttonMoverCaja);
                TextView textMover = (TextView) dialogo.findViewById(R.id.textMoverCaja);
                TextView separador = (TextView) dialogo.findViewById(R.id.separador);

                eliminar.setVisibility(View.GONE);
                dropdownCajas.setVisibility(View.GONE);
                mover.setVisibility(View.GONE);
                textMover.setVisibility(View.GONE);
                separador.setVisibility(View.GONE);


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
                                    Toast.makeText(getApplication(), "Seleccione un tamaño para la caja", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                caja = new Caja(nombre.getText().toString(),
                                        descripcion.getText().toString(),
                                        dropdownTamanio.getSelectedItem().toString(),
                                        currentCuartoID);
                                daoCaja.insertar(caja);
                                listaCajas = daoCaja.verTodos();
                                adapterCajas.notifyDataSetChanged();
                                dialogo.dismiss();
                            }
                        }catch (Exception e){
                            Toast.makeText(getApplication(), "ERROR", Toast.LENGTH_SHORT).show();
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

        agregarArticulo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialogo = new Dialog(ActivityArticulosYCajas.this);
                dialogo.setTitle("Nuevo registro");
                dialogo.setCancelable(true);
                dialogo.setContentView(R.layout.crear_articulocuarto);
                dialogo.show();
                final EditText nombre = (EditText) dialogo.findViewById(R.id.inputItemNombre);

                //Spinner tipo
                final Spinner dropdownTipo = (Spinner) dialogo.findViewById(R.id.dropdownArticuloCuartoTipo);
                String[] spinnerTamanioArray = {"", "Mueble", "Electrodoméstico", "Decoración", "Planta", "Implemento Gym", "Otros"};
                ArrayAdapter<String> spinnerTamanioArrayAdapter = new ArrayAdapter<String> (dialogo.getContext(), android.R.layout.simple_spinner_item, spinnerTamanioArray);
                dropdownTipo.setAdapter(spinnerTamanioArrayAdapter);

                Button guardar = (Button) dialogo.findViewById(R.id.buttonAddCuarto);
                Button cancelar = (Button) dialogo.findViewById(R.id.buttonCancelCuarto);
                Button eliminar = (Button) dialogo.findViewById(R.id.buttonEliminarArticuloCuarto);
                final Spinner dropdownCuartos = (Spinner) dialogo.findViewById(R.id.dropdownMoverArticuloCuarto);
                Button mover = (Button) dialogo.findViewById(R.id.buttonMoverArticuloCuarto);
                TextView textMover = (TextView) dialogo.findViewById(R.id.textMoverArticuloCuarto);
                TextView separador = (TextView) dialogo.findViewById(R.id.separador);

                eliminar.setVisibility(View.GONE);
                dropdownCuartos.setVisibility(View.GONE);
                mover.setVisibility(View.GONE);
                textMover.setVisibility(View.GONE);
                separador.setVisibility(View.GONE);


                guardar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            if (nombre.getText().toString().isEmpty() || dropdownTipo.getSelectedItemPosition() == 0){
                                if(nombre.getText().toString().isEmpty())
                                    nombre.setError("Este campo es obligatorio");
                                if (dropdownTipo.getSelectedItemPosition() == 0)
                                    Toast.makeText(getApplication(), "Seleccione un tipo para el articulo", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                articulo = new ArticuloCuarto(nombre.getText().toString(), dropdownTipo.getSelectedItem().toString(), currentCuartoID);
                                daoArticuloCuarto.insertar(articulo);
                                listaArticulos = daoArticuloCuarto.verTodos();
                                adapterArticulosCuarto.notifyDataSetChanged();
                                dialogo.dismiss();
                            }
                        }catch (Exception e){
                            Toast.makeText(getApplication(), "ERROR", Toast.LENGTH_SHORT).show();
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

    }
}
