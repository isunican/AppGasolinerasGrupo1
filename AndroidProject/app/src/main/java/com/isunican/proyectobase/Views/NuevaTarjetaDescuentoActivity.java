package com.isunican.proyectobase.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.isunican.proyectobase.Presenter.PresenterTarjetaDescuento;
import com.isunican.proyectobase.R;

public class NuevaTarjetaDescuentoActivity extends AppCompatActivity implements View.OnClickListener {

    TextView txtNombre;
    TextView txtMarca;
    TextView txtTipoDescuento;
    TextView txtDescuento;
    TextView txtComentarios;
    TextView nombre;
    TextView marca;
    TextView descuento;
    TextView comentarios;

    Spinner spnTipoDescuento;

    Button btnGuardar;
    Button btnCancelar;

    PresenterTarjetaDescuento presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_tarjeta_descuento);

        // Presenter
        presenter = new PresenterTarjetaDescuento();

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

        // Datos del spinner del tipo de descuento
        String[] datos = new String[] {"Porcentual", "cts/L"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, datos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Aplica el adaptador creado a nuestro spinner
        spnTipoDescuento.setAdapter(adapter);
    }


    public void onClick(View v) {
        if(v.getId() == R.id.btnGuardar) {
            // lee y almacena datos
            String strNombre = nombre.getText().toString();
            String strMarca = marca.getText().toString();
            String strTipoDescuento = spnTipoDescuento.getSelectedItem().toString();
            String strDescuento = descuento.getText().toString();
            String strComentarios = comentarios.getText().toString();

            System.out.println(strNombre + strMarca +strTipoDescuento + strDescuento + strComentarios);
            // Si hay algún campo sin rellenar, salta un aviso al usuario
            if (strNombre.equals("")  || strMarca.equals("") || strDescuento.equals("")){
                System.out.println("estro promer if");
                Toast toast = Toast.makeText(getApplicationContext(), "Complete todos los campos", Toast.LENGTH_LONG);
                toast.show();
            } else {
                System.out.println("entro else");
                presenter.anhadirNuevaTarjeta(strNombre,strComentarios,strMarca,strTipoDescuento,strDescuento);
                // TENER EN CUENTA QUE NO SE ESTAN ACTUALIZANDO TODAVIA LOS PRECIOS
                Toast toast = Toast.makeText(getApplicationContext(), "Tarjeta añadida correctamente", Toast.LENGTH_LONG);
                Intent intent = new Intent(NuevaTarjetaDescuentoActivity.this, MainActivity.class);
                NuevaTarjetaDescuentoActivity.this.startActivity(intent);
                toast.show();
            }
        } else if (v.getId() == R.id.btnCancelar) {
            // Vuelve a la pantalla inicial
            Intent intent = new Intent(NuevaTarjetaDescuentoActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }


}