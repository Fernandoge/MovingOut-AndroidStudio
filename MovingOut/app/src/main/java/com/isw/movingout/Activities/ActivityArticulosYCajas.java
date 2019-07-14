package com.isw.movingout.Activities;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.isw.movingout.Adaptadores.AdaptadorArticulosCuarto;
import com.isw.movingout.Adaptadores.AdaptadorCajas;
import com.isw.movingout.Objetos.ArticuloCuarto;
import com.isw.movingout.Objetos.Caja;
import com.isw.movingout.R;
import com.isw.movingout.Daos.daoArticuloCuarto;
import com.isw.movingout.Daos.daoCaja;

import java.util.ArrayList;

public class ActivityArticulosYCajas extends AppCompatActivity {

    daoArticuloCuarto daoArticuloCuarto;
    daoCaja daoCaja;
    AdaptadorArticulosCuarto adapterArticulosCuarto;
    AdaptadorCajas adapterCajas;
    ArrayList<ArticuloCuarto> listaArticulos;
    ArrayList<Caja> listaCajas;
    ArticuloCuarto articulo;
    Caja caja;
    int currentCuartoID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_articulos);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            currentCuartoID = extras.getInt("cuartoID");
        }

        daoArticuloCuarto = new daoArticuloCuarto(this, currentCuartoID);
        listaArticulos = daoArticuloCuarto.verTodos();
        adapterArticulosCuarto = new AdaptadorArticulosCuarto(this, listaArticulos, daoArticuloCuarto);

        daoCaja = new daoCaja(this, currentCuartoID);
        listaCajas = daoCaja.verTodos();
        adapterCajas = new AdaptadorCajas(this, listaCajas, daoCaja);

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
                final EditText estado = (EditText) dialogo.findViewById(R.id.inputCajaEstado);
                final EditText tamanio = (EditText) dialogo.findViewById(R.id.inputCajaTamanio);
                final EditText etiqueta = (EditText) dialogo.findViewById(R.id.inputCajaEtiqueta);
                Button guardar = (Button) dialogo.findViewById(R.id.buttonAddCaja);
                Button cancelar = (Button) dialogo.findViewById(R.id.buttonCancelCaja);

                guardar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            caja = new Caja(nombre.getText().toString(),
                                    descripcion.getText().toString(),
                                    estado.getText().toString(),
                                    tamanio.getText().toString(),
                                    etiqueta.getText().toString(),
                                    currentCuartoID);
                            daoCaja.insertar(caja);
                            listaCajas = daoCaja.verTodos();
                            adapterCajas.notifyDataSetChanged();
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

        agregarArticulo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialogo = new Dialog(ActivityArticulosYCajas.this);
                dialogo.setTitle("Nuevo registro");
                dialogo.setCancelable(true);
                dialogo.setContentView(R.layout.crear_articulocuarto);
                dialogo.show();
                final EditText nombre = (EditText) dialogo.findViewById(R.id.inputItemNombre);
                final EditText etiqueta = (EditText) dialogo.findViewById(R.id.inputItemEtiqueta);
                Button guardar = (Button) dialogo.findViewById(R.id.buttonAddCuarto);
                Button cancelar = (Button) dialogo.findViewById(R.id.buttonCancelCuarto);

                guardar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            articulo = new ArticuloCuarto(nombre.getText().toString(),
                                    etiqueta.getText().toString(),
                                    currentCuartoID);
                            daoArticuloCuarto.insertar(articulo);
                            listaArticulos = daoArticuloCuarto.verTodos();
                            adapterArticulosCuarto.notifyDataSetChanged();
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
