package com.isunican.proyectobase.Presenter;

import android.content.Context;

import com.isunican.proyectobase.DAO.GasolineraDAO;
import com.isunican.proyectobase.DAO.GasolineraFavoritaDAO;
import com.isunican.proyectobase.Database.AppDatabase;
import android.widget.Toast;

import com.isunican.proyectobase.Model.Gasolinera;
import com.isunican.proyectobase.Model.GasolineraFavorita;
import com.isunican.proyectobase.Model.TarjetaDescuento;

import java.util.ArrayList;
import java.util.List;

public class PresenterGasolinerasFavoritas {

    private ArrayList<Gasolinera> gasolineras; //Lista de gasolineras favoritas
    //Context contexto; //Contexto de la aplicación (Necesario para acceder a la BD)

    private List<GasolineraFavorita> gasolineraFavoritaList;

    /**
     * Crea el presenter, inicializando la lista de gasolineras favoritas
     */
    public PresenterGasolinerasFavoritas(){
        gasolineras = new ArrayList<>();        //Cargar datos de la BD
        gasolineraFavoritaList = new ArrayList<>();

        //this.contexto = contexto;
    }

    public List<GasolineraFavorita> getListaGasolinerasFavoritas() { return gasolineraFavoritaList; }

    public void setListaGasolinerasFavoritas(List<GasolineraFavorita> gasolineraFavoritaListM) {
        this.gasolineraFavoritaList = gasolineraFavoritaList;
    }

    public GasolineraFavorita getGasolineraFavoritaPorId(int id, GasolineraFavoritaDAO gasolineraFavoritaDAO){
        for (GasolineraFavorita gF: gasolineraFavoritaDAO.getAll()) {
            if(gF.getIdGasolinera() == id)
                return gF;
        }
        return null;
    }

    public Gasolinera getGasolineraPorId (int id, GasolineraDAO gasolineraDAO){
        for (Gasolinera g: gasolineraDAO.getAll()) {
            if(g.getIdeess() == id)
                return g;
        }
        return null;
    }

    /**
     * Elimina una gasolinera de la lista de favoritos, borrandola tanto de
     * la lista como de la base de datos.
     *
     * @author Elena Romon Lopez
     *
     * @param idGasolinera identificador de la gasolinera a eliminar
     * @return Gasolinera gasolinera eliminada
     */
    public Gasolinera eliminaGasolineraFavorita(int idGasolinera, GasolineraDAO gasolineraDAO,
                                                        GasolineraFavoritaDAO gasolineraFavoritaDAO){

        Gasolinera gasolinera;
        GasolineraFavorita gasolineraFavorita;

        if (gasolineraFavoritaDAO == null || gasolineraDAO == null) {
            gasolinera = null;
            //System.out.println("if");
        } else {
            gasolinera = getGasolineraPorId(idGasolinera, gasolineraDAO);
            gasolineraFavorita = getGasolineraFavoritaPorId(idGasolinera, gasolineraFavoritaDAO);
            //System.out.println("else");
            if (gasolineraFavorita != null && gasolinera != null) {
                //System.out.println("hola maninnnnn");
                gasolineraFavoritaList.remove(gasolineraFavorita);
                //AppDatabase.getInstance(contexto);
                gasolineraFavoritaDAO.delete(gasolineraFavorita);
                gasolineraDAO.delete(gasolinera);
                //System.out.println("Gasolinera después de borrar" + gasolineraDAO.findById(gasolinera.getIdeess()).size());
                //System.out.println("Gasolinera Favorita después de borrar" + gasolineraFavoritaDAO.findByGasolineraId(gasolinera.getIdeess()).size());
            }
        }
        return gasolinera;
    }
}
