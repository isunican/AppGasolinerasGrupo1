package com.isunican.proyectobase.Views;

import com.isunican.proyectobase.Database.AppDatabase;
import com.isunican.proyectobase.Presenter.PresenterGasolineras;
import com.isunican.proyectobase.Presenter.PresenterGasolinerasFavoritas;
import com.isunican.proyectobase.R;
import com.isunican.proyectobase.Model.*;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

/*
------------------------------------------------------------------
    Vista de detalle

    Presenta datos de detalle de una Gasolinera concreta.
    La gasolinera a mostrar se le pasa directamente a la actividad
    en la llamada por intent (usando putExtra / getExtra)
    Para ello Gasolinera implementa la interfaz Parcelable
------------------------------------------------------------------
*/
public class DetailActivity extends AppCompatActivity {

    ImageButton favButton;
    TextView comentario;
    TextView comentarioEditText;
    boolean gasolineraEsFavorita = false;
    PresenterGasolinerasFavoritas gasolinerasFavoritas=new PresenterGasolinerasFavoritas();
    PresenterGasolineras presenterGasolineras = new PresenterGasolineras();


    private static final int BTN_POSITIVO = DialogInterface.BUTTON_POSITIVE;

    Gasolinera g;
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
        setContentView(R.layout.activity_detail);

        // muestra el logo en el actionBar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.por_defecto_mod);

        // captura el TextView
        // obtiene el objeto Gasolinera a mostrar
        // y lo introduce en el TextView convertido a cadena de texto
        TextView nombreGasolinera = findViewById(R.id.nombreGasolineraText);
        TextView direccion = findViewById(R.id.direccionText);
        TextView precioGasoleoA = findViewById(R.id.precioGasoleoAText);
        TextView precioGasoleo95 = findViewById(R.id.precioGasoleo95Text);
        ImageView logo = findViewById(R.id.gasolineraIcon);
        comentario = findViewById(R.id.comentarioText);
        g = getIntent().getExtras().getParcelable(getResources().getString(R.string.pasoDatosGasolinera));

        favButton = findViewById(R.id.favButton);

        // Buscamos la gasolinera para ver si es favorita
        Gasolinera gDAO = presenterGasolineras.getGasolineraPorIdess(g.getIdeess(),
                AppDatabase.getInstance(getApplicationContext()).gasolineraDAO());
        if(gDAO != null)
        {
            // existe la gasolinera favorita
            GasolineraFavorita gFavorita = gasolinerasFavoritas.getGasolineraFavoritaPorId(gDAO.getId(),
                    AppDatabase.getInstance(getApplicationContext()).gasolineraFavoritaDAO());
            favButton.setImageResource(R.drawable.favorito_activado); // icono favorito activado
            gasolineraEsFavorita = true;
            comentario.setText("Comentario:\n"+gFavorita.getComentario());
        }
        else
        {
            // no existe la gasolinera favorita
            favButton.setImageResource(R.drawable.favorito_desactivado); // icono favorito activado
            gasolineraEsFavorita = false;
        }

        favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abre ventana para añadir comentario
                creaVentanaAnhadeComentario();
            }
        });

        String rotuleImageID = g.getRotulo().toLowerCase();

        // Tengo que protegerme ante el caso en el que el rotulo solo tiene digitos.
        // En ese caso getIdentifier devuelve esos digitos en vez de 0.
        Context context = getApplicationContext();
        int imageID = context.getResources().getIdentifier(rotuleImageID,
                "drawable", context.getPackageName());

        if (imageID == 0 || TextUtils.isDigitsOnly(rotuleImageID)) {
            imageID = context.getResources().getIdentifier(getResources().getString(R.string.pordefecto),
                    "drawable", context.getPackageName());
        }
        logo.setImageResource(imageID);

        // cajas de texto
        nombreGasolinera.setText(g.getRotulo());
        direccion.setText("Dirección:\n" + g.getDireccion());
        precioGasoleoA.setText("Gasoleo A: " + g.getGasoleoA() + "€");
        precioGasoleo95.setText("Gasoleo 95: " + g.getGasolina95() + "€");
    }

    public void creaVentanaAnhadeComentario(){
        // Creacion alertDialog
        final AlertDialog alertDialogBuilder = new AlertDialog.Builder(this)
                .setPositiveButton(getResources().getString(R.string.guardar),null)
                .setNegativeButton(getResources().getString(R.string.cancelar), null)
                .create();

        LayoutInflater inflater = this.getLayoutInflater();
        final View view = inflater.inflate(R.layout.anhade_comentario_favorito, null);

        comentarioEditText = view.findViewById(R.id.textBox_anhadeComentario);

        if(!gasolineraEsFavorita){
            // Definicion positive button ("guardar")
            alertDialogBuilder.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialog) {
                    Button b = alertDialogBuilder.getButton(BTN_POSITIVO);
                    b.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(getApplicationContext(), "Gasolinera favorita añadida con comentario: "+
                                    comentario.getText(), Toast.LENGTH_LONG).show();
                            comentario.setText("Comentario:\n"+comentarioEditText.getText());
                            favButton.setImageResource(R.drawable.favorito_activado);
                            gasolineraEsFavorita = true;
                            ThreadAnhadirGasolineras thread=new ThreadAnhadirGasolineras();
                            new Thread(thread).start();
                            alertDialogBuilder.dismiss();
                        }
                    });
                }
            });
            alertDialogBuilder.setView(view);
            alertDialogBuilder.show();
        }else{
            // TODO Eliminar gasolinera de favoritos
            favButton.setImageResource(R.drawable.favorito_desactivado);
            gasolineraEsFavorita = false;
        }

    }

    public class ThreadAnhadirGasolineras implements Runnable{
        public ThreadAnhadirGasolineras(){
            // Constructor vacio para la creacion de la Task
        }
        public void run(){
            int id = presenterGasolineras.anhadeGasolinera(g, AppDatabase.getInstance(getApplicationContext()).gasolineraDAO());
            gasolinerasFavoritas.anhadirGasolineraFavorita(id,comentarioEditText.getText().toString(),
                    AppDatabase.getInstance(getApplicationContext()).gasolineraFavoritaDAO());
        }

    }
}