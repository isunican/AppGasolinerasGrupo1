package com.isunican.proyectobase.Views;

import androidx.appcompat.app.AppCompatActivity;

import com.isunican.proyectobase.Model.Gasolinera;
import com.isunican.proyectobase.Presenter.PresenterFiltroMarcas;
import com.isunican.proyectobase.Presenter.PresenterGasolineras;
import com.isunican.proyectobase.R;

import android.app.Activity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import java.util.ArrayList;

public class FiltroMarcaAcivity extends AppCompatActivity {

/**
 * Vista Filtrar por gasolinera
 *
 *
 *
 * @author Carolay Corales
 * @version 0.0.1
 */

    private PresenterFiltroMarcas presenterFiltroMarcas;
    private EditText campo_marca;
    private ListView lista_marcas;
    private ArrayAdapter<String> adapter;
    private ArrayList<Gasolinera> listaSinFiltrar;
    private ArrayList<Gasolinera> listaFiltrada;
    private String[] s;
    private ArrayList marcasGasolineras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtro_marca_acivity);

        s = new String[]{"Elena", "Chanyeol", "Chen", "Baekhyun", "Jongin"};

        //listaSinFiltrar=getIntent().getExtras().getParcelable(getResources().getString(R.string.pasoListaGasolinerasFiltros));
       // presenterFiltroMarcas = new PresenterFiltroMarcas(listaSinFiltrar);
       // marcasGasolineras = (ArrayList) presenterFiltroMarcas.getMarcas();
        //
        campo_marca = findViewById(R.id.txtMarca);
        //lista_marcas = findViewById(R.id.list_marcas);

        //Ventana flotante
        DisplayMetrics medidasVentana = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(medidasVentana);

        int ancho = medidasVentana.widthPixels;
        int alto = medidasVentana.heightPixels;

        //Porcentajes que ocupa la ventana en la pantalla
        getWindow().setLayout((int)(ancho*0.85), (int)(alto*0.70));

        //Adapter
        //adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,s);
       // lista_marcas.setAdapter(adapter);

        //Filtra el texto del EditText con los string del ListView
        campo_marca.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                adapter.getFilter().filter(s);
            }
        });


        campo_marca.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent event)
            {
                boolean action = false;
                if (actionId == EditorInfo.IME_ACTION_SEND)
                {
                    // hide keyboard
                    InputMethodManager inputMethodManager = (InputMethodManager) textView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(textView.getWindowToken(), 0);

                    action = true;
                }
                return action;
            }
        });
       
        //lista_marcas.setOnItemClickListener(new AdapterView.OnItemClickListener(){
         //   public void onItemClick(AdapterView<?> a, View v, int position, long id) {
           //     String marca = lista_marcas.getItemAtPosition(position).toString();
             //   campo_marca.setText(marca);
          //  }
      //  });

      

    }
}