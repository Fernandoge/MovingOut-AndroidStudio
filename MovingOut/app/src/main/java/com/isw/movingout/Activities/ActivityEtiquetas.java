package com.isw.movingout.Activities;

import android.app.Activity;
import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.isw.movingout.Adaptadores.AdaptadorEtiquetas;
import com.isw.movingout.Daos.daoEtiqueta;
import com.isw.movingout.Objetos.Etiqueta;
import com.isw.movingout.R;

import java.util.ArrayList;

public class ActivityEtiquetas extends AppCompatActivity {

    daoEtiqueta dao;
    AdaptadorEtiquetas adapter;
    ArrayList<Etiqueta> lista;
    Etiqueta etiqueta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_etiquetas);

        dao = new daoEtiqueta(this);
        lista = dao.verTodos();
        adapter = new AdaptadorEtiquetas(this, lista, dao);
        ListView list = (ListView) findViewById(R.id.listBox);
        Button agregar = (Button) findViewById(R.id.buttonAddEtiqueta);
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
                final Dialog dialogo = new Dialog(ActivityEtiquetas.this);
                dialogo.setTitle("Nuevo registro");
                dialogo.setCancelable(true);
                dialogo.setContentView(R.layout.crear_etiqueta);
                dialogo.show();
                final EditText nombre = (EditText) dialogo.findViewById(R.id.inputEtiquetaNombre);
                Button guardar = (Button) dialogo.findViewById(R.id.buttonAddEtiqueta);
                Button cancelar = (Button) dialogo.findViewById(R.id.buttonCancelEtiqueta);
                Button eliminar = (Button) dialogo.findViewById(R.id.buttonEliminarEtiqueta);
                eliminar.setVisibility(View.GONE);

                guardar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            etiqueta = new Etiqueta(nombre.getText().toString());
                            dao.insertar(etiqueta);
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
