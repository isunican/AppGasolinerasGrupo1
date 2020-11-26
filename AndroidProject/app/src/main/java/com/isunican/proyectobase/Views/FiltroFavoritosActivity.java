package com.isunican.proyectobase.Views;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import com.isunican.proyectobase.Model.GasolineraFavorita;
import com.isunican.proyectobase.Presenter.PresenterGasolineras;
import com.isunican.proyectobase.Presenter.PresenterGasolinerasFavoritas;
import com.isunican.proyectobase.Presenter.PresenterTarjetaDescuento;
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
    PresenterTarjetaDescuento presenterTarjetaDescuento = PresenterTarjetaDescuento.getInstance();
    //Contexto de la aplicación
    Context contexto;
    //Presenter que gestiona las gasolineras
    PresenterGasolineras presenterGasolineras;
    //Presenter que gestiona las gasolineras favoritas
    PresenterGasolinerasFavoritas presenterGasolinerasFavoritas;
    //Lista de las gasolineras favoritas actuales
    List<Gasolinera> listaActual;

    // Elemento de la activity. ListView que contendra las gasolineras favoritas
    ListView listViewFav;

    //Elementos del dialogo de filtro fav
    ListView listViewMarcasFavDialog;
    ListView listViewLocalidadFavDialog;
    EditText textMarcaFavDialog;
    EditText textLocalidadFavDialog;

    //Adapter para lista de marcas y localidades
    ArrayAdapter<String> adapterListMarcas;
    ArrayAdapter<String> adapterListLocalidades;

    //Adapter para la lista de gasolineras favoritas
    ArrayAdapter<Gasolinera> adapterFavoritas;

    // Caja de anhadir/modifica comentario
    TextView comentarioEditText;
    Gasolinera gasolineraAModificar;


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

        listaActual = new ArrayList<>();

        presenterGasolineras = new PresenterGasolineras();
        presenterGasolinerasFavoritas = new PresenterGasolinerasFavoritas();
        FetcherThread hilo = new FetcherThread(presenterGasolinerasFavoritas, contexto);
        Thread t = new Thread(hilo);
        t.start();

        try {
            t.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // muestra el logo en el actionBar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.por_defecto_mod);

        listaActual = presenterGasolinerasFavoritas.getGasolinerasFavoritas();
        listaActual = presenterTarjetaDescuento.actualizarListaDePrecios(listaActual);
        //Adapter al que se le pasa la lista de gasolineras favoritas
        adapterFavoritas = new GasolineraArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, listaActual);

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
                        listaActual.get(position));
                FiltroFavoritosActivity.this.startActivity(myIntent);
            }
        });
    }

    /**
     * Clase privada que implementa la clase TextWatcher
     */
    private class TextChange  implements TextWatcher{
        View view;
        //Constructor
        private TextChange (View v) {
            view = v;
        }//end constructor
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {//No se implementa
        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {//No se implementa
        }
        @Override
        public void afterTextChanged(Editable s) {
            ArrayList<Gasolinera> filtradas;
            switch (view.getId()){
                case R.id.textMarcaFavDialog:
                    adapterListMarcas.getFilter().filter(s);
                    filtradas = (ArrayList<Gasolinera>) ExtractorMarcasUtil.aplicaFiltro(textMarcaFavDialog.getText().toString(), listaActual);
                    ArrayList<String> localidades = (ArrayList<String>) ExtractorLocalidadUtil.extraeLocalidades(filtradas);
                    ArrayAdapter<String> newLocalidades = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, localidades);
                    listViewLocalidadFavDialog.setAdapter(newLocalidades);
                    break;
                case R.id.textLocalidadFavDialog:
                    adapterListLocalidades.getFilter().filter(s);
                    filtradas = (ArrayList<Gasolinera>) ExtractorLocalidadUtil.aplicaFiltro(textLocalidadFavDialog.getText().toString(), listaActual);
                    ArrayList<String> marcas = (ArrayList<String>) ExtractorMarcasUtil.extraeMarcas(filtradas);
                    ArrayAdapter<String> newMarcas = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, marcas);
                    listViewMarcasFavDialog.setAdapter(newMarcas);
                    break;
                default:
            }
        }
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
     *
     * Crea dialogo de confirmación para eliminar la gasolinera al pulsar el boton de eliminar
     *
     * @param gasolinera
     * @author Jaime López-Agudo Higuera
     */
    public void creaDialogoConfirmacion(final Gasolinera gasolinera) {
        final AlertDialog alertDialogConfirmacion = new AlertDialog.Builder(this)
                .setPositiveButton(getResources().getString(R.string.aplicar), null)
                .setNegativeButton(getResources().getString(R.string.cancelar), null)
                .create();
        LayoutInflater inflater=this.getLayoutInflater();
        final View view =inflater.inflate(R.layout.confirmacion_elimina_favorito, null);
        final TextView txtConfirmacion = view.findViewById(R.id.txt_confirmacion);
        txtConfirmacion.setText("¿Quiere eliminar la gasolinera " + gasolinera.getRotulo() + " de su lista de favoritos?");
        alertDialogConfirmacion.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button btnAceptar = alertDialogConfirmacion.getButton(BTN_POSITIVO);
                btnAceptar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RemoverThread hilo = new RemoverThread(gasolinera, getApplicationContext(), presenterGasolinerasFavoritas);
                        new Thread(hilo).start();
                        Toast.makeText(getApplicationContext(), "gasolinera eliminada", Toast.LENGTH_LONG).show();
                        adapterFavoritas.remove(gasolinera);
                        alertDialogConfirmacion.dismiss();
                    }

                });
            }
        });
        alertDialogConfirmacion.setView(view);
        alertDialogConfirmacion.show();
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

        //Adapters al que se les pasa la lista de marcas y localidades
        final ArrayList<String> marcasFavoritas = (ArrayList<String>) presenterGasolinerasFavoritas.getMarcasFavoritas();
        final ArrayList<String> localidadesFavoritas = (ArrayList<String>) presenterGasolinerasFavoritas.getLocalidadesFavoritas();
        adapterListMarcas = new ArrayAdapter<>(this,  android.R.layout.simple_dropdown_item_1line, marcasFavoritas);
        adapterListLocalidades = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, localidadesFavoritas);

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

                        if(!marcasFavoritas.contains(textMarcaFavDialog.getText().toString()) && !textMarcaFavDialog.getText().toString().isEmpty()) {
                            textMarcaFavDialog.setError(getResources().getString(R.string.marca_invalida));
                        }
                        else if(!localidadesFavoritas.contains(textLocalidadFavDialog.getText().toString()) && !textLocalidadFavDialog.getText().toString().isEmpty()){
                            textLocalidadFavDialog.setError(getResources().getString(R.string.localidad_invalida));
                        }
                        else {
                            if (textMarcaFavDialog.getText().toString().equals("") && textLocalidadFavDialog.getText().toString().equals("")) {
                                listaActual = (ArrayList<Gasolinera>) presenterGasolinerasFavoritas.getGasolinerasFavoritas();

                            } else if (textMarcaFavDialog.getText().toString().equals("")) {
                                listaActual = (ArrayList<Gasolinera>) presenterGasolinerasFavoritas.filtrarGasolinerasFavLocal(textLocalidadFavDialog.getText().toString());
                            } else if (textLocalidadFavDialog.getText().toString().equals("")) {
                                listaActual = (ArrayList<Gasolinera>) presenterGasolinerasFavoritas.filtrarGasolinerasFavMarca(textMarcaFavDialog.getText().toString());
                            } else {
                                listaActual = (ArrayList<Gasolinera>) presenterGasolinerasFavoritas.filtraGasolinerasFavAmbos(textMarcaFavDialog.getText().toString(), textLocalidadFavDialog.getText().toString());
                            }

                            adapterFavoritas = new GasolineraArrayAdapter(FiltroFavoritosActivity.this, 0, listaActual);
                            listViewFav.findViewById(R.id.listFavGasolineras);
                            listViewFav.setAdapter(adapterFavoritas);
                            //Mensaje de datos filtrados
                            Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.toast_filtro_aplicado), Toast.LENGTH_LONG);
                            toast.show();
                            //Cierra el dialogo
                            alertDialogBuilder.dismiss();
                        }
                    }
                });
            }
        });




        // Pasamos el adapter a las listView
        listViewMarcasFavDialog.setAdapter(adapterListMarcas);
        listViewLocalidadFavDialog.setAdapter(adapterListLocalidades);

        //Filtrar las listView de los dialogos
        textMarcaFavDialog.addTextChangedListener(new TextChange(textMarcaFavDialog));
        textLocalidadFavDialog.addTextChangedListener(new TextChange(textLocalidadFavDialog));

        // ClickListener sobre la lista de marcas
        listViewMarcasFavDialog.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                String marca = listViewMarcasFavDialog.getItemAtPosition(position).toString();
                textMarcaFavDialog.setText(marca);

                //Filtrar lista del otro campo
                    ArrayList<Gasolinera> filtradas = (ArrayList<Gasolinera>) presenterGasolinerasFavoritas.filtrarGasolinerasFavMarca(marca);
                    ArrayList<String> localidades = (ArrayList<String>) ExtractorLocalidadUtil.extraeLocalidades(filtradas);
                    ArrayAdapter<String> newLocalidades = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, localidades);
                    listViewLocalidadFavDialog.setAdapter(newLocalidades);

            }
        });

        // ClickListener sobre la lista de localidades
        listViewLocalidadFavDialog.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                String localidad = listViewLocalidadFavDialog.getItemAtPosition(position).toString();
                textLocalidadFavDialog.setText(localidad);
                
                //Filtrar lista del otro campo

                    ArrayList<Gasolinera> filtradas = (ArrayList<Gasolinera>) presenterGasolinerasFavoritas.filtrarGasolinerasFavLocal(localidad);
                    ArrayList<String> marcas = (ArrayList<String>) ExtractorMarcasUtil.extraeMarcas(filtradas);
                    ArrayAdapter<String> newMarcas = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, marcas);
                    listViewMarcasFavDialog.setAdapter(newMarcas);
            }
        });

        // Insertar elementos en el dialogo
        alertDialogBuilder.setView(view);
        alertDialogBuilder.show();

    }

    // ------------------------------------------------------
    // MODIFICA GASOLINERA
    public void modificaComentario(final Gasolinera gasolinera){
        final AlertDialog alertDialogBuilder = new AlertDialog.Builder(this)
                .setPositiveButton(getResources().getString(R.string.guardar),null)
                .setNegativeButton(getResources().getString(R.string.cancelar), null)
                .create();

        LayoutInflater inflater = this.getLayoutInflater();
        final View view = inflater.inflate(R.layout.modifica_comentario_favorito, null);
        comentarioEditText = view.findViewById(R.id.textBox_modificaComentario);
        // Buscamos la gasolinera para ver si es favorita
        final Gasolinera gDAO = presenterGasolineras.getGasolineraPorIdess(gasolinera.getIdeess(),
                AppDatabase.getInstance(getApplicationContext()).gasolineraDAO());
        if(gDAO != null) {
            // existe la gasolinera favorita
            GasolineraFavorita gFavorita = presenterGasolinerasFavoritas.getGasolineraFavoritaPorId(gDAO.getId(),
                    AppDatabase.getInstance(getApplicationContext()).gasolineraFavoritaDAO());
            // Escribo el comentario que ya habia en la gasolinera
            comentarioEditText.setText(gFavorita.getComentario());
        }
        // Definicion positive button ("guardar")
        alertDialogBuilder.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button b = alertDialogBuilder.getButton(BTN_POSITIVO);
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(comentarioEditText.getText().length()>240)
                            comentarioEditText.setError("El comentario debe ser menor de 240 carácteres");
                        else {
                            gasolineraAModificar = gDAO;
                            gasolineraAModificar.setId(gDAO.getId());
                            Toast.makeText(getApplicationContext(), "Comentario modificado", Toast.LENGTH_LONG).show();
                            ThreadModificaGasolineras thread = new ThreadModificaGasolineras();
                            new Thread(thread).start();
                            alertDialogBuilder.dismiss();
                        }
                    }
                });
            }
        });
        alertDialogBuilder.setView(view);
        alertDialogBuilder.show();
    }
    public class ThreadModificaGasolineras implements Runnable{
        public ThreadModificaGasolineras(){
            // Constructor vacio para la creacion de la Task
        }
        public void run(){
            presenterGasolinerasFavoritas.modificarGasolineraFavorita(gasolineraAModificar.getId(),
                    comentarioEditText.getText().toString(),
                    AppDatabase.getInstance(getApplicationContext()).gasolineraFavoritaDAO());
        }

    }
    // ------------------------------------------------------

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

        Button buttonModifica;
        Button buttonElimina;

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
            View view = inflater.inflate(R.layout.item_gasolinera_fav, null);

            // Asocia las variables de dicho layout
            ImageView logo = view.findViewById(R.id.imageViewLogoFav);
            TextView rotulo = view.findViewById(R.id.textViewRotuloFav);
            TextView direccion = view.findViewById(R.id.textViewDireccionFav);
            TextView gasoleoA = view.findViewById(R.id.textViewGasoleoAFav);
            TextView gasolina95 = view.findViewById(R.id.textViewGasolina95Fav);

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
                TextView tv = view.findViewById(R.id.textViewGasoleoALabelFav);
                RelativeLayout.LayoutParams params = ((RelativeLayout.LayoutParams) tv.getLayoutParams());
                params.setMargins(15, 0, 0, 0);
                tv.setTextSize(11);
                TextView tmp;
                tmp = view.findViewById(R.id.textViewGasolina95LabelFav);
                tmp.setTextSize(11);
                tmp = view.findViewById(R.id.textViewGasoleoAFav);
                tmp.setTextSize(11);
                tmp = view.findViewById(R.id.textViewGasolina95Fav);
                tmp.setTextSize(11);
            }
            // ------------------------------------------------------
            // MODIFICA GASOLINERA

            buttonModifica = view.findViewById(R.id.buttonModifica);
            buttonModifica.setFocusable(false);
            buttonModifica.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    modificaComentario(gasolinera);
                }
            });

            buttonElimina=view.findViewById(R.id.buttonElimina);
            buttonElimina.setFocusable(false);
            buttonElimina.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    creaDialogoConfirmacion(gasolinera);
                }
            });
            // ------------------------------------------------------
            return view;
        }

        private void cargaIcono(Gasolinera gasolinera, ImageView logo) {
            String rotuleImageID = gasolinera.getRotulo().toLowerCase();

            // Tengo que protegerme ante el caso en el que el rotulo solo tiene digitos.
            // En ese caso getIdentifier devuelve esos digitos en vez de 0.
            int imageID = context.getResources().getIdentifier(rotuleImageID,
                    "drawable", context.getPackageName());

            if (imageID == 0 || TextUtils.isDigitsOnly(rotuleImageID)) {
                imageID = context.getResources().getIdentifier(context.getResources().getString(R.string.pordefecto),
                        "drawable", context.getPackageName());
            }
            logo.setImageResource(imageID);
        }
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
