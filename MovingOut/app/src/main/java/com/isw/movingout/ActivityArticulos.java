package com.isw.movingout;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ActivityArticulos extends AppCompatActivity {

    daoArticulo dao;
    AdaptadorArticulos adapter;
    ArrayList<Articulo> lista;
    Articulo articulo;
    String currentCuarto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_articulos);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            currentCuarto = extras.getString("cuarto");
        }

        dao = new daoArticulo(this, currentCuarto);
        lista = dao.verTodos();
        adapter = new AdaptadorArticulos(this, lista, dao);

        ListView list = (ListView) findViewById(R.id.listBox);
        Button agregar = (Button) findViewById(R.id.buttonAdd);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Dialogo para ver vista previa de registro vista.xml
            }
        });

        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialogo = new Dialog(ActivityArticulos.this);
                dialogo.setTitle("Nuevo registro");
                dialogo.setCancelable(true);
                dialogo.setContentView(R.layout.crear_articulocuarto);
                dialogo.show();
                final EditText nombre = (EditText) dialogo.findViewById(R.id.inputCuartoNombre);
                final EditText etiqueta = (EditText) dialogo.findViewById(R.id.inputItemEtiqueta);
                final EditText descripcion = (EditText) dialogo.findViewById(R.id.inputItemDescripcion);
                Button guardar = (Button) dialogo.findViewById(R.id.buttonAddCuarto);
                Button cancelar = (Button) dialogo.findViewById(R.id.buttonCancelCuarto);

                guardar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            articulo = new Articulo(nombre.getText().toString(),
                                    descripcion.getText().toString(),
                                    currentCuarto,
                                    etiqueta.getText().toString());
                            dao.insertar(articulo);
                            lista = dao.verTodos();
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
