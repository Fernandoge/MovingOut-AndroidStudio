package com.isw.movingout.Activities;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.isw.movingout.Adaptadores.AdaptadorArticulosCaja;
import com.isw.movingout.Daos.daoArticuloCaja;
import com.isw.movingout.Daos.daoCaja;
import com.isw.movingout.Objetos.ArticuloCaja;
import com.isw.movingout.Objetos.Caja;
import com.isw.movingout.R;

import java.util.ArrayList;

public class ActivityArticulosCaja extends AppCompatActivity {

    daoArticuloCaja daoArticuloCaja;
    daoCaja daoCaja;
    AdaptadorArticulosCaja adapter;
    ArrayList<ArticuloCaja> listaArticulosCaja;
    ArrayList<Caja> listaCajas;
    ArticuloCaja articuloCaja;
    int currentCajaID;
    String currentCajaNombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_articulocajas);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            currentCajaID = extras.getInt("cajaID");
            currentCajaNombre = extras.getString("cajaNombre");
        }

        daoArticuloCaja = new daoArticuloCaja(this, currentCajaID);
        daoCaja = new daoCaja(this,0);
        listaArticulosCaja = daoArticuloCaja.verTodos();
        listaCajas = daoCaja.obtenerCajas();
        adapter = new AdaptadorArticulosCaja(this, listaArticulosCaja, daoArticuloCaja, listaCajas);
        TextView textCurrentCaja = (TextView) findViewById(R.id.textCurrentCajaNombre);
        textCurrentCaja.setText(currentCajaNombre);
        ListView list = (ListView) findViewById(R.id.listBox);
        Button agregarArticuloCaja = (Button) findViewById(R.id.buttonAddArticuloCaja);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Dialogo para ver vista previa de registro vista.xml
            }
        });

        agregarArticuloCaja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialogo = new Dialog(ActivityArticulosCaja.this);
                dialogo.setTitle("Nuevo registro caja");
                dialogo.setCancelable(true);
                dialogo.setContentView(R.layout.crear_articulocaja);
                dialogo.show();
                final EditText nombre = (EditText) dialogo.findViewById(R.id.inputArticuloCajaNombre);
                final EditText cantidad = (EditText) dialogo.findViewById(R.id.inputArticuloCajaCantidad);
                Button guardar = (Button) dialogo.findViewById(R.id.buttonAddArticuloCaja);
                Button cancelar = (Button) dialogo.findViewById(R.id.buttonCancelArticuloCaja);

                Button eliminar = (Button) dialogo.findViewById(R.id.buttonEliminarArticuloCaja);
                final Spinner dropdownCajas = (Spinner) dialogo.findViewById(R.id.dropdownMoverArticuloCaja);
                Button mover = (Button) dialogo.findViewById(R.id.buttonMoverArticuloCaja);
                TextView textMover = (TextView) dialogo.findViewById(R.id.text2);
                eliminar.setVisibility(View.GONE);
                dropdownCajas.setVisibility(View.GONE);
                mover.setVisibility(View.GONE);
                textMover.setVisibility(View.GONE);


                guardar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            articuloCaja = new ArticuloCaja(nombre.getText().toString(),
                                    Integer.parseInt(cantidad.getText().toString()), currentCajaID);
                            daoArticuloCaja.insertar(articuloCaja);
                            listaArticulosCaja = daoArticuloCaja.verTodos();
                            adapter.notifyDataSetChanged();
                            dialogo.dismiss();
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
