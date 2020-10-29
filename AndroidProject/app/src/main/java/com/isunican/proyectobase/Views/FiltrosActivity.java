package com.isunican.proyectobase.Views;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.isunican.proyectobase.R;


/**
 * Actividad para filtrar por tipo de gasolina
 *
 * @author Miguel
 */
public class FiltrosActivity extends AppCompatActivity implements View.OnClickListener {

    Spinner spinnerTiposGasolina;
    Button btnAplicar;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filtros_gasolinera);

        spinnerTiposGasolina = findViewById(R.id.spinner_tipoGasolina);
        btnAplicar = findViewById(R.id.aplicar_button);
        btnAplicar.setOnClickListener(this);

        //Ventana flotante
        DisplayMetrics medidasVentana = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(medidasVentana);
        getSupportActionBar().setTitle("Filtrar por tipo de gasolina");

        int ancho = medidasVentana.widthPixels;
        int alto = medidasVentana.heightPixels;

        //Porcentajes que ocupa la ventana en la pantalla
        getWindow().setLayout((int)(ancho*0.85), (int)(alto*0.70));

        String[] datos = new String[] {"Gasolina 95", "Diesel"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, datos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerTiposGasolina.setAdapter(adapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.aplicar_button:
                String strTipoGasolina = spinnerTiposGasolina.getSelectedItem().toString();
                // aplicar filtros

                Toast t = Toast.makeText(getApplicationContext(), strTipoGasolina, Toast.LENGTH_LONG);
                t.show();
                //mandar los datos obtenidos de vuelta
                Intent intentResultadoTipo=new Intent();
                intentResultadoTipo.putExtra("tipo",strTipoGasolina);
                setResult(Activity.RESULT_OK,intentResultadoTipo);
                this.finish();
                break;
        }
    }
}
