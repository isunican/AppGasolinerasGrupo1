package com.isunican.proyectobase.Presenter;

import com.isunican.proyectobase.DAO.GasolineraFavoritaDAO;
import com.isunican.proyectobase.Utilities.ExtractorLocalidadUtil;
import com.isunican.proyectobase.DAO.GasolineraDAO;
import com.isunican.proyectobase.Model.Gasolinera;
import com.isunican.proyectobase.Model.GasolineraFavorita;
import com.isunican.proyectobase.Utilities.CommonUtils;
import com.isunican.proyectobase.Utilities.ExtractorMarcasUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.logging.Logger;

/**
 * Clase que gestiona las gasolineras favoritas del usuario,
 * apoyandose para ello en una base de datos, accesible mediante
 * sus DAO
 *
 * @author Luis Cruz
 * @author Elena Romon
 * @author Jaime Lopez-Agudo
 *
 * @version 0.0.1
 */
public class PresenterGasolinerasFavoritas {

    ArrayList<Gasolinera> listaOriginal; //Lista de gasolineras favoritas
    private static final Logger LOGGER=Logger.getLogger(PresenterGasolinerasFavoritas.class.getName());

    private List<GasolineraFavorita> gasolineraFavoritaList;

    /**
     * Crea el presenter, inicializando la lista de gasolineras favoritas
     *
     */
    public PresenterGasolinerasFavoritas(){
        //Cargar datos de la BD
        gasolineraFavoritaList = new ArrayList<>();
        listaOriginal = new ArrayList<>();
    }

    public List<GasolineraFavorita> getListaGasolinerasFavoritas() { return gasolineraFavoritaList; }
    public void setListaGasolinerasFavoritas(List<GasolineraFavorita> gasolineraFavoritaList) {
        this.gasolineraFavoritaList = gasolineraFavoritaList;
    }

    public GasolineraFavorita getGasolineraFavoritaPorId(int id, GasolineraFavoritaDAO gasolineraFavoritaDAO){
        if (gasolineraFavoritaDAO == null) {
            return null;
        }
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
     * @param gasolinera gasolinera a eliminar
     * @return Gasolinera gasolinera eliminada
     */
    public GasolineraFavorita eliminaGasolineraFavorita(Gasolinera gasolinera, GasolineraDAO gasolineraDAO,
                                                        GasolineraFavoritaDAO gasolineraFavoritaDAO){

        GasolineraFavorita gasolineraFavorita = null;

        if (gasolineraFavoritaDAO != null && gasolineraDAO != null) {
            gasolineraFavorita = getGasolineraFavoritaPorId(gasolinera.getId(), gasolineraFavoritaDAO);
            if (gasolineraFavorita != null) {
                gasolineraFavoritaList.remove(gasolineraFavorita);
                gasolineraFavoritaDAO.delete(gasolineraFavorita);
                gasolineraDAO.delete(gasolinera);
            }
        }
        return gasolineraFavorita;
    }


    public GasolineraFavorita anhadirGasolineraFavorita(int idGasolinera, String comentario, GasolineraFavoritaDAO gasolineraFavoritaDAO){
        if (gasolineraFavoritaDAO == null) return null;
        GasolineraFavorita favorito=new GasolineraFavorita(comentario,idGasolinera);
        long rowid = gasolineraFavoritaDAO.insertOne(favorito);
        favorito.setId(gasolineraFavoritaDAO.getIdFromRowId(rowid));
        gasolineraFavoritaList.add(favorito);
        return favorito;
}
    
    public GasolineraFavorita modificarGasolineraFavorita(int idGasolinera, String comentario, GasolineraFavoritaDAO gasolineraFavoritaDAO){
        if (gasolineraFavoritaDAO == null) return null;
        GasolineraFavorita g = null;
        boolean encontrado = false;
        gasolineraFavoritaList.addAll(gasolineraFavoritaDAO.getAll());
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
    
    /**
     * Carga las gasolineras favoritas del usuario desde la base de datos
     */
    public void cargaGasolineras(GasolineraDAO gasolineraDAO, GasolineraFavoritaDAO gasolineraFavoritaDAO){
        ArrayList<GasolineraFavorita> favoritas = (ArrayList<GasolineraFavorita>) gasolineraFavoritaDAO.getAll();
        for(GasolineraFavorita g: favoritas){
            listaOriginal.add(gasolineraDAO.findById(g.getIdGasolinera()).get(0));
        }
    }

    /**
     * @return Devuelve la lista de gasolineras favoritas
     */
    public List<Gasolinera> getGasolinerasFavoritas(){
        LOGGER.info("Estoy cogiendo las gasolineras");
        return listaOriginal;
    }

    
    /**
     * Filtra las gasolineras favoritas por la marca
     * @param marca por la que filtrar las gasolineras favoritas
     * @return la lista de gasolineras filtradas
     */
    public List<Gasolinera> filtrarGasolinerasFavMarca(String marca){
        ArrayList<Gasolinera> listaActualizada = (ArrayList<Gasolinera>) ExtractorMarcasUtil.aplicaFiltro(marca, listaOriginal);
        if(listaActualizada.isEmpty())return listaOriginal;
        return listaActualizada;
    }

    /**
     * @return Devuelve una lista con las marcas de las gasolineras guardadas como favoritas
     */
    public List<String> getMarcasFavoritas(){
        ArrayList<String> marcas = (ArrayList<String>) ExtractorMarcasUtil.extraeMarcas(listaOriginal);
        CommonUtils.sortStringList(marcas);
        return marcas;
    }

    /**
     * Filtra las gasolineras favoritas por la localidad
     * @param localidad por la que filtrar las gasolineras favoritas
     * @return la lista de gasolineras filtradas
     */
    public List<Gasolinera> filtrarGasolinerasFavLocal(String localidad){
        ArrayList<Gasolinera> listaActualizada = (ArrayList<Gasolinera>) ExtractorLocalidadUtil.aplicaFiltro(localidad, listaOriginal);
        if(listaActualizada.isEmpty()) return listaOriginal;
        return listaActualizada;
    }

    /**
     * @return Devuelve una lista con las localidades de las gasolineras guardadas como favoritas
     */
    public List<String> getLocalidadesFavoritas(){
        ArrayList<String> localidades = (ArrayList<String>) ExtractorLocalidadUtil.extraeLocalidades(listaOriginal);
        CommonUtils.sortStringList(localidades);
        return localidades;
    }

    /**
     * Filtra las gasolineras favoritas tanto por marca como por localidad
     * @param marca por la que filtrar
     * @param localidad por la que filtrar
     * @return lista filtrada acorde a los 2 parámetros
     */
    public List<Gasolinera> filtraGasolinerasFavAmbos(String marca, String localidad){
        ArrayList<Gasolinera> listaActualizada = (ArrayList<Gasolinera>) ExtractorMarcasUtil.aplicaFiltro(marca, listaOriginal);
        if(!listaActualizada.isEmpty())listaActualizada = (ArrayList<Gasolinera>) ExtractorLocalidadUtil.aplicaFiltro(localidad, listaActualizada);

        if(listaActualizada.isEmpty()) return listaOriginal;
        return listaActualizada;
    }
}