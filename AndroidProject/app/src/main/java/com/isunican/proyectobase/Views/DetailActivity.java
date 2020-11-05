package com.isunican.proyectobase.Views;

import com.isunican.proyectobase.R;
import com.isunican.proyectobase.Model.*;

import android.content.Context;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

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
        direccion.setText("Dirección:\n"+g.getDireccion());
        precioGasoleoA.setText("Gasoleo A: "+g.getGasoleoA()+"€");
        precioGasoleo95.setText("Gasoleo 95: "+g.getGasolina95()+"€");

    }
}