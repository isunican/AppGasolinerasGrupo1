package com.isunican.proyectobase.Presenter;

import android.content.Context;
import android.util.Log;

import com.isunican.proyectobase.DAO.GasolineraFavoritaDAO;
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
    public void setListaGasolinerasFavoritas(List<GasolineraFavorita> gasolineraFavoritaList) {
        this.gasolineraFavoritaList = gasolineraFavoritaList;
    }

    public Gasolinera eliminaGasolineraFavorita(Gasolinera gasolinera){

        // hago cosas
        return gasolinera;
    }
    public GasolineraFavorita anhadirGasolineraFavorita(int idGasolinera, String comentario, GasolineraFavoritaDAO gasolineraFavoritaDAO){
        GasolineraFavorita favorito=new GasolineraFavorita(comentario,idGasolinera);
        gasolineraFavoritaDAO.insertOne(favorito);
        Log.d("Gas añadida",favorito.toString());
        gasolineraFavoritaList.add(favorito);
        return favorito;
    }
    public GasolineraFavorita modificarGasolineraFavorita(int idGasolinera, String comentario, GasolineraFavoritaDAO gasolineraFavoritaDAO){
        List<GasolineraFavorita> gF=gasolineraFavoritaDAO.findByGasolineraId(idGasolinera);
        gasolineraFavoritaList.remove(gF.get(0));
        gF.get(0).setComentario(comentario);
        gasolineraFavoritaDAO.update(gF.get(0));
        gasolineraFavoritaList.add(gF.get(0));
        return gF.get(0);
    }
}
