package com.isunican.proyectobase.Views;

import com.google.android.material.navigation.NavigationView;
import com.isunican.proyectobase.Database.AppDatabase;
import com.isunican.proyectobase.Presenter.*;
import com.isunican.proyectobase.Model.*;
import com.isunican.proyectobase.R;
import com.isunican.proyectobase.Utilities.ExtractorMarcasUtil;
import com.isunican.proyectobase.Utilities.CommonUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;


import java.util.ArrayList;
import java.util.List;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
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
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    PresenterGasolineras presenterGasolineras;
    PresenterTarjetaDescuento presenterTarjetaDescuento;
    //Codigo sucio
    PresenterFiltroMarcas presenterFiltroMarcas;

    List<Gasolinera>listaGasolinerasActual;
    List<Gasolinera>listaGasolinerasDAO;
    //Lista con el filtro aplicado
    List<Gasolinera> currentList;

    // Vista de lista y adaptador para cargar datos en ella
    ListView listViewGasolineras;
    ArrayAdapter<Gasolinera> adapter;

    // Barra de progreso circular para mostar progeso de carga
    ProgressBar progressBar;

    // Swipe and refresh (para recargar la lista con un swipe)
    SwipeRefreshLayout mSwipeRefreshLayout;

    // Sidebar
    NavigationView navigationView;

    //ActionBarDrawerToggle
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;

    //Adapter para la listView
    ArrayAdapter<String> dataAdapter;

    EditText editTextPrecioMax;
    Spinner spinnerPrecioMax;
    //Filtro
    String tipoGasolina;

    // Boton guardar
    private static final int BTN_POSITIVO = DialogInterface.BUTTON_POSITIVE;

    /**
     * onCreate
     * <p>
     * Crea los elementos que conforman la actividad
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigationView = findViewById(R.id.nav_view_main);
        navigationView.setNavigationItemSelectedListener(this);
        drawerLayout = findViewById(R.id.activity_precio_gasolina_drawer);

        currentList = new ArrayList<>();

        this.presenterGasolineras = new PresenterGasolineras();
        this.presenterTarjetaDescuento = PresenterTarjetaDescuento.getInstance();
        //Esto impide que no se carge ese método para los tests(Hay otras soluciones, pero esta es la mas rapida)
        List<TarjetaDescuento> tarjetas = presenterTarjetaDescuento.getListaDeTarjetasDelUsuario();


        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Muestra el logo en el actionBar
        getSupportActionBar().setIcon(R.drawable.por_defecto_mod);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //ActionBarDrawerToggle
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Swipe and refresh
        // Al hacer swipe en la lista, lanza la tarea asíncrona de carga de datos
        mSwipeRefreshLayout = findViewById(R.id.swiperefresh);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                CargaDatosGasolinerasTask hilo = new CargaDatosGasolinerasTask(MainActivity.this);
                Thread t = new Thread(hilo);
                t.start();
                hilo.onPostExecute(true);
            }
        });

        // Al terminar de inicializar todas las variables
        // se lanza una tarea para cargar los datos de las gasolineras
        // Esto se ha de hacer en segundo plano definiendo una tarea asíncrona
        CargaDatosGasolinerasTask hilo = new CargaDatosGasolinerasTask(MainActivity.this);
        Thread t = new Thread(hilo);
        t.start();
        try {
            t.join();
            hilo.onPostExecute(true);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

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
     * <p>
     * Redefine métodos para el uso de un menú de tipo action bar.
     * <p>
     * onCreateOptionsMenu
     * Carga las opciones del menú a partir del fichero de recursos menu/menu.xml
     * <p>
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
        if(toggle.onOptionsItemSelected(item)){
            return true;
        }
        if(item.getItemId()==R.id.itemActualizar){
            mSwipeRefreshLayout.setRefreshing(true);
            CargaDatosGasolinerasTask hilo = new CargaDatosGasolinerasTask(MainActivity.this);
            Thread t = new Thread(hilo);
            t.start();
            hilo.onPostExecute(true);
        }else if(item.getItemId() == R.id.itemInfo) {
            Intent myIntent = new Intent(MainActivity.this, InfoActivity.class);
            MainActivity.this.startActivity(myIntent);
        }else if(toggle.onOptionsItemSelected(item)) {
            return false;
        }else if(item.getItemId() == R.id.button_test_filtroMarca){
            creaAlertDialogFiltroMarca();
        }else if(item.getItemId() == R.id.button_test_filtroTipoGasolina){
            creaVentanaFiltroTipoGasolina();
        }else if(item.getItemId()==R.id.button_test_anhadeTarjetaDescuento){
            creaVentanaAnhadirTarjetaDescuento();
        }else if(item.getItemId() == R.id.button_test_filtroPrecioMaximo){
            creaVentanaFiltroPrecio();
        }
        return true;
    }
    
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.filtroTipoGasolina:
                creaVentanaFiltroTipoGasolina();
                break;
            case R.id.filtroMarcaGasolinera:
                creaAlertDialogFiltroMarca();
                break;
            case R.id.itemNuevaTarjetaDescuento:
                creaVentanaAnhadirTarjetaDescuento();
                break;
            case R.id.filtarPrecioMaximo:
                creaVentanaFiltroPrecio();
                break;
            case R.id.filtarGasolinerasFavoritas:
                if(AppDatabase.getInstance(this).gasolineraFavoritaDAO().getAll().isEmpty())
                {
                    creaVentanaFavoritosVacios();
                } else {
                    Intent favIntent = new Intent(MainActivity.this, FiltroFavoritosActivity.class);
                    startActivity(favIntent);
                }
                break;
            default:
                Log.d("MIGUEL", "Default en switch");
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }

    /**
     * Ventana emergente que filtra las gasolineras por el precio maximo
     * @Autor Carolay Corales
     */

    public void creaVentanaFiltroPrecio(){

        // Definidos Inflater y View
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_filtro_preciomax, null);

        //Definidos los elementos
        spinnerPrecioMax = view.findViewById(R.id.spinnerFiltroPrecio);
        editTextPrecioMax = view.findViewById(R.id.textNumberPrecioMax);


        //Adapter para el spinner
        final ArrayAdapter<CharSequence> adpSpinner = ArrayAdapter.createFromResource(this,
                R.array.tipos_gasolinas, android.R.layout.simple_spinner_item);

        adpSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPrecioMax.setAdapter(adpSpinner);

        // Creacion alertDialog
        final AlertDialog alertDialogFiltroPrecio = new AlertDialog.Builder(this)
                .setPositiveButton(getResources().getString(R.string.aceptar),null)
                .setNegativeButton(getResources().getString(R.string.cancelar), null)
                .setCancelable(false) //Impide que el dialogo se cierre si pulsas fuera del dialogo
                .create();



        // Definicion positive button
        alertDialogFiltroPrecio.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button bpm = alertDialogFiltroPrecio.getButton(BTN_POSITIVO);
                bpm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Error si se hay mas de un punto, coma o un punto y coma a la vez
                        if (!caracterValidos(editTextPrecioMax.getText().toString().replace(",",".")) || (editTextPrecioMax.getText().toString().length() == 1 && editTextPrecioMax.getText().toString().replace(",",".").equals("."))) {
                            editTextPrecioMax.setError(getResources().getString(R.string.mensaje_error_pmaxCarcaterNovalido));

                        //Error si el campo precio esta vacio o es 0
                        }else if(editTextPrecioMax.getText().toString().isEmpty()){
                            editTextPrecioMax.setError(getResources().getString(R.string.mensaje_error_pmaxVacio));
                        } else if(Double.parseDouble(editTextPrecioMax.getText().toString().replace(",",".")) <= 0 ){
                            editTextPrecioMax.setError(getResources().getString(R.string.mensaje_error_pmaxCero));

                        }else{
                            double precio=Double.parseDouble(editTextPrecioMax.getText().toString().replace(",","."));
                            String tipo =spinnerPrecioMax.getSelectedItem().toString();
                            try{
                                currentList = presenterGasolineras.filtrarGasolineraPorPrecioMaximo(tipo, listaGasolinerasActual,precio);
                                if(currentList.size() == 0){
                                    //Opcion de cerrar el teclado cuando sale el dialogo de informacion
                                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                                    imm.hideSoftInputFromWindow(editTextPrecioMax.getWindowToken(), 0);

                                    //Ventana emergente informativa
                                    creaVentanaInformativa();
                                }else{
                                    adapter = new GasolineraArrayAdapter(MainActivity.this, 0, currentList);
                                    listViewGasolineras = findViewById(R.id.listViewGasolineras);
                                    listViewGasolineras.setAdapter(adapter);
                                    alertDialogFiltroPrecio.dismiss();

                                    //Mensaje de datos filtrados
                                    Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.toast_filtro_aplicado), Toast.LENGTH_LONG);
                                    toast.show();
                                }
                            }catch(NullPointerException e) {
                                Toast.makeText(getApplicationContext(), "Error al al leer gasolineras", Toast.LENGTH_LONG);
                            }
                        }



                    }
                });
            }
        });

        //Insertar elementos en el dialogo
        alertDialogFiltroPrecio.setView(view);
        alertDialogFiltroPrecio.show();
    }

    /**
     * Metodo que comprueba si el caracter introducido  en el campo delprecio de filtro por
     * precio maximo es valido
     * @autor Carolay Corales
     * @param textoPrecio
     * @return true si el caracter no contiene mas de un punto, coma o que no contenga un punto y coma
     * en el mismo string
     */
    public boolean caracterValidos(String textoPrecio){
        //Numero de puntos en el string
        int numPuntos = contarCaracteres(textoPrecio, '.');
        //Numero de comas en el string
        int numComas =  contarCaracteres(textoPrecio, ',');
        //Si hay un punto y coma en el mismo string
        int numPuntoComa = numPuntos + numComas;

        if (numPuntos > 1 || numComas > 1 || numPuntoComa >=2) {
            return false;
        }
        return true;
    }

    /**
     * Metodo que cuenta el numero de veces que aparece un caracter en un string
     * @Autor Carolay Corales
     * @param precio
     * @param caracter
     * @return
     */
    public int contarCaracteres(String precio, char caracter) {
        int posicion, contador = 0;
        posicion = precio.indexOf(caracter);
        while (posicion != -1) {
            contador++;
            posicion = precio.indexOf(caracter, posicion + 1);
        }
        return contador;
    }


    /**
     * Ventana que muestra un mensaje informativo
     * @Autor: Carolay Corales
     */
    public void creaVentanaInformativa(){
        //Crea el alertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.mensaje_info_pmax);
        builder.setPositiveButton(R.string.ok, null);
        //No permite cerrar la ventana si pulsas fuera del dialogo
        builder.setCancelable(false);
        builder.create();
        builder.show();
    }

    /*
     * Ventana de dialogo con un formulario para anhadir una tarjeta
     * de descuento.
     */
    public void creaVentanaAnhadirTarjetaDescuento(){

        // Creacion alertDialog
        final AlertDialog alertDialogBuilder = new AlertDialog.Builder(this)
                .setPositiveButton(getResources().getString(R.string.guardar),null)
                .setNegativeButton(getResources().getString(R.string.cancelar), null)
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
        ArrayAdapter<String> adapterTipoDescuento = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, datosTipoDescuento);
        adapterTipoDescuento.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnTipoDescuento.setAdapter(adapterTipoDescuento);

        // Datos spinner de marcas
        List<String> datosMarcas = ExtractorMarcasUtil.extraeMarcas(presenterGasolineras.getGasolineras());
        datosMarcas = CommonUtils.sortStringList(datosMarcas);
        datosMarcas.add(0,getResources().getString(R.string.default_brand));
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, datosMarcas);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnMarca.setAdapter(adapter2);

        // Definicion positive button ("guardar")
        alertDialogBuilder.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button b = alertDialogBuilder.getButton(BTN_POSITIVO);
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


    /*
     * Ventana de dialogo para filtrar gasolineras por marca
     */
    public void creaAlertDialogFiltroMarca(){
        presenterFiltroMarcas = new PresenterFiltroMarcas(presenterGasolineras.getGasolineras());
        // Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_filtro_marca_acivity, null);

        // List elements
        final EditText marcaTxt = view.findViewById(R.id.txtMarca);
        final ListView marcaListView = view.findViewById(R.id.list_marcas);

        //Create alertDialog
        final AlertDialog alertDialogBuilder = new AlertDialog.Builder(this)
                //Set to null. We override the onclick
                .setPositiveButton(getResources().getString(R.string.aceptar), null)
                .setNegativeButton(getResources().getString(R.string.cancelar), null)
                .create();

        // Create list elements with an array adapter
        dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, presenterFiltroMarcas.getMarcas());

        marcaListView.setAdapter(dataAdapter);

        // Positive button
        alertDialogBuilder.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {

                Button b = alertDialogBuilder.getButton(BTN_POSITIVO);
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        ArrayList<String> listaAux = (ArrayList)presenterFiltroMarcas.getMarcas();

                        if(marcaTxt.getText().toString().isEmpty()){

                            marcaTxt.setError(getResources().getString(R.string.campo_vacio));

                        }else if(!listaAux.contains(marcaTxt.getText().toString())){
                            marcaTxt.setError(getResources().getString(R.string.marca_invalida));
                        }
                        else{
                            //Actualiza la lista actual para solo contener las gasolineras con la marca seleccionada
                            currentList= presenterFiltroMarcas.filtraGasolineras(marcaTxt.getText().toString());
                            adapter = new GasolineraArrayAdapter(MainActivity.this, 0, currentList);
                            listViewGasolineras = findViewById(R.id.listViewGasolineras);
                            listViewGasolineras.setAdapter(adapter);

                            Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.toast_filtro_aplicado), Toast.LENGTH_LONG);
                            toast.show();
                            alertDialogBuilder.dismiss();
                        }
                    }
                });
            }
        });

        marcaTxt.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //No se implementa porque en este caso no se necesita.
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //No se implementa porque en este caso no se necesita.
            }

            @Override
            public void afterTextChanged(Editable s) {dataAdapter.getFilter().filter(s);
            }
        });
        marcaListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                String marca = marcaListView.getItemAtPosition(position).toString();
                marcaTxt.setText(marca);
            }
        });

                // Set elements in the dialog
        alertDialogBuilder.setView(view);
        alertDialogBuilder.show();

    }

    /*
     * Ventana de dialogo para filtrar por tipo de gasolina con un spinner
     * para seleccionar el tipo
     *
     * author: Miguel Carbayo Fernández
     */

    public void creaVentanaFiltroTipoGasolina(){

        // Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.filtros_gasolinera, null);

        // List elements
        final Spinner tipoGasolinaSpinner = view.findViewById(R.id.spinner_tipoGasolina);

        //Create alertDialog
        final AlertDialog alertDialogBuilder = new AlertDialog.Builder(this)
                //.setView(view)
                //Set to null. We override the onclick
                .setPositiveButton(getResources().getString(R.string.aceptar), null)
                .setNegativeButton(getResources().getString(R.string.cancelar), null)
                .setTitle(getResources().getString(R.string.filtrar_tipoGasolina))
                .create();

        // Create list elements with an array adapter
        String[] datos = new String[] {"Gasolina95", "Diesel"};
        ArrayAdapter<String> adapterFiltroGasolina = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, datos);
        adapterFiltroGasolina.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tipoGasolinaSpinner.setAdapter(adapterFiltroGasolina);

        //Positive button
        alertDialogBuilder.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button b = alertDialogBuilder.getButton(BTN_POSITIVO);
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String strTipoGasolina = tipoGasolinaSpinner.getSelectedItem().toString();
                        List<Gasolinera> gasolinerasFiltradas=null;
                        try{
                            gasolinerasFiltradas = presenterGasolineras.filtraGasolinerasTipoCombustible(strTipoGasolina, listaGasolinerasActual);
                        }catch(NullPointerException e){
                            Toast.makeText(getApplicationContext(),"Error al al leer gasolineras",Toast.LENGTH_LONG);
                        }

                        refreshAdapter(gasolinerasFiltradas);
                        Toast.makeText(getApplicationContext(), strTipoGasolina, Toast.LENGTH_LONG).show();
                        alertDialogBuilder.dismiss();
                    }
                });
            }
        });

        // Set elements in the dialog
        alertDialogBuilder.setView(view);
        alertDialogBuilder.show();
    }

    /**
     *Crea la ventana emergente que avisa al usuario de que la lista de favoritos esta
     *vacia, se puede cerrar pulsando en aceptar o fuera de la propia ventana
     */
    public void creaVentanaFavoritosVacios(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.fav_vacios);
        builder.setPositiveButton(getResources().getString(R.string.aceptar), null);

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private void refreshAdapter(List<Gasolinera> gasolinerasNuevas){
        adapter.clear();
        adapter.addAll(gasolinerasNuevas);
    }

    private void updateListWithNewDiscountCard(){
        //Esto tiene que cambiar cuando se haga la historia de ver tarjetas de descuento porque tenemos que usar solo una tarjeta de desucento al tiempo
        listaGasolinerasActual = presenterTarjetaDescuento.actualizarListaDePrecios(listaGasolinerasActual);
        adapter.notifyDataSetChanged();
    }

    /**
     * CargaDatosGasolinerasTask
     * <p>
     * Tarea asincrona para obtener los datos de las gasolineras
     * en segundo plano.
     * <p>
     * Redefinimos varios métodos que se ejecutan en el siguiente orden:
     * onPreExecute: activamos el dialogo de progreso
     * doInBackground: solicitamos que el presenter cargue los datos
     * onPostExecute: desactiva el dialogo de progreso,
     * muestra las gasolineras en formato lista (a partir de un adapter)
     * y define la acción al realizar al seleccionar alguna de ellas
     * <p>
     * http://www.sgoliver.net/blog/tareas-en-segundo-plano-en-android-i-thread-y-asynctask/
     */
    private class CargaDatosGasolinerasTask implements Runnable {

        Activity activity;

        /**
         * Constructor del hilo
         *
         * @param activity
         */
        public CargaDatosGasolinerasTask(Activity activity) {
            this.activity = activity;
        }

        /**
         * run
         * <p>
         * Tarea ejecutada en segundo plano
         * Llama al presenter para que lance el método de carga de los datos de las gasolineras
         * */
        @Override
        public void run() {
            presenterGasolineras.cargaDatosGasolineras();
        }

        /**
         * onPostExecute
         * <p>
         * Se ejecuta al finalizar run
         * Oculta el diálogo de progreso.
         * Muestra en una lista los datos de las gasolineras cargadas,
         * creando un adapter y pasándoselo a la lista.
         * Define el manejo de la selección de los elementos de la lista,
         * lanzando con una intent una actividad de detalle
         * a la que pasamos un objeto Gasolinera
         *
         * @param res
         */
        protected void onPostExecute(Boolean res) {
            listaGasolinerasActual=presenterGasolineras.getGasolineras();
            currentList = presenterGasolineras.getGasolineras();

            listaGasolinerasDAO = AppDatabase.getInstance(getApplicationContext()).gasolineraDAO().getAll();
            Toast toast;

            mSwipeRefreshLayout.setRefreshing(false);

            // Si se ha obtenido resultado en la tarea en segundo plano
            if (Boolean.TRUE.equals(res)) {
                // Definimos el array adapter
                adapter = new GasolineraArrayAdapter(activity, 0, presenterGasolineras.getGasolineras());
                //Jaime ha estado aquí, actualizar  las gasolineras si ha cambiado el precio
                for (Gasolinera gDao : listaGasolinerasDAO) {
                    for (Gasolinera g : listaGasolinerasActual) {
                        if (gDao.equals(g) && gDao.getGasolina95() != g.getGasolina95() && gDao.getGasoleoA() != g.getGasoleoA()) {
                            gDao.setGasoleoA(g.getGasoleoA());
                            gDao.setGasolina95(g.getGasolina95());
                            AppDatabase.getInstance(getApplicationContext()).gasolineraDAO().update(gDao);
                        }
                    }

                }
                adapter = new GasolineraArrayAdapter(activity, 0, listaGasolinerasActual);

                // Obtenemos la vista de la lista
                listViewGasolineras = findViewById(R.id.listViewGasolineras);

                // Cargamos los datos en la lista
                if (!listaGasolinerasActual.isEmpty()) {
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
                     */
                    Intent myIntent = new Intent(MainActivity.this, DetailActivity.class);
                    myIntent.putExtra(getResources().getString(R.string.pasoDatosGasolinera),
                            currentList.get(position));
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
        public View getView(final int position, View convertView, ViewGroup parent) {

            // Obtiene el elemento que se está mostrando
            final Gasolinera gasolinera = listaGasolineras.get(position);

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
            gasoleoA.setText(" " + gasolinera.getGasoleoA() + getResources().getString(R.string.moneda));
            gasolina95.setText(" " + gasolinera.getGasolina95() + getResources().getString(R.string.moneda));

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
                imageID = context.getResources().getIdentifier(getResources().getString(R.string.pordefecto),
                        "drawable", context.getPackageName());
            }
            logo.setImageResource(imageID);
        }
    }
}
