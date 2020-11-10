package com.isunican.proyectobase.Views;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.isunican.proyectobase.Model.Gasolinera;
import com.isunican.proyectobase.Presenter.PresenterGasolineras;
import com.isunican.proyectobase.R;
import androidx.appcompat.app.AppCompatActivity;


public class FiltroFavoritosActivity extends AppCompatActivity  {

    // PresenterGasolineras presenterGasolineras;
    PresenterGasolineras presenterGasolineras;
    // Vista de lista y adaptador para cargar datos en ella
    ListView listViewFav;
    ArrayAdapter<Gasolinera> adapter;

    /**
     * onCreate
     *
     * Crea los elementos que conforman la actividad
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav);

        // muestra el logo en el actionBar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.por_defecto_mod);


        //Inserta lista de gasolineras en la listView
        listViewFav = findViewById(R.id.listViewGasolineras);







    }
}
