package com.isunican.proyectobase.Views;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.isunican.proyectobase.R;

public class FiltrosActivity extends AppCompatDialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        //super.onCreate(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.filtros_gasolinera, null);
        builder.setView(view).setTitle("Filtro");

        /*
        setContentView((R.layout.filtros_gasolinera));
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int height = dm.heightPixels;
        int width = dm.widthPixels;

        getWindow().setLayout((int)(width*0.6), (int)(height*0.6));
        */
        return builder.create();
    }

}
