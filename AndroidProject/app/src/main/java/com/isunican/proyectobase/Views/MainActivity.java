package com.isunican.proyectobase.Views;

import com.google.android.material.navigation.NavigationView;
import com.isunican.proyectobase.Presenter.*;
import com.isunican.proyectobase.Model.*;
import com.isunican.proyectobase.R;
import com.isunican.proyectobase.Utilities.BrandExtractorUtil;
import com.isunican.proyectobase.Utilities.CommonUtils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import java.util.ArrayList;
import java.util.List;

import android.util.Log;
import android.view.Gravity;
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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


/*
------------------------------------------------------------------
    Vista principal

    Presenta los datos de las gasolineras en formato lista.

------------------------------------------------------------------
*/
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    PresenterGasolineras presenterGasolineras;
    PresenterTarjetaDescuento presenterTarjetaDescuento;

    List<Gasolinera> gasolinerasActuales;

    // Vista de lista y adaptador para cargar datos en ella
    ListView listViewGasolineras;
    ArrayAdapter<Gasolinera> adapter;

    // Barra de progreso circular para mostar progeso de carga
    ProgressBar progressBar;

    // Swipe and refresh (para recargar la lista con un swipe)
    SwipeRefreshLayout mSwipeRefreshLayout;

    // Sidebar
    RelativeLayout layout;
    DrawerLayout drawerLayout;

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
        setContentView(R.layout.activity_main);
        setNavigationViewListener();
        drawerLayout = findViewById(R.id.activity_precio_gasolina_drawer);

        this.presenterGasolineras = new PresenterGasolineras();
        this.presenterTarjetaDescuento = new PresenterTarjetaDescuento();

        // Barra de progreso
        // https://materialdoc.com/components/progress/
        progressBar = new ProgressBar(MainActivity.this,null,android.R.attr.progressBarStyleLarge);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(100,100);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        layout = findViewById(R.id.activity_precio_gasolina);
        layout.addView(progressBar,params);

        // Muestra el logo en el actionBar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.por_defecto_mod);

        // Swipe and refresh
        // Al hacer swipe en la lista, lanza la tarea asíncrona de carga de datos
        mSwipeRefreshLayout = findViewById(R.id.swiperefresh);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new CargaDatosGasolinerasTask(MainActivity.this).execute();
            }
        });

        // Al terminar de inicializar todas las variables
        // se lanza una tarea para cargar los datos de las gasolineras
        // Esto se ha de hacer en segundo plano definiendo una tarea asíncrona
        new CargaDatosGasolinerasTask(this).execute();

        Button test_anhadeTarjetaDescuento = findViewById(R.id.button_test_anhadeTarjetaDescuento);
        test_anhadeTarjetaDescuento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                creaVentanaAnhadirTarjetaDescuento();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    /**
     * Menú action bar
     *
     * Redefine métodos para el uso de un menú de tipo action bar.
     *
     * onCreateOptionsMenu
     * Carga las opciones del menú a partir del fichero de recursos menu/menu.xml
     *
     * onOptionsItemSelected
     * Define las respuestas a las distintas opciones del menú
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.itemActualizar){
            mSwipeRefreshLayout.setRefreshing(true);
            new CargaDatosGasolinerasTask(this).execute();
        }
        else if(item.getItemId()==R.id.itemInfo){
            Intent myIntent = new Intent(MainActivity.this, InfoActivity.class);
            MainActivity.this.startActivity(myIntent);
        }
        return true;
    }
    
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.filtroTipoGasolina:
                FiltrosActivity filtro = new FiltrosActivity();
                filtro.show(getSupportFragmentManager(), "Dialog");
                break;


            case R.id.itemNuevaTarjetaDescuento:
                creaVentanaAnhadirTarjetaDescuento();
                break;

            default:
                Log.d("MIGUEL", "Default en switch");
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }

    /*
     * Ventana de dialogo con un formulario para anhadir una tarjeta
     * de descuento.
     */
    public void creaVentanaAnhadirTarjetaDescuento(){

        // Creacion alertDialog
        final AlertDialog alertDialogBuilder = new AlertDialog.Builder(this)
                .setPositiveButton(android.R.string.ok,null)
                .setNegativeButton(android.R.string.cancel, null)
                .create();

        LayoutInflater inflater = this.getLayoutInflater();
        final View view = inflater.inflate(R.layout.activity_nueva_tarjeta_descuento, null);

        // Elementos del formulario
        final TextView nombre = view.findViewById(R.id.nombreTarjeta);
        final Spinner spnMarca = view.findViewById(R.id.spnMarcas);
        final Spinner spnTipoDescuento = view.findViewById(R.id.spnTipoDescuento);
        final TextView descuento = view.findViewById(R.id.descuento);
        final TextView comentarios = view.findViewById(R.id.comentarios);

        // Datos spinner de tipo descuento
        String[] datosTipoDescuento = new String[] {getResources().getString(R.string.default_type_discount_card),getResources().getString(R.string.porcentual),
                getResources().getString(R.string.cts_litro)};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, datosTipoDescuento);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnTipoDescuento.setAdapter(adapter);

        // Datos spinner de marcas
        List<String> datosMarcas = BrandExtractorUtil.extractBrands((ArrayList<Gasolinera>) presenterGasolineras.getGasolineras());
        datosMarcas = CommonUtils.sortStringList(datosMarcas);
        datosMarcas.add(0,getResources().getString(R.string.default_brand));
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, datosMarcas);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnMarca.setAdapter(adapter2);

        // Definicion positive button ("guardar")
        alertDialogBuilder.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button b = alertDialogBuilder.getButton(AlertDialog.BUTTON_POSITIVE);
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // lee y almacena datos
                        String strNombre = nombre.getText().toString();
                        String strMarca = spnMarca.getSelectedItem().toString();
                        String strTipoDescuento = spnTipoDescuento.getSelectedItem().toString();
                        String strDescuento = descuento.getText().toString();
                        String strComentario = comentarios.getText().toString();

                        // Si hay algun campo sin rellenar, salta un aviso al usuario
                        if (strNombre.equals("")) {
                            nombre.setError(getResources().getString(R.string.complete_nombre));
                        } else if (strMarca.equals(getResources().getString(R.string.default_brand))) {
                            TextView errorText = (TextView)spnMarca.getSelectedView();
                            errorText.setError("");
                            errorText.setTextColor(Color.RED);
                        } else if (strTipoDescuento.equals(getResources().getString(R.string.default_type_discount_card))) {
                            TextView errorText = (TextView)spnTipoDescuento.getSelectedView();
                            errorText.setError("");
                            errorText.setTextColor(Color.RED);
                        } else if (strDescuento.equals("")) {
                            descuento.setError(getResources().getString(R.string.complete_descuento));
                        } else {
                            if (presenterTarjetaDescuento.anhadirNuevaTarjeta(strNombre, strComentario, strMarca, strTipoDescuento, strDescuento)) {
                                Toast toast = Toast.makeText(getApplicationContext(),
                                        getResources().getString(R.string.tarjeta_descuento_guardada), Toast.LENGTH_LONG);
                                toast.show();
                                updateListWithNewDiscountCard();
                                alertDialogBuilder.dismiss();
                            }
                        }
                    }
                });
            }
        });
        alertDialogBuilder.setView(view);
        alertDialogBuilder.show();
    }

    private void updateListWithNewDiscountCard(){
        //Esto tiene que cambiar cuando se haga la historia de ver tarjetas de descuento porque tenemos que usar solo una tarjeta de desucento al tiempo
        List<Gasolinera> gasolinerasActualesActualizadas = presenterTarjetaDescuento.actualizarListaDePrecios(presenterGasolineras.getGasolineras());
        adapter.clear();
        gasolinerasActuales = gasolinerasActualesActualizadas;
        adapter.addAll(gasolinerasActuales);
        adapter.notifyDataSetChanged();
    }

    private void setNavigationViewListener() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    /**
     * CargaDatosGasolinerasTask
     *
     * Tarea asincrona para obtener los datos de las gasolineras
     * en segundo plano.
     *
     * Redefinimos varios métodos que se ejecutan en el siguiente orden:
     * onPreExecute: activamos el dialogo de progreso
     * doInBackground: solicitamos que el presenter cargue los datos
     * onPostExecute: desactiva el dialogo de progreso,
     *    muestra las gasolineras en formato lista (a partir de un adapter)
     *    y define la acción al realizar al seleccionar alguna de ellas
     *
     * http://www.sgoliver.net/blog/tareas-en-segundo-plano-en-android-i-thread-y-asynctask/
     */
    private class CargaDatosGasolinerasTask extends AsyncTask<Void, Void, Boolean> {

        Activity activity;

        /**
         * Constructor de la tarea asincrona
         * @param activity
         */
        public CargaDatosGasolinerasTask(Activity activity) {
            this.activity = activity;
        }

        /**
         * onPreExecute
         *
         * Metodo ejecutado de forma previa a la ejecucion de la tarea definida en el metodo doInBackground()
         * Muestra un diálogo de progreso
         */
        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);  //To show ProgressBar
        }

        /**
         * doInBackground
         *
         * Tarea ejecutada en segundo plano
         * Llama al presenter para que lance el método de carga de los datos de las gasolineras
         * @param params
         * @return boolean
         */
        @Override
        protected Boolean doInBackground(Void... params) {
            return presenterGasolineras.cargaDatosGasolineras();
        }

        /**
         * onPostExecute
         *
         * Se ejecuta al finalizar doInBackground
         * Oculta el diálogo de progreso.
         * Muestra en una lista los datos de las gasolineras cargadas,
         * creando un adapter y pasándoselo a la lista.
         * Define el manejo de la selección de los elementos de la lista,
         * lanzando con una intent una actividad de detalle
         * a la que pasamos un objeto Gasolinera
         *
         * @param res
         */
        @Override
        protected void onPostExecute(Boolean res) {
            gasolinerasActuales = presenterGasolineras.getGasolineras();
            Toast toast;

            // Si el progressDialog estaba activado, lo oculta
            progressBar.setVisibility(View.GONE);     // To Hide ProgressBar

            mSwipeRefreshLayout.setRefreshing(false);

            // Si se ha obtenido resultado en la tarea en segundo plano
            if (res) {
                // Definimos el array adapter

                adapter = new GasolineraArrayAdapter(activity, 0, (ArrayList<Gasolinera>)gasolinerasActuales);

                // Obtenemos la vista de la lista
                listViewGasolineras = findViewById(R.id.listViewGasolineras);

                // Cargamos los datos en la lista
                if (!gasolinerasActuales.isEmpty()) {
                    // datos obtenidos con exito
                    listViewGasolineras.setAdapter(adapter);
                    toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.datos_exito), Toast.LENGTH_LONG);
                } else {
                    // los datos estan siendo actualizados en el servidor, por lo que no son actualmente accesibles
                    // sucede en torno a las :00 y :30 de cada hora
                    toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.datos_no_accesibles), Toast.LENGTH_LONG);
                }
            } else {
                // error en la obtencion de datos desde el servidor
                toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.datos_no_obtenidos), Toast.LENGTH_LONG);
            }

            // Muestra el mensaje del resultado de la operación en un toast
            if (toast != null) {
                toast.show();
            }

            /*
             * Define el manejo de los eventos de click sobre elementos de la lista
             * En este caso, al pulsar un elemento se lanzará una actividad con una vista de detalle
             * a la que le pasamos el objeto Gasolinera sobre el que se pulsó, para que en el
             * destino tenga todos los datos que necesita para mostrar.
             * Para poder pasar un objeto Gasolinera mediante una intent con putExtra / getExtra,
             * hemos tenido que hacer que el objeto Gasolinera implemente la interfaz Parcelable
             */
            listViewGasolineras.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> a, View v, int position, long id) {

                    /* Obtengo el elemento directamente de su posicion,
                     * ya que es la misma que ocupa en la lista
                     * Alternativa 1: a partir de posicion obtener algun atributo int opcionSeleccionada = ((Gasolinera) a.getItemAtPosition(position)).getIdeess();
                     * Alternativa 2: a partir de la vista obtener algun atributo String opcionSeleccionada = ((TextView)v.findViewById(R.id.textViewRotulo)).getText().toString();
                     */
                    Intent myIntent = new Intent(MainActivity.this, DetailActivity.class);
                    myIntent.putExtra(getResources().getString(R.string.pasoDatosGasolinera),
                            gasolinerasActuales.get(position));
                    MainActivity.this.startActivity(myIntent);

                }
            });
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
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
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
            gasoleoA.setText(" " + gasolinera.getGasoleoA() + getResources().getString(R.string.moneda));
            gasolina95.setText(" " + gasolinera.getGasolina95() + getResources().getString(R.string.moneda));

            // carga icono
            {
                String rotuleImageID = gasolinera.getRotulo().toLowerCase();

                // Tengo que protegerme ante el caso en el que el rotulo solo tiene digitos.
                // En ese caso getIdentifier devuelve esos digitos en vez de 0.
                int imageID = context.getResources().getIdentifier(rotuleImageID,
                        "drawable", context.getPackageName());

                if (imageID == 0 || TextUtils.isDigitsOnly(rotuleImageID)) {
                    imageID = context.getResources().getIdentifier(getResources().getString(R.string.pordefecto),
                            "drawable", context.getPackageName());
                }
                logo.setImageResource(imageID);
            }


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
    }
}