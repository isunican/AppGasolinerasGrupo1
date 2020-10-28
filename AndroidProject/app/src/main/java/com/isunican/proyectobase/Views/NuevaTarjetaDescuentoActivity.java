package com.isunican.proyectobase.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.isunican.proyectobase.R;

public class NuevaTarjetaDescuentoActivity extends AppCompatActivity implements View.OnClickListener {

    TextView txtNombre;
    TextView txtMarca;
    TextView txtTipoDescuento;
    TextView txtDescuento;
    TextView txtComentarios;

    Spinner spnTipoDescuento;

    TextView nombre;
    EditText marca;
    EditText descuento;
    EditText comentarios;

    Button btnGuardar;
    Button btnCancelar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_tarjeta_descuento);

        // Muestra el titulo del formulario
        getSupportActionBar().setTitle("Nueva tarjeta descuento");

        // Vistas
        txtNombre = findViewById(R.id.txtNombreTarjeta);
        txtMarca = findViewById(R.id.txtMarcaGasolinera);
        txtTipoDescuento = findViewById(R.id.txtTipoDescuento);
        txtDescuento = findViewById(R.id.txtDescuento);
        txtComentarios = findViewById(R.id.txtComentarios);
        spnTipoDescuento = findViewById(R.id.spnTipoDescuento);
        nombre = findViewById(R.id.nombreTarjeta);
        marca = findViewById(R.id.marcaGasolinera);
        descuento = findViewById(R.id.descuento);
        comentarios = findViewById(R.id.comentarios);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnCancelar = findViewById(R.id.btnCancelar);

        // Listeners de los botones
        btnGuardar.setOnClickListener(this);
        btnCancelar.setOnClickListener(this);
    }


    public void onClick(View v) {
        if(v.getId() == R.id.btnGuardar) {
            // lee y almacena datos
            //value1 = txtOperador1.getText().toString();
            //value2 = txtOperador2.getText().toString();
            String strNombre = nombre.getText().toString();
            System.out.println(strNombre);
        } else if (v.getId() == R.id.btnCancelar) {
            // Vuelve a la pantalla inicial
        }
    }


}