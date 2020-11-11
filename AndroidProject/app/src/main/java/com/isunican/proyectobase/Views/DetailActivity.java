package com.isunican.proyectobase.Views;

import com.isunican.proyectobase.Database.AppDatabase;
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
    boolean gasolineraEsFavorita = false;
    PresenterGasolinerasFavoritas gasolinerasFavoritas=new PresenterGasolinerasFavoritas();

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
        // TODO GasolineraFavorita gFavorita = listaGasolinerasFavoritas.get(g.getIdeess());
        /*
        if(gFavorita == null)
            gasolineraEsFavorita = false;
        else
            gasolineraEsFavorita = true;
        */
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

        favButton = findViewById(R.id.favButton);
        // TODO para cuando la gasolinear tenga atributo favorita
        /*
        if(gasolineraEsFavorita)){
            favButton.setImageResource(R.drawable.favorito_activado); // icono favorito activado
            gasolineraEsFavorita = true;
        }else{
            favButton.setImageResource(R.drawable.favorito_desactivado; // icono favorito desactivado
            gasolineraEsFavorita = false;
        }
        */
        favButton.setImageResource(R.drawable.favorito_desactivado); // icono favorito desactivado TODO quitar
        favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abre ventana para añadir comentario
                creaVentanaAnhadeComentario();
            }
        });

        // TODO para cuando haya comentario guardado en gasolinera
        /*
        if(!gFavorita.getComentario().equals("")){
            comentario.setText("Comentario:\n" + g.getComentario());
        }else{
            comentario.setText("");
        }
        */
    }

    public void creaVentanaAnhadeComentario(){
        // Creacion alertDialog
        final AlertDialog alertDialogBuilder = new AlertDialog.Builder(this)
                .setPositiveButton(getResources().getString(R.string.guardar),null)
                .setNegativeButton(getResources().getString(R.string.cancelar), null)
                .create();

        LayoutInflater inflater = this.getLayoutInflater();
        final View view = inflater.inflate(R.layout.anhade_comentario_favorito, null);

        final TextView comentarioEditText = view.findViewById(R.id.textBox_anhadeComentario);

        if(!gasolineraEsFavorita){
            // Definicion positive button ("guardar")
            alertDialogBuilder.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialog) {
                    Button b = alertDialogBuilder.getButton(BTN_POSITIVO);
                    b.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // TODO Anhade gasolinera a favoritas
                            // TODO Anhade comentario a gasolinera
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
        }
        public void run(){
            gasolinerasFavoritas.getListaGasolinerasFavoritas();
            Log.d("Añado Gaso","anhadoGAsol");
            gasolinerasFavoritas.anhadirGasolineraFavorita(g.getIdeess(),comentario.getText().toString(),
                    AppDatabase.getInstance(getApplicationContext()).gasolineraFavoritaDAO());
        }

    }
}