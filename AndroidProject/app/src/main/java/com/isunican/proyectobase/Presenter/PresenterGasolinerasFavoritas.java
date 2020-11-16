package com.isunican.proyectobase.Presenter;

import android.content.Context;

import com.isunican.proyectobase.DAO.GasolineraDAO;
import com.isunican.proyectobase.DAO.GasolineraFavoritaDAO;
import com.isunican.proyectobase.Model.Gasolinera;
import com.isunican.proyectobase.Model.GasolineraFavorita;
import com.isunican.proyectobase.Utilities.CommonUtils;
import com.isunican.proyectobase.Utilities.ExtractorMarcasUtil;
import com.isunican.proyectobase.Utilities.ExtractorLocalidadUtil;

import java.util.ArrayList;
import java.util.List;

import static com.isunican.proyectobase.Presenter.PresenterGasolineras.SANTANDER;

/**
 * Clase que gestiona las gasolineras favoritas del usuario,
 * apoyandose para ello en una base de datos, accesible mediante
 * sus DAO
 *
 * @author Luis Cruz
 * @author Elena Romón
 * @author Adrian Celis
 *
 * @version 0.0.1
 */
public class PresenterGasolinerasFavoritas {

    ArrayList<Gasolinera> listaOriginal; //Lista de gasolineras favoritas


    /**
     * Crea el presenter, inicializando la lista de gasolineras favoritas
     *
     */
    public PresenterGasolinerasFavoritas(){
        listaOriginal = new ArrayList<>();
        //Cargar datos de la BD

        gasolinerasDummy();
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


    public Gasolinera eliminaGasolineraFavorita(Gasolinera gasolinera){

        // hago cosas
        return gasolinera;
    }

    /**
     * @return Devuelve la lista de gasolineras favoritas
     */
    public List<Gasolinera> getGasolinerasFavoritas(){
        System.out.println("Gasolineras pasadas");
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
     *
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

    /**
     * Datos de prueba para comprobar ciertas funcionalidades del presenter
     */
    private void gasolinerasDummy(){
        this.listaOriginal.add(new Gasolinera(1000,SANTANDER,SANTANDER, "Av Valdecilla", 1.299,1.359,"AVIA"));
        this.listaOriginal.add(new Gasolinera(1053,SANTANDER,SANTANDER, "Plaza Matias Montero", 0,1.349,"CAMPSA"));
        this.listaOriginal.add(new Gasolinera(420,SANTANDER,SANTANDER, "Area Arrabal Puerto de Raos", 0,1.279,"E.E.S.S. MAS, S.L."));
        this.listaOriginal.add(new Gasolinera(9564,SANTANDER,SANTANDER, "Av Parayas", 1.189,0,"EASYGAS"));
        this.listaOriginal.add(new Gasolinera(1025,SANTANDER,SANTANDER, "Calle el Empalme", 1.259,0,"CARREFOUR"));
    }
}
