package com.isunican.proyectobase.Views;

import com.isunican.proyectobase.Presenter.PresenterGasolineras;
import com.isunican.proyectobase.Presenter.PresenterGasolinerasFavoritas;
import com.isunican.proyectobase.DAO.GasolineraDAO;
import com.isunican.proyectobase.DAO.GasolineraFavoritaDAO;
import com.isunican.proyectobase.Database.AppDatabase;
import com.isunican.proyectobase.R;
import com.isunican.proyectobase.Model.*;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.speech.tts.TextToSpeech;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;

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
    TextView nombreGasolinera;
    boolean gasolineraEsFavorita = false;
    PresenterGasolinerasFavoritas gasolinerasFavoritas;
    PresenterGasolineras presenterGasolineras = new PresenterGasolineras();
    Gasolinera g;
    final static int NUM_CARACTERES_MAXIMO_COMENTARIO = 240;

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
        setContentView(R.layout.activity_detail);

        gasolinerasFavoritas = new PresenterGasolinerasFavoritas();

        // muestra el logo en el actionBar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.por_defecto_mod);

        // captura el TextView
        // obtiene el objeto Gasolinera a mostrar
        // y lo introduce en el TextView convertido a cadena de texto
        nombreGasolinera = findViewById(R.id.nombreGasolineraText);
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
        if (gDAO != null) {
            // existe la gasolinera favorita
            GasolineraFavorita gFavorita = gasolinerasFavoritas.getGasolineraFavoritaPorId(gDAO.getId(),
                    AppDatabase.getInstance(getApplicationContext()).gasolineraFavoritaDAO());
            favButton.setImageResource(R.drawable.favorito_activado); // icono favorito activado
            favButton.setTag(R.drawable.favorito_activado);
            gasolineraEsFavorita = true;
            comentario.setText("Comentario:\n" + gFavorita.getComentario());
        } else {
            // no existe la gasolinera favorita
            favButton.setImageResource(R.drawable.favorito_desactivado); // icono favorito activado
            favButton.setTag(R.drawable.favorito_desactivado);
            gasolineraEsFavorita = false;
        }

        favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abre ventana para añadir comentario
                LayoutInflater inflater = getLayoutInflater();
                creaDialogoComentario(inflater);
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

        favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abre ventana para añadir comentario
                respuestaBotonFavorito();
            }
        });

    }

    /**
     * Método auxiliar llamado por el onclick listener del botón detras del icono de la estrella
     */
    private void respuestaBotonFavorito() {
        // Creacion alertDialog
        LayoutInflater inflater = this.getLayoutInflater();
        if (!gasolineraEsFavorita) {
            creaDialogoComentario(inflater);
        } else {
            creaDialogoConfirmacion(inflater);
        }

    }

    /**
     * Crea y abre el dialogo para introducir el comentario
     *
     * @param inflater
     */
    public void creaDialogoComentario(LayoutInflater inflater) {
        // Definicion positive button ("guardar")
        final AlertDialog alertDialogBuilder = new AlertDialog.Builder(this)
                .setPositiveButton(getResources().getString(R.string.guardar), null)
                .setNegativeButton(getResources().getString(R.string.cancelar), null)
                .create();
        final View view = inflater.inflate(R.layout.anhade_comentario_favorito, null);

        //final TextView
        comentarioEditText = view.findViewById(R.id.textBox_anhadeComentario);

        // Definicion positive button ("guardar")
        alertDialogBuilder.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button b = alertDialogBuilder.getButton(BTN_POSITIVO);
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (comentarioEditText.getText().length() > NUM_CARACTERES_MAXIMO_COMENTARIO)
                            comentarioEditText.setError("El comentario debe ser menor de 240 carácteres");
                        else {
                            String toastComentarioReducido = comentarioEditText.getText().toString().trim();
                            if (toastComentarioReducido.length() > 30) {
                                toastComentarioReducido = toastComentarioReducido.substring(0, 30);
                                toastComentarioReducido += "...";
                            }
                            Toast.makeText(getApplicationContext(), "Gasolinera favorita añadida con comentario: " +
                                    toastComentarioReducido, Toast.LENGTH_LONG).show();
                            comentario.setText("Comentario:\n" + comentarioEditText.getText());
                            favButton.setImageResource(R.drawable.favorito_activado);
                            favButton.setTag(R.drawable.favorito_activado);
                            gasolineraEsFavorita = true;
                            ThreadAnhadirGasolineras thread = new ThreadAnhadirGasolineras();
                            new Thread(thread).start();
                            alertDialogBuilder.dismiss();
                        }
                    }
                });
            }
        });
        alertDialogBuilder.setView(view);
        alertDialogBuilder.show();

        // Caracteres totales escritos
        final TextView textNumCaracteresActual = view.findViewById(R.id.textNumCaracteresActual);
        final TextView textNumCaracteresTotal = view.findViewById(R.id.textNumCaracteresTotal);
        textNumCaracteresTotal.setText("/"+NUM_CARACTERES_MAXIMO_COMENTARIO);
        comentarioEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int numCaracteresActual = comentarioEditText.getText().length();
                textNumCaracteresActual.setText(Integer.toString(numCaracteresActual));
                if(numCaracteresActual>NUM_CARACTERES_MAXIMO_COMENTARIO)
                    textNumCaracteresActual.setTextColor(Color.RED);
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });

        }


    /**
     * Método que crea una ventana de diálogo de confirmación para eliminar la gasolinera.
     *
     * @Author Jaime López-Agudo Higuera
     * @param inflater
     */
    public void creaDialogoConfirmacion(LayoutInflater inflater) {
        //Creacion de la ventana de diálogo
        final AlertDialog alertDialogConfirmacion = new AlertDialog.Builder(this)
                .setPositiveButton(getResources().getString(R.string.aplicar), null)
                .setNegativeButton(getResources().getString(R.string.cancelar), null)
                .create();
        //Variables de la ventana de diálogo
        final View view1 = inflater.inflate(R.layout.confirmacion_elimina_favorito, null);
        final TextView txtConfirmacion = view1.findViewById(R.id.txt_confirmacion);
        txtConfirmacion.setText("¿Quiere eliminar la gasolinera " + nombreGasolinera.getText() + " de su lista de favoritos?");
        //on click listener del botón de aceptar
        alertDialogConfirmacion.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button btnAceptar = alertDialogConfirmacion.getButton(BTN_POSITIVO);
                btnAceptar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //lanzamos la task de eliminar el hilo
                        RemoverThread hilo = new RemoverThread(g, DetailActivity.this.getApplicationContext(), gasolinerasFavoritas);
                        new Thread(hilo).start();
                        //Cambiamos la interfaz para que se refleje la gasolinera eliminada
                        favButton.setImageResource(R.drawable.favorito_desactivado);
                        favButton.setTag(R.drawable.favorito_desactivado);
                        gasolineraEsFavorita = false;
                        Toast.makeText(getApplicationContext(), "gasolinera eliminada", Toast.LENGTH_LONG).show();
                        comentario.setText("");
                        //Cerramos la ventana de dialogo
                        alertDialogConfirmacion.dismiss();
                    }

                });
            }
        });
        alertDialogConfirmacion.setView(view1);
        alertDialogConfirmacion.show();
    }

    public class ThreadAnhadirGasolineras implements Runnable {
        public ThreadAnhadirGasolineras() {
            // Constructor vacio para la creacion de la Task
        }

        public void run() {
            int id = presenterGasolineras.anhadeGasolinera(g, AppDatabase.getInstance(getApplicationContext()).gasolineraDAO());
            gasolinerasFavoritas.anhadirGasolineraFavorita(id, comentarioEditText.getText().toString().trim(),
                    AppDatabase.getInstance(getApplicationContext()).gasolineraFavoritaDAO());
        }
    }
}

class RemoverThread implements Runnable {

    private PresenterGasolinerasFavoritas presenterGasolinerasFavoritas;
    private Gasolinera gasolinera;
    private Context contexto;

    public RemoverThread (Gasolinera g, Context contexto, PresenterGasolinerasFavoritas presenterGasolinerasFavoritas) {
        this.gasolinera = g;
        this.contexto = contexto;
        this.presenterGasolinerasFavoritas = presenterGasolinerasFavoritas;
    }

    @Override
    public void run() {
        GasolineraDAO gasolineraDAO = AppDatabase.getInstance(contexto).gasolineraDAO();
        GasolineraFavoritaDAO gasolineraFavoritaDAO = AppDatabase.getInstance(contexto).gasolineraFavoritaDAO();
        List<Gasolinera> lista = gasolineraDAO.findByIdEESS(gasolinera.getIdeess());
        presenterGasolinerasFavoritas.eliminaGasolineraFavorita(lista.get(0), gasolineraDAO, gasolineraFavoritaDAO);
    }
}