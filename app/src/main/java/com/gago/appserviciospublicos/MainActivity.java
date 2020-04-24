package com.gago.appserviciospublicos;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.gago.appserviciospublicos.BasedeDatos.DBControlador;
import com.gago.appserviciospublicos.Modelos.Servicio;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    DBControlador controlador;

    Spinner spTipoServicio;
    EditText edMedidor, edDireccion;
    TextView tvUnidad;

    Button btGuardar, btCancelar;

    int tipoDServicio;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        spTipoServicio = findViewById(R.id.idSpinnerTipoServicio);
        edMedidor = findViewById(R.id.idEdMedida);
        edDireccion = findViewById(R.id.idEdDireccion);
        tvUnidad = findViewById(R.id.idTvUnidad);
        btGuardar = findViewById(R.id.idBtGuardar);
        btCancelar = findViewById(R.id.idBtCancelar);

        controlador = new DBControlador(getApplicationContext());

        ArrayAdapter<CharSequence> otroAdacter = ArrayAdapter.createFromResource(this
                , R.array.spinner_tipo_servicio, R.layout.support_simple_spinner_dropdown_item);
        spTipoServicio.setAdapter(otroAdacter);

        spTipoServicio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tipoDServicio = position;
                switch (tipoDServicio) {
                    case 0:
                        tvUnidad.setText(view.getResources().getString(R.string.unidad_agua));
                        break;
                    case 1:
                        tvUnidad.setText(view.getResources().getString(R.string.unidad_luz));
                        break;
                    case 2:
                        tvUnidad.setText(view.getResources().getString(R.string.unidad_gas));
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btGuardar.setOnClickListener(this);
        btCancelar.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.idBtGuardar:
                Calendar calendar = Calendar.getInstance();
                int medicion = edMedidor.getText().toString().isEmpty() ? 0 : Integer.parseInt(edMedidor.getText().toString());
                Servicio servicio = new Servicio(edDireccion.getText().toString(), calendar, medicion, tipoDServicio);
                long retorno = controlador.agregarRegistro(servicio);
                if (retorno != -1) {
                    Toast.makeText(v.getContext(), "registro guardado", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(v.getContext(), "registro fallido", Toast.LENGTH_SHORT).show();
                }
                limpiarCampo();
                break;
            case R.id.idBtCancelar:
                limpiarCampo();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_listado) {
            Intent i = new Intent(this, ListadoActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void limpiarCampo() {
        edMedidor.setText("");
        edDireccion.setText("");
    }

}
