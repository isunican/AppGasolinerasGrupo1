package com.isunican.proyectobase.Views;

import com.isunican.proyectobase.R;
import com.isunican.proyectobase.Model.*;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;


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

    TextView nombreGasolinera;
    TextView direccion;
    TextView precioGasoleoA;
    TextView precioGasoleo95;
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
        nombreGasolinera = findViewById(R.id.nombreGasolineraText);
        direccion = findViewById(R.id.direccionText);
        precioGasoleoA = findViewById(R.id.precioGasoleoAText);
        precioGasoleo95 = findViewById(R.id.precioGasoleo95Text);
        g = getIntent().getExtras().getParcelable(getResources().getString(R.string.pasoDatosGasolinera));

        nombreGasolinera.setText(g.getRotulo());
        direccion.setText("Dirección:\n"+g.getDireccion());
        precioGasoleoA.setText("Gasoleo A: "+g.getGasoleoA()+"€");
        precioGasoleo95.setText("Gasoleo 95: "+g.getGasolina95()+"€");

    }
}