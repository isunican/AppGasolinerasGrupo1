package com.isunican.proyectobase.Views;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;

import com.isunican.proyectobase.R;

public class FiltrosActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView((R.layout.filtros_gasolinera));
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int height = dm.heightPixels;
        int width = dm.widthPixels;

        getWindow().setLayout((int)(width*0.6), (int)(height*0.6));
    }

}
