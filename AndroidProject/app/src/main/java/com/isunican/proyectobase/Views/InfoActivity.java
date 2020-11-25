package com.isunican.proyectobase.Views;

import com.isunican.proyectobase.R;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


/*
------------------------------------------------------------------
    Vista de información

    Presenta datos de información de la aplicación.
------------------------------------------------------------------
*/
public class InfoActivity extends AppCompatActivity {

    TextView textView;
    int clicks = 0;
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
        setContentView(R.layout.activity_info);

        // muestra el logo en el actionBar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.por_defecto_mod);

        // captura el TextView
        // y muestra en él un texto de información predefinido
        textView = findViewById(R.id.nombreGasolineraText);
        textView.setText(getResources().getString(R.string.infoTexto));

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicks++;
                if(clicks >= 10){
                    System.out.println("Activado Easter Egg");
                    setContentView(R.layout.easter_egg);
                    Toast easterEgg = Toast.makeText( getApplicationContext(), getResources().getString(R.string.Easter_Egg), Toast.LENGTH_LONG);
                    easterEgg.show();
                    getSupportActionBar().hide();
                }
            }
        });
    }
}
