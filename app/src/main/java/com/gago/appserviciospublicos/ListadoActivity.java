package com.gago.appserviciospublicos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
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

    ListaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado);

        lista = findViewById(R.id.idListaServicios);

        controlador = new DBControlador(getApplicationContext());

        listaServicios = controlador.optenerRegistros();

        adapter = new ListaAdapter(getApplicationContext(), R.layout.lista_item_layout, listaServicios);
        lista.setAdapter(adapter);

        registerForContextMenu(lista);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.listado_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()) {
            case R.id.idModificarRegistroMenu:
                modificarRegistro(menuInfo.position);
                return true;
            case R.id.idBorrarRegistroMenu:
                borrarRegistro(menuInfo.position);
                return true;
        }
        return super.onContextItemSelected(item);
    }

    private void modificarRegistro(int posicion) {
        Intent intent = new Intent(this, ModificarActivity.class);
        intent.putExtra("indice", posicion);
        startActivity(intent);
        listaServicios = controlador.optenerRegistros();
        adapter = new ListaAdapter(getApplicationContext(), R.layout.lista_item_layout, listaServicios);
        lista.setAdapter(adapter);
    }

    private void borrarRegistro(int posicion) {
        int retorno = controlador.borrarRegistro(listaServicios.get(posicion));
        if (retorno == 1) {
            Toast.makeText(getApplicationContext(), "registro eliminado", Toast.LENGTH_SHORT).show();
            adapter = new ListaAdapter(getApplicationContext(), R.layout.lista_item_layout, listaServicios);
            lista.setAdapter(adapter);
        } else {
            Toast.makeText(getApplicationContext(), "error al borrar", Toast.LENGTH_SHORT).show();
        }
    }
}
