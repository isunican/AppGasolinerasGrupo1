package com.isunican.proyectobase.Presenter;

import android.content.Context;
import android.util.Log;

import com.isunican.proyectobase.DAO.GasolineraDAO;
import com.isunican.proyectobase.DAO.GasolineraFavoritaDAO;
import com.isunican.proyectobase.Database.AppDatabase;
import com.isunican.proyectobase.Model.Gasolinera;
import com.isunican.proyectobase.Model.GasolineraFavorita;
import com.isunican.proyectobase.Model.TarjetaDescuento;

import java.util.ArrayList;
import java.util.Iterator;
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

    public GasolineraFavorita getGasolineraFavoritaPorId(int id, GasolineraFavoritaDAO gasolineraFavoritaDAO){

        for (GasolineraFavorita gF: gasolineraFavoritaDAO.getAll()) {
            if(gF.getIdGasolinera() == id)
                return gF;
        }
        return null;
    }

    public Gasolinera eliminaGasolineraFavorita(Gasolinera gasolinera){

        // hago cosas
        return gasolinera;
    }

    public GasolineraFavorita anhadirGasolineraFavorita(int idGasolinera, String comentario, GasolineraFavoritaDAO gasolineraFavoritaDAO){
        GasolineraFavorita favorito=new GasolineraFavorita(comentario,idGasolinera);
        long rowid = gasolineraFavoritaDAO.insertOne(favorito);
        favorito.setId(gasolineraFavoritaDAO.getIdFromRowId(rowid));
        gasolineraFavoritaList.add(favorito);
        return favorito;
    }
    
    public GasolineraFavorita modificarGasolineraFavorita(int idGasolinera, String comentario, GasolineraFavoritaDAO gasolineraFavoritaDAO){
        GasolineraFavorita g = null;
        boolean encontrado = false;
        Iterator<GasolineraFavorita> gasolineraFavoritaIterator = gasolineraFavoritaList.iterator();
        while(!encontrado && gasolineraFavoritaIterator.hasNext()){
            g = gasolineraFavoritaIterator.next();
            if (g.getIdGasolinera() == idGasolinera){
                g.setComentario(comentario);
                gasolineraFavoritaDAO.update(g);
                encontrado = true;
            }
        }
        return g;
    }
}
