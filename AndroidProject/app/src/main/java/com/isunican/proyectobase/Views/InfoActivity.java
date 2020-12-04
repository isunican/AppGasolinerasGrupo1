package com.isunican.proyectobase.Views;

import com.isunican.proyectobase.R;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.CountDownTimer;
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
    int clicks = 0; //Contador de clicks para activar el easter egg
    static final int TRIGGER = 10; //Maximo de veces que hay que pulsar para mostrar el easter egg
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

        //Detecta cuando han pulsado el texto
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicks++;
                //Si se ha pulsado el número requerido de veces el texto, mostrar el easter egg
                if(clicks >= TRIGGER){
                    setContentView(R.layout.easter_egg);
                    Toast easterEgg = Toast.makeText( getApplicationContext(), getResources().getString(R.string.Easter_Egg), Toast.LENGTH_LONG);
                    easterEgg.show();
                    getSupportActionBar().hide();

                } else if(clicks >= TRIGGER/2){ //Si se ha pulsado el texto más de la mitad de las veces requeridas, dar una pista
                    int toastDurationInMilliSeconds = 250;
                    CountDownTimer toastCountDown;

                    final Toast easterEggCounter = Toast.makeText( getApplicationContext(), getResources().getString(R.string.Easter_Egg_Counter1)
                            + " "+(TRIGGER-clicks) +" "+ getResources().getString(R.string.Easter_Egg_Counter2), Toast.LENGTH_SHORT);

                    //Timer para tener una duración personalizada del toast
                    toastCountDown = new CountDownTimer(toastDurationInMilliSeconds, 1000 /*Tick duration*/) {
                        public void onTick(long millisUntilFinished) {
                            easterEggCounter.show();
                        }
                        public void onFinish() {
                            easterEggCounter.cancel();
                        }
                    };

                    easterEggCounter.show();
                    toastCountDown.start();
                }
            }
        });
    }
}
