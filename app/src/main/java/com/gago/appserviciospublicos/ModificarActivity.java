package com.gago.appserviciospublicos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.gago.appserviciospublicos.BasedeDatos.DBControlador;
import com.gago.appserviciospublicos.Modelos.Servicio;

import java.util.ArrayList;
import java.util.Calendar;

public class ModificarActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvTitulo, tvUnidad;
    EditText edDireccion, edMedidor;
    Spinner spinnerTipoServicio;
    Button btGuardar, btCancelar;

    DBControlador controlador;

    int tipoDServicio;
    int indice;
    long id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        tvTitulo = findViewById(R.id.idTvTitulo);
        tvUnidad = findViewById(R.id.idTvUnidad);
        edDireccion = findViewById(R.id.idEdDireccion);
        edMedidor = findViewById(R.id.idEdMedida);
        spinnerTipoServicio = findViewById(R.id.idSpinnerTipoServicio);
        btGuardar = findViewById(R.id.idBtGuardar);
        btCancelar = findViewById(R.id.idBtCancelar);

        tvTitulo.setText(getString(R.string.modificar_registro));

        controlador = new DBControlador(getApplicationContext());

        Intent i = getIntent();
        indice = i.getIntExtra("indice", 0);

        ArrayList<Servicio> lista = controlador.optenerRegistros();

        Servicio servicio = lista.get(indice);
        id = servicio.getId();

        edDireccion.setText(servicio.getDireccion());
        edMedidor.setText(Integer.toString(servicio.getMedida()));


        ArrayAdapter<CharSequence> otroAdacter = ArrayAdapter.createFromResource(this
                , R.array.spinner_tipo_servicio, R.layout.support_simple_spinner_dropdown_item);
        spinnerTipoServicio.setAdapter(otroAdacter);
        spinnerTipoServicio.setSelection(servicio.getTipoServicio());
        spinnerTipoServicio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tipoDServicio = position;
                switch (tipoDServicio) {
                    case 0:
                        tvUnidad.setText("m^3");
                        break;
                    case 1:
                        tvUnidad.setText("kw/h");
                        break;
                    case 2:
                        tvUnidad.setText("cc");
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
                try {
                    int medicion = edMedidor.getText().toString().isEmpty() ? 0 : Integer.parseInt(edMedidor.getText().toString());
                    Servicio servicio = new Servicio(id, edDireccion.getText().toString(), calendar, medicion, tipoDServicio);
                    int retorno = controlador.actualizarRegistro(servicio);
                    if (retorno == 1) {
                        Toast.makeText(getApplicationContext(), "actualizacion exitosa", Toast.LENGTH_SHORT).show();
                        setResult(Activity.RESULT_OK);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "fallo en la actualizacion", Toast.LENGTH_SHORT).show();
                    }
                } catch (NumberFormatException nuEx) {
                    Toast.makeText(getApplicationContext(), "numero muy grande", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.idBtCancelar:
                setResult(Activity.RESULT_CANCELED);
                finish();
                break;
        }
    }
}
