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

public class MainActivity extends AppCompatActivity {

    daoArticulo dao;
    Adaptador adapter;
    ArrayList<Articulo> lista;
    Articulo articulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dao = new daoArticulo(this);
        lista = dao.verTodos();
        adapter = new Adaptador(this, lista, dao);
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
                final Dialog dialogo = new Dialog(MainActivity.this);
                dialogo.setTitle("Nuevo registro");
                dialogo.setCancelable(true);
                dialogo.setContentView(R.layout.item_parameters);
                dialogo.show();
                final EditText nombre = (EditText) dialogo.findViewById(R.id.inputItemNombre);
                final EditText cuarto = (EditText) dialogo.findViewById(R.id.inputItemCuarto);
                final EditText etiqueta = (EditText) dialogo.findViewById(R.id.inputItemEtiqueta);
                final EditText descripcion = (EditText) dialogo.findViewById(R.id.inputItemDescripcion);
                Button guardar = (Button) dialogo.findViewById(R.id.buttonAddItem);
                Button cancelar = (Button) dialogo.findViewById(R.id.buttonCancelItem);

                guardar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            articulo = new Articulo(nombre.getText().toString(),
                                    descripcion.getText().toString(),
                                    cuarto.getText().toString(),
                                    etiqueta.getText().toString());
                            dao.insertar(articulo);
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
