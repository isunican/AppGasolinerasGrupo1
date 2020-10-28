package com.isunican.proyectobase.Views;

import androidx.appcompat.app.AppCompatActivity;
import com.isunican.proyectobase.R;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;


public class FiltroMarcaAcivity extends AppCompatActivity {

    EditText campo_marca;
    private  ListView lista_marcas;
    private ArrayAdapter<String> adapter;
    private String[] marcasGasolineras={"Repsol","Cepsa","Petronol","Galp","Cobo","Shell","Carrefour","Avia","Meroil", "Eroski", "EasyGas"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtro_marca_acivity);

        //
        campo_marca = findViewById(R.id.txtMarca);
        lista_marcas = findViewById(R.id.list_marcas);

        //Ventana flotante
        DisplayMetrics medidasVentana = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(medidasVentana);

        int ancho = medidasVentana.widthPixels;
        int alto = medidasVentana.heightPixels;

        //Porcentajes que ocupa la ventana en la pantalla
        getWindow().setLayout((int)(ancho*0.85), (int)(alto*0.70));

        //Adapter
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, marcasGasolineras);
        lista_marcas.setAdapter(adapter);

        //Filtra el texto del EditText con los string del ListView
        campo_marca.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

}