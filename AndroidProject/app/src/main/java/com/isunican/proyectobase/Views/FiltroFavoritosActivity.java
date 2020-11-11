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
import android.widget.Toast;



import com.isunican.proyectobase.Presenter.PresenterGasolinerasFavoritas;
import com.isunican.proyectobase.R;
import androidx.appcompat.app.AppCompatActivity;



/**
------------------------------------------------------------------
    Vista filtrar gasolineras favoritas

    Presenta los datos de las gasolineras favoritas en formato lista.
    Dispone de una ventana flotante al que se accede mediante un boton
    con el icono de lupa. La ventana flotante muestra dos listas
    (marcas y localidades) y dos cajas de texto.

    @author Carolay Corales
------------------------------------------------------------------
*/

public class FiltroFavoritosActivity extends AppCompatActivity  {

    private static final int BTN_POSITIVO = DialogInterface.BUTTON_POSITIVE;

    // Elemento de la activity. ListView que contendra las gasolineras favoritas
    ListView listViewFav;

    //Elementos del dialgo de filtro fav
    ListView listViewMarcasFavDialog;
    ListView listViewLocalidadFavDialog;
    EditText textMarcaFavDialog;
    EditText textLocalidadFavDialog;


    PresenterGasolinerasFavoritas presenterGasolinerasFavoritas;

    //Adapter para lista de marcas y localidades
    ArrayAdapter<String> adapterListMarcas;
    ArrayAdapter<String> adapterListLocalidades;

    //Adapter para la lista de gasolineras favoritas
    //"definir tipo" adapterFavoritas;


    /**
     * onCreate
     *
     * Crea los elementos que conforman la actividad
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Titulo en el actionBar
        this.setTitle(R.string.title_fav);

        presenterGasolinerasFavoritas = new PresenterGasolinerasFavoritas(this.getApplicationContext());
        FetcherThread hilo = new FetcherThread(presenterGasolinerasFavoritas);
        new Thread(hilo).start();

        // muestra el logo en el actionBar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.por_defecto_mod);

        //Adapter al que se le pasa la lista de gasolineras favoritas
        // adapterFavoritas = //Insertar lista de gasolineras favoritas

        //Inserta lista de gasolineras favoritas en la listView
        // listViewFav = findViewById(R.id.listFavGasolineras);
        // listViewFav.setAdapter(adapterMarcas);

    }

    /**
     * Menú action bar
     * <p>
     * Redefine métodos para el uso de un menú de tipo action bar.
     * <p>
     * onCreateOptionsMenu
     * Carga las opciones del menú a partir del fichero de recursos menu/fav_menu.xml
     * <p>
     * onOptionsItemSelected
     * Define las respuestas a las distintas opciones del menú
     */
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


    /**
     *  Ventana flotante para filtrar marca y localidad.
     */
    public void creaDialogoFiltroFav(){
        // Definidos Inflater y View
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_dialog_fav, null);

        //Definimos los elementos del dialogo
        listViewMarcasFavDialog = view.findViewById(R.id.listViewMarcasFavDialog);
        listViewLocalidadFavDialog = view.findViewById(R.id.listViewLocalidadesFavDialog);
        textLocalidadFavDialog = view.findViewById(R.id.textLocalidadFavDialog);
        textMarcaFavDialog = view.findViewById(R.id.textMarcaFavDialog);

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

                        //TODO: comportamiento que se ejecuta cuando se da al boton de aceptar

                        //Mensaje de datos filtrados
                        Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.toast_filtro_aplicado), Toast.LENGTH_LONG);
                        toast.show();
                        //Cierra el dialogo
                        alertDialogBuilder.dismiss();
                    }
                });
            }
        });


        //Adapters al que se les pasa la lista de marcas y localidades
        //adapterListMarcas = new ArrayAdapter<>(this,  android.R.layout.simple_dropdown_item_1line, //insertar lista de marcas);
        //adapterListLocalidades = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, //insertar lista de localidades);


        //Pasamos el adapter a las listView
        //TODO: Descomentar las dos siguientes lineas cuando se hayan pasado los elementos los adapters en las lineas 125 y 126
        //listViewMarcasFavDialog.setAdapter(adapterListMarcas);
        //listViewLocalidadFavDialog.setAdapter(adapterListLocalidades);

        // Insertar elementos en el dialogo
        alertDialogBuilder.setView(view);
        alertDialogBuilder.show();

    }
}

class FetcherThread implements Runnable{

    private PresenterGasolinerasFavoritas presenterGasolinerasFavoritas;
    public FetcherThread(PresenterGasolinerasFavoritas presenter){

        this.presenterGasolinerasFavoritas = presenter;
    }

    @Override
    public void run() {
        presenterGasolinerasFavoritas.cargaGasolineras();
    }
}
