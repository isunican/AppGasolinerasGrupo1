package com.isunican.proyectobase.Presenter;

import android.content.Context;
import android.util.Log;

import com.isunican.proyectobase.Database.AppDatabase;
import com.isunican.proyectobase.Model.Gasolinera;
import com.isunican.proyectobase.Model.GasolineraFavorita;
import com.isunican.proyectobase.Model.TarjetaDescuento;

import java.util.ArrayList;
import java.util.List;

public class PresenterGasolinerasFavoritas {

    private List<GasolineraFavorita> gasolineraFavoritaList;

    public PresenterGasolinerasFavoritas(){
        this.gasolineraFavoritaList = new ArrayList<GasolineraFavorita>();
    }

    public List<GasolineraFavorita> getListaGasolinerasFavoritas() { return gasolineraFavoritaList; }
    public void setListaGasolinerasFavoritas(List<GasolineraFavorita> gasolineraFavoritaListM) {
        this.gasolineraFavoritaList = gasolineraFavoritaList;
    }

    public Gasolinera eliminaGasolineraFavorita(Gasolinera gasolinera){

        // hago cosas
        return gasolinera;
    }
    public GasolineraFavorita anhadirGasolineraFavorita(int idGasolinera, String comentario, Context contexto){
        GasolineraFavorita favorito=new GasolineraFavorita(comentario,idGasolinera);
        AppDatabase.getInstance(contexto).gasolineraFavoritaDAO().insertOne(favorito);
        Log.d("Gas añadida",favorito.toString());
        gasolineraFavoritaList.add(favorito);
        return favorito;
    }
}
