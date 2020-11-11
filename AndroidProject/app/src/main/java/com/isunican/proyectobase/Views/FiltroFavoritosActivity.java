package com.isunican.proyectobase.Views;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.isunican.proyectobase.Model.Gasolinera;
import com.isunican.proyectobase.Presenter.PresenterGasolineras;
import com.isunican.proyectobase.R;
import androidx.appcompat.app.AppCompatActivity;
import  com.isunican.proyectobase.Presenter.*;


public class FiltroFavoritosActivity extends AppCompatActivity  {

    private static final int BTN_POSITIVO = DialogInterface.BUTTON_POSITIVE;

    // Vista de lista y adaptador para cargar datos en ella
    ListView listViewFav;

    //Elementos del dialgo de filtro fav
    ListView listViewMarcasFavDialog;
    ListView listViewLocalidadFavDialog;
    EditText textMarcaFavDialog;
    EditText textLocalidadFavDialog;

    //Adapter String prueba marcas y localidades
    ArrayAdapter<String> adapterListMarcas;
    ArrayAdapter<String> adapterListLocalidades;

    PresenterGasolineras gas;
    PresenterFiltroMarcas marcaslist;

    ArrayAdapter<String> adapterMarcas;


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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Titulo en el actionBar
        this.setTitle(R.string.title_fav);


        // muestra el logo en el actionBar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.por_defecto_mod);


        gas = new PresenterGasolineras();
        gas.cargaDatosDummy();
        marcaslist = new PresenterFiltroMarcas(gas.getGasolineras());

        String [] c = new String[] {"Hola", "Bro", "Que", "Tal"};
        adapterMarcas = new ArrayAdapter<>(this,  android.R.layout.simple_dropdown_item_1line, c);
        //Inserta lista de gasolineras favoritas en la listView
       listViewFav = findViewById(R.id.listFavGasolineras);
       listViewFav.setAdapter(adapterMarcas);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.fav_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_favorite){
            creaDialogoFiltroFav();
        }
        return true;
    }


    /*
    Ventana flotante para filtrar marca y localidad
     */
    public void creaDialogoFiltroFav(){
        //Creamos el dilogo
        final AlertDialog alertDialogBuilder = new AlertDialog.Builder(this)
                //Set to null. We override the onclick
                .setPositiveButton(android.R.string.ok, null)
                .setNegativeButton(android.R.string.cancel, null)
                .create();

        //Implementar el funcionamiento del boton aceptar
        alertDialogBuilder.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {

                Button b = alertDialogBuilder.getButton(BTN_POSITIVO);
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
            }
        });

        // Definidos Inflater y View
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_dialog_fav, null);

        //Array de String de prueba
        String [] c = new String[] {"Hola", "Bro", "Que", "Tal",",","Muy", "Bien", "Manin", "De", "Puta","Pena"};
        adapterListMarcas = new ArrayAdapter<>(this,  android.R.layout.simple_dropdown_item_1line, c);
        adapterListLocalidades = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, c);

        //Definimos los elementos del dialogo
        listViewMarcasFavDialog = view.findViewById(R.id.listViewMarcasFavDialog);
        listViewLocalidadFavDialog = view.findViewById(R.id.listViewLocalidadesFavDialog);
        textLocalidadFavDialog = view.findViewById(R.id.textLocalidadFavDialog);
        textMarcaFavDialog = view.findViewById(R.id.textMarcaFavDialog);

        //Pasamos el adapter a las listView
        listViewMarcasFavDialog.setAdapter(adapterListMarcas);
        listViewLocalidadFavDialog.setAdapter(adapterListLocalidades);

        // Insertar elementos en el dialogo
        alertDialogBuilder.setView(view);
        alertDialogBuilder.show();

    }
}
