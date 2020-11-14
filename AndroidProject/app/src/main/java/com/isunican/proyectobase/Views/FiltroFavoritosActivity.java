package com.isunican.proyectobase.Views;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.isunican.proyectobase.DAO.GasolineraDAO;
import com.isunican.proyectobase.DAO.GasolineraFavoritaDAO;
import com.isunican.proyectobase.Database.AppDatabase;
import com.isunican.proyectobase.Model.Gasolinera;
import com.isunican.proyectobase.Presenter.PresenterGasolinerasFavoritas;
import com.isunican.proyectobase.R;
import com.isunican.proyectobase.Utilities.ExtractorLocalidadUtil;
import com.isunican.proyectobase.Utilities.ExtractorMarcasUtil;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;


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

    Context contexto;


    PresenterGasolinerasFavoritas presenterGasolinerasFavoritas;

    ArrayList<Gasolinera> currentList;

    //Adapter para lista de marcas y localidades
    ArrayAdapter<String> adapterListMarcas;
    ArrayAdapter<String> adapterListLocalidades;

    //Adapter para la lista de gasolineras favoritas
    ArrayAdapter<Gasolinera> adapterFavoritas;


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

        contexto = this.getApplicationContext();

        //Titulo en el actionBar
        this.setTitle(R.string.title_fav);

        currentList = new ArrayList<>();

        presenterGasolinerasFavoritas = new PresenterGasolinerasFavoritas();
        FetcherThread hilo = new FetcherThread(presenterGasolinerasFavoritas, contexto);
        Thread t = new Thread(hilo);
        t.start();

        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //presenterGasolinerasFavoritas.cargaGasolineras();

        // muestra el logo en el actionBar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.por_defecto_mod);


        currentList = (ArrayList<Gasolinera>) presenterGasolinerasFavoritas.getGasolinerasFavoritas();
        //Adapter al que se le pasa la lista de gasolineras favoritas
        adapterFavoritas = new GasolineraArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, currentList);
        //adapterFavoritas.notifyDataSetChanged();

        //Inserta lista de gasolineras favoritas en la listView
        listViewFav = findViewById(R.id.listFavGasolineras);
        listViewFav.setAdapter(adapterFavoritas);


        listViewFav.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {

                /* Obtengo el elemento directamente de su posicion,
                 * ya que es la misma que ocupa en la lista
                 */
                Intent myIntent = new Intent(FiltroFavoritosActivity.this, DetailActivity.class);
                myIntent.putExtra(getResources().getString(R.string.pasoDatosGasolinera),
                        currentList.get(position));
                FiltroFavoritosActivity.this.startActivity(myIntent);
            }
        });

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
        // Respond to the action bar's Up/Home button
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
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

                        if(textMarcaFavDialog.getText().toString().equals("") && textLocalidadFavDialog.getText().toString().equals("")){
                            currentList = (ArrayList<Gasolinera>) presenterGasolinerasFavoritas.getGasolinerasFavoritas();

                        }else if(textMarcaFavDialog.getText().toString().equals("")){
                            currentList = (ArrayList<Gasolinera>) presenterGasolinerasFavoritas.filtrarGasolinerasFavLocal(textLocalidadFavDialog.getText().toString());
                        }else if(textLocalidadFavDialog.getText().toString().equals("")){
                            currentList = (ArrayList<Gasolinera>) presenterGasolinerasFavoritas.filtrarGasolinerasFavMarca(textMarcaFavDialog.getText().toString());
                        }else{
                            currentList = (ArrayList<Gasolinera>) presenterGasolinerasFavoritas.filtraGasolinerasFavAmbos(textMarcaFavDialog.getText().toString(),textLocalidadFavDialog.getText().toString());
                        }

                        adapterFavoritas = new GasolineraArrayAdapter(FiltroFavoritosActivity.this, 0, currentList);
                        listViewFav.findViewById(R.id.listFavGasolineras);
                        listViewFav.setAdapter(adapterFavoritas);
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
        ArrayList<String> marcasFavoritas = (ArrayList<String>) presenterGasolinerasFavoritas.getMarcasFavoritas();
        ArrayList<String> localidadesFavoritas = (ArrayList<String>) presenterGasolinerasFavoritas.getLocalidadesFavoritas();
        adapterListMarcas = new ArrayAdapter<>(this,  android.R.layout.simple_dropdown_item_1line, marcasFavoritas);
        adapterListLocalidades = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, localidadesFavoritas);


        // Pasamos el adapter a las listView
        listViewMarcasFavDialog.setAdapter(adapterListMarcas);
        listViewLocalidadFavDialog.setAdapter(adapterListLocalidades);


        // ClickListener sobre la lista de marcas
        listViewMarcasFavDialog.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                String marca = listViewMarcasFavDialog.getItemAtPosition(position).toString();
                textMarcaFavDialog.setText(marca);
            }
        });

        // ClickListener sobre la lista de marcas
        listViewLocalidadFavDialog.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                String localidad = listViewLocalidadFavDialog.getItemAtPosition(position).toString();
                textLocalidadFavDialog.setText(localidad);
            }
        });

        // Insertar elementos en el dialogo
        alertDialogBuilder.setView(view);
        alertDialogBuilder.show();

    }
}

