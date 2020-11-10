package com.isunican.proyectobase.Views;

import com.isunican.proyectobase.R;
import com.isunican.proyectobase.Model.*;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    TextView nombreGasolinera;
    boolean gasolineraEsFavorita = false;

    private static final int BTN_POSITIVO = DialogInterface.BUTTON_POSITIVE;
    private static final int BTN_NEGATIVO = DialogInterface.BUTTON_NEGATIVE;

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
        nombreGasolinera = findViewById(R.id.nombreGasolineraText);
        TextView direccion = findViewById(R.id.direccionText);
        TextView precioGasoleoA = findViewById(R.id.precioGasoleoAText);
        TextView precioGasoleo95 = findViewById(R.id.precioGasoleo95Text);
        ImageView logo = findViewById(R.id.gasolineraIcon);
        comentario = findViewById(R.id.comentarioText);
        Gasolinera g = getIntent().getExtras().getParcelable(getResources().getString(R.string.pasoDatosGasolinera));
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
        if(g.esFavorita()){
            favButton.setImageResource(R.drawable.favorito_activado); // icono favorito activado
            gasolineraEsFavorita = true;
        }else{
            favButton.setImageResource(R.drawable.favorito_desactivado; // icono favorito desactivado
            gasolineraEsFavorita = false;
        }
        */
        favButton.setImageResource(R.drawable.favorito_desactivado); // icono favorito desactivado TODO quitar
        Toast.makeText(getApplicationContext(),"estado de boton es: "+favButton.isActivated(), Toast.LENGTH_LONG);
        favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abre ventana para añadir comentario
                    respuestaBotonFavorito();
            }
        });

        // TODO creo que ya no hace falta
        /*
        if(!g.getComentario().equals("")){
            comentario.setText("Comentario:\n" + g.getComentario());
        }else{
            comentario.setText("");
        }
        */
    }
    public void respuestaBotonFavorito(){
        // Creacion alertDialog
        LayoutInflater inflater = this.getLayoutInflater();
        if(!gasolineraEsFavorita){
           creaDialogoComentario(inflater);
        }else{
            creaDialogoConfirmacion(inflater);
        }

    }

    /**
     * Se puede refactorizar más todavia
     * @param inflater
     */
    public void creaDialogoComentario(LayoutInflater inflater){
        // Definicion positive button ("guardar")
        final AlertDialog alertDialogBuilder = new AlertDialog.Builder(this)
                .setPositiveButton(getResources().getString(R.string.guardar),null)
                .setNegativeButton(getResources().getString(R.string.cancelar), null)
                .create();
        final View view = inflater.inflate(R.layout.anhade_comentario_favorito, null);

        final TextView comentarioEditText = view.findViewById(R.id.textBox_anhadeComentario);
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
                        alertDialogBuilder.dismiss();
                    }
                });
            }
        });
        alertDialogBuilder.setView(view);
        alertDialogBuilder.show();
    }

    /**
     * Se puede refactorizar más todavia
     * @param inflater
     */
    public void creaDialogoConfirmacion(LayoutInflater inflater){
        final AlertDialog alertDialogConfirmacion=new AlertDialog.Builder(this)
                .setPositiveButton(getResources().getString(R.string.aplicar),null)
                .setNegativeButton(getResources().getString(R.string.cancelar),null)
                .create();
        final View view1=inflater.inflate(R.layout.confirmacion_elimina_favorito,null);
        final TextView txt_confirmacion=view1.findViewById(R.id.txt_confirmacion);
        txt_confirmacion.setText("¿Quiere eliminar la gasolinera "+nombreGasolinera.getText()+" de su lista de favoritos?");
        alertDialogConfirmacion.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button btnAceptar=alertDialogConfirmacion.getButton(BTN_POSITIVO);
                btnAceptar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        favButton.setImageResource(R.drawable.favorito_desactivado);
                        gasolineraEsFavorita = false;
                        Toast.makeText(getApplicationContext(),"gasolinera eliminada",Toast.LENGTH_LONG).show();
                        comentario.setText("");
                        alertDialogConfirmacion.dismiss();
                    }

                });
            }
        });
        alertDialogConfirmacion.setView(view1);
        alertDialogConfirmacion.show();
    }

}