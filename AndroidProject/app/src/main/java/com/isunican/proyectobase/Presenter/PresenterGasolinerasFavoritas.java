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
    Context contexto; //Contexto de la aplicación (Necesario para acceder a la BD)

    private List<GasolineraFavorita> gasolineraFavoritaList;

    /**
     * Crea el presenter, inicializando la lista de gasolineras favoritas
     * @param contexto de la aplicación (Necesario para la BD)
     */
    public PresenterGasolinerasFavoritas(Context contexto){
        gasolineras = new ArrayList<>();        //Cargar datos de la BD
        gasolineraFavoritaList = new ArrayList<>();

        this.contexto = contexto;
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

    /**
     * Elimina una gasolinera de la lista de favoritos, borrandola tanto de
     * la lista como de la base de datos.
     *
     * @author Elena Romon Lopez
     *
     * @param idGasolinera identificador de la gasolinera a eliminar
     * @return Gasolinera gasolinera eliminada
     */
    public GasolineraFavorita eliminaGasolineraFavorita(int idGasolinera, GasolineraDAO gasolineraDAO,
                                                        GasolineraFavoritaDAO gasolineraFavoritaDAO){

        GasolineraFavorita gasolineraEliminar = getGasolineraFavoritaPorId(idGasolinera,gasolineraFavoritaDAO);


        /**
        for (int i = 0; i<gasolineraFavoritaList.size(); i++){
            if (gasolineraFavoritaList.get(i).getIdGasolinera() == idGasolinera){
                gasolineraEliminar = gasolineraFavoritaList.get(i);
                gasolineraFavoritaList.remove(i);
            }
        }
         */

        // TODO: Quitar gasolinera de la lista de la BD
        //AppDatabase.getInstance(contexto);
        //gasolineraFavoritaDAO.delete(gasolineraEliminar);
        //gasolineraDAO.delete(gasolineraEliminar);


        // TODO: eliminar de la BD, eliminar de la lista, y mostrar mensaje de error
        //
        // Qué se obtiene de
        //
        if (gasolineraEliminar != null){
            gasolineraFavoritaList.remove(gasolineraEliminar);
            AppDatabase.getInstance(contexto);
            gasolineraFavoritaDAO.delete(gasolineraEliminar);
            //gasolineraDAO.delete(gasolineraEliminar);
            Toast.makeText(contexto, "La gasolinera se ha eliminado correctamente.", Toast.LENGTH_SHORT).show();
        } else {
            //Mensaje de error: la gasolinera no se ha podido eliminar correctamente.
            Toast.makeText(contexto, "La gasolinera no se ha podido eliminar correctamente", Toast.LENGTH_SHORT).show();
        }

        return gasolineraEliminar;
    }
    public GasolineraFavorita anhadirGasolineraFavorita(int idGasolinera, String comentario, Context contexto){
        GasolineraFavorita favorito=new GasolineraFavorita(comentario,idGasolinera);
        AppDatabase.getInstance(contexto).gasolineraFavoritaDAO().insertOne(favorito);
        return favorito;
    }
}
