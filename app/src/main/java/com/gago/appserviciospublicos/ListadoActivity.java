package com.gago.appserviciospublicos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.gago.appserviciospublicos.BasedeDatos.DBControlador;
import com.gago.appserviciospublicos.Modelos.ListaAdapter;
import com.gago.appserviciospublicos.Modelos.Servicio;

import java.util.ArrayList;

public class ListadoActivity extends AppCompatActivity {

    ListView lista;

    ArrayList<Servicio> listaServicios;

    DBControlador controlador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado);

        lista = findViewById(R.id.idListaServicios);

        controlador = new DBControlador(getApplicationContext());

        listaServicios = controlador.optenerRegistros();

        ListaAdapter adapter = new ListaAdapter(getApplicationContext(), R.layout.lista_item_layout, listaServicios);
        lista.setAdapter(adapter);

        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "khe vergaa " + position, Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }
}