/*
   ------------------------------------------------------------------
       GasolineraArrayAdapter

       Adaptador para inyectar los datos de las gasolineras
       en el listview del layout principal de la aplicacion
   ------------------------------------------------------------------
   */
class GasolineraArrayAdapter extends ArrayAdapter<Gasolinera> {

    private Context context;
    private List<Gasolinera> listaGasolineras;

    // Constructor
    public GasolineraArrayAdapter(Context context, int resource, List<Gasolinera> objects) {
        super(context, resource, objects);
        this.context = context;
        this.listaGasolineras = objects;
    }

    // Llamado al renderizar la lista
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Obtiene el elemento que se está mostrando
        Gasolinera gasolinera = listaGasolineras.get(position);

        // Indica el layout a usar en cada elemento de la lista
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_gasolinera, null);

        // Asocia las variables de dicho layout
        ImageView logo = view.findViewById(R.id.imageViewLogo);
        TextView rotulo = view.findViewById(R.id.textViewRotulo);
        TextView direccion = view.findViewById(R.id.textViewDireccion);
        TextView gasoleoA = view.findViewById(R.id.textViewGasoleoA);
        TextView gasolina95 = view.findViewById(R.id.textViewGasolina95);

        // Y carga los datos del item
        rotulo.setText(gasolinera.getRotulo());
        direccion.setText(gasolinera.getDireccion());
        gasoleoA.setText(" " + gasolinera.getGasoleoA() + "€");
        gasolina95.setText(" " + gasolinera.getGasolina95() + "€");

        // carga icono
        cargaIcono(gasolinera, logo);

        // Si las dimensiones de la pantalla son menores
        // reducimos el texto de las etiquetas para que se vea correctamente
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        if (displayMetrics.widthPixels < 720) {
            TextView tv = view.findViewById(R.id.textViewGasoleoALabel);
            RelativeLayout.LayoutParams params = ((RelativeLayout.LayoutParams) tv.getLayoutParams());
            params.setMargins(15, 0, 0, 0);
            tv.setTextSize(11);
            TextView tmp;
            tmp = view.findViewById(R.id.textViewGasolina95Label);
            tmp.setTextSize(11);
            tmp = view.findViewById(R.id.textViewGasoleoA);
            tmp.setTextSize(11);
            tmp = view.findViewById(R.id.textViewGasolina95);
            tmp.setTextSize(11);
        }
        return view;
    }

    private void cargaIcono(Gasolinera gasolinera, ImageView logo) {
        String rotuleImageID = gasolinera.getRotulo().toLowerCase();

        // Tengo que protegerme ante el caso en el que el rotulo solo tiene digitos.
        // En ese caso getIdentifier devuelve esos digitos en vez de 0.
        int imageID = context.getResources().getIdentifier(rotuleImageID,
                "drawable", context.getPackageName());

        if (imageID == 0 || TextUtils.isDigitsOnly(rotuleImageID)) {
            imageID = context.getResources().getIdentifier("por_defecto",
                    "drawable", context.getPackageName());
        }
        logo.setImageResource(imageID);
    }
}

/**
*Clase que carga los datos de las gasolineras
 */
class FetcherThread implements Runnable{

    private PresenterGasolinerasFavoritas presenterGasolinerasFavoritas;
    private Context contexto;

    public FetcherThread(PresenterGasolinerasFavoritas presenter, Context contexto){

        this.presenterGasolinerasFavoritas = presenter;
        this.contexto = contexto;

    }

    @Override
    public void run() {
        GasolineraFavoritaDAO gasolineraFavoritaDAO = AppDatabase.getInstance(contexto).gasolineraFavoritaDAO();
        GasolineraDAO gasolineraDAO = AppDatabase.getInstance(contexto).gasolineraDAO();
        presenterGasolinerasFavoritas.cargaGasolineras(gasolineraDAO, gasolineraFavoritaDAO);
    }
}
