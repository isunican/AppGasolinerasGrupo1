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

        Gasolinera gasolinera = getGasolineraPorId(idGasolinera, gasolineraDAO);
        GasolineraFavorita gasolineraEliminar = getGasolineraFavoritaPorId(idGasolinera,gasolineraFavoritaDAO);


        /**
        for (int i = 0; i<gasolineraFavoritaList.size(); i++){
            if (gasolineraFavoritaList.get(i).getIdGasolinera() == idGasolinera){
                gasolineraEliminar = gasolineraFavoritaList.get(i);
                gasolineraFavoritaList.remove(i);
            }
        }
         */

        //AppDatabase.getInstance(contexto);
        //gasolineraFavoritaDAO.delete(gasolineraEliminar);
        //gasolineraFavoritaList.remove(gasolineraEliminar);
        //gasolineraDAO.delete(gasolinera);

        System.out.println("Número de gasolineras favoritas = " + gasolineraFavoritaList.size());

        // TODO: eliminar de la BD, eliminar de la lista, y mostrar mensaje de error
        if (gasolineraEliminar != null){
            gasolineraFavoritaList.remove(gasolineraEliminar);
            AppDatabase.getInstance(contexto);
            gasolineraFavoritaDAO.delete(gasolineraEliminar);
            System.out.println("Número de gasolineras favoritas = " + gasolineraFavoritaList.size());
            Toast.makeText(contexto, "La gasolinera se ha eliminado correctamente.", Toast.LENGTH_SHORT).show();
        } else {
            //Mensaje de error: la gasolinera no se ha podido eliminar correctamente.
            Toast.makeText(contexto, "La gasolinera no se ha podido eliminar correctamente", Toast.LENGTH_SHORT).show();
        }
        return gasolinera;
    }
    public GasolineraFavorita anhadirGasolineraFavorita(int idGasolinera, String comentario, Context contexto){
        GasolineraFavorita favorito=new GasolineraFavorita(comentario,idGasolinera);
        AppDatabase.getInstance(contexto).gasolineraFavoritaDAO().insertOne(favorito);
        return favorito;
    }

    /**
     * Datos de prueba para comprobar ciertas funcionalidades del presenter
     */
    private void gasolinerasDummy(){
        this.gasolineras.add(new Gasolinera(1000,"SANTANDER","SANTANDER", "Av Valdecilla", 1.299,1.359,"AVIA"));
        this.gasolineras.add(new Gasolinera(1053,"SANTANDER","SANTANDER", "Plaza Matias Montero", 0,1.349,"CAMPSA"));
        this.gasolineras.add(new Gasolinera(420,"SANTANDER","SANTANDER", "Area Arrabal Puerto de Raos", 0,1.279,"E.E.S.S. MAS, S.L."));
        this.gasolineras.add(new Gasolinera(9564,"SANTANDER","SANTANDER", "Av Parayas", 1.189,0,"EASYGAS"));
        this.gasolineras.add(new Gasolinera(1025,"SANTANDER","SANTANDER", "Calle el Empalme", 1.259,0,"CARREFOUR"));
        this.gasolineraFavoritaList.add(new GasolineraFavorita("Buena comida.",1000));
        this.gasolineraFavoritaList.add(new GasolineraFavorita("Buen trato.",1053));
        this.gasolineraFavoritaList.add(new GasolineraFavorita("Buena aparcamiento.",420));
        this.gasolineraFavoritaList.add(new GasolineraFavorita("Precios muy bajos.",9564));
        this.gasolineraFavoritaList.add(new GasolineraFavorita("Buen hospedaje.",1025));

    }
}
