package com.isunican.proyectobase.Views;

import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import com.isunican.proyectobase.R;

public class DialogoComentarioGasolineraFavorita {

    final static int NUM_CARACTERES_MAXIMO_COMENTARIO = 240;

    private DialogoComentarioGasolineraFavorita(){
        // no es necesario
    }

    public static int getNumCaracteresMaximoComentario(){
        return NUM_CARACTERES_MAXIMO_COMENTARIO;
    }

    public static void cambiaNumeroCaracteresActual(View view, final TextView comentarioEditText){
        final TextView textNumCaracteresActual = view.findViewById(R.id.textNumCaracteresActual);
        final TextView textNumCaracteresTotal = view.findViewById(R.id.textNumCaracteresTotal);
        textNumCaracteresTotal.setText("/"+NUM_CARACTERES_MAXIMO_COMENTARIO);
        // Escribimos el numero de caracteres actual por si estamos modificando uno existente
        int numCaracteresActual = comentarioEditText.getText().length();
        textNumCaracteresActual.setText(Integer.toString(numCaracteresActual));
        comentarioEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No es necesario
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int numCaracteresActual = comentarioEditText.getText().length();
                textNumCaracteresActual.setText(Integer.toString(numCaracteresActual));
                if(numCaracteresActual>NUM_CARACTERES_MAXIMO_COMENTARIO)
                    textNumCaracteresActual.setTextColor(Color.RED);
                else
                    textNumCaracteresActual.setTextColor(Color.BLACK);
            }
            @Override
            public void afterTextChanged(Editable s) {
                // No es necesario
            }
        });
    }



}
