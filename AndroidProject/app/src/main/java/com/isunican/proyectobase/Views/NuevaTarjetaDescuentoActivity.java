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

    private TextView txtNombre;
    private TextView txtMarca;
    private TextView txtTipoDescuento;
    private TextView txtDescuento;
    private TextView txtComentarios;
    private TextView nombre;
    private TextView marca;
    private TextView descuento;
    private TextView comentarios;

    private Spinner spnTipoDescuento;

    private Button btnGuardar;
    private Button btnCancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_tarjeta_descuento);

        // Muestra el titulo del formulario
        getSupportActionBar().setTitle(getResources().getString(R.string.nueva_tarjeta_descuento));

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
        String[] datos = new String[] {getResources().getString(R.string.porcentual),
                getResources().getString(R.string.cts_litro)};
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

            // Si hay algún campo sin rellenar, salta un aviso al usuario
            if (strNombre.equals("")  || strMarca.equals("") || strDescuento.equals("")){
                Toast toast = Toast.makeText(getApplicationContext(),
                        getResources().getString(R.string.complete_todos_los_campos), Toast.LENGTH_LONG);
                toast.show();
            } else {
                Intent intent = getIntent();
                intent.putExtra("nombre", strNombre);
                intent.putExtra("marca", strMarca);
                intent.putExtra("tipo", strTipoDescuento);
                intent.putExtra("descuento", strDescuento);
                intent.putExtra("descripcion", strComentarios);
                setResult(RESULT_OK, intent);
                finish();
            }
        } else if (v.getId() == R.id.btnCancelar) {
            // Vuelve a la pantalla inicial sin pasarle ningún dato
            setResult(RESULT_CANCELED);
            finish();
        }
    }
    @Override
    public void onBackPressed(){
        setResult(RESULT_CANCELED);
        finish();
    }
}