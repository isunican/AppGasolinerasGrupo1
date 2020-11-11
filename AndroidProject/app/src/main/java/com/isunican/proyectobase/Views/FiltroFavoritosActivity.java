package com.isunican.proyectobase.Views;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.isunican.proyectobase.Presenter.PresenterFiltroMarcas;
import com.isunican.proyectobase.Presenter.PresenterGasolineras;
import com.isunican.proyectobase.Presenter.PresenterGasolinerasFavoritas;
import com.isunican.proyectobase.R;
import androidx.appcompat.app.AppCompatActivity;


public class FiltroFavoritosActivity extends AppCompatActivity  {

    private static final int BTN_POSITIVO = DialogInterface.BUTTON_POSITIVE;

    // Vista de lista y adaptador para cargar datos en ella
    ListView listViewFav;
    PresenterGasolinerasFavoritas presenterGasolinerasFavoritas;
    //ArrayAdapter<Gasolinera> adapter;



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
        //Titulo en el actionBar
        this.setTitle(R.string.title_fav);

        presenterGasolinerasFavoritas = new PresenterGasolinerasFavoritas(this.getApplicationContext());
        FetcherThread hilo = new FetcherThread(presenterGasolinerasFavoritas);
        new Thread(hilo).start();

        // muestra el logo en el actionBar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.por_defecto_mod);


        //Inserta lista de gasolineras en la listView
        listViewFav = findViewById(R.id.listViewGasolineras);
        //listViewFav.setAdapter(adap);
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

    public void creaDialogoFiltroFav(){
        //Lista de elementos
        EditText txtMarcaFav = findViewById(R.id.textMarca);
        ListView listaOpcionesMarca = findViewById(R.id.listaMarcasFav);
        EditText txtLocalidaFav = findViewById(R.id.textLocalidad);
        ListView listaOpcionesLocalidades = findViewById(R.id.listaLocalidadesFav);

        // Definidos Inflater y View
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_dialog_fav, null);

        // TODO: Crear lista de adapter con los elementos para lista de marcas
        // TODO: Crear lista de adapter con los elementos para lista de localidades

        //Crea AlertDialog
        final AlertDialog alertDialogBuilder = new AlertDialog.Builder(this)
                //Set to null. We override the onclick
                .setPositiveButton(android.R.string.ok, null)
                .setNegativeButton(android.R.string.cancel, null)
                .create();

        //Implementa funcion del boton aceptar
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
