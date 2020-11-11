package com.isunican.proyectobase.Presenter;

import android.content.Context;

import com.isunican.proyectobase.Database.AppDatabase;
import com.isunican.proyectobase.Model.Gasolinera;
import com.isunican.proyectobase.Model.GasolineraFavorita;
import com.isunican.proyectobase.Utilities.BrandExtractorUtil;
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

    private ArrayList<Gasolinera> gasolineras; //Lista de gasolineras favoritas
    Context contexto; //Contexto de la aplicación (Necesario para acceder a la BD)

    /**
     * Crea el presenter, inicializando la lista de gasolineras favoritas
     * @param contexto de la aplicación (Necesario para la BD)
     */
    public PresenterGasolinerasFavoritas(Context contexto){
        gasolineras= new ArrayList<>();
        //Cargar datos de la BD

        this.contexto = contexto;

        //gasolinerasDummy();
    }

    /**
     * Carga las gasolineras favoritas del usuario desde la base de datos
     */
    public void cargaGasolineras(){
        ArrayList<GasolineraFavorita> favoritas = (ArrayList<GasolineraFavorita>) AppDatabase.getInstance(contexto).gasolineraFavoritaDAO().getAll();
        for(GasolineraFavorita g: favoritas){
            gasolineras.add(AppDatabase.getInstance(contexto).gasolineraDAO().findById(g.getIdGasolinera()).get(0));
        }
        System.out.println("Gasolineras cargadas "+ favoritas.size());
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
        return gasolineras;
    }

    /**
     * Filtra las gasolineras favoritas por la marca
     * @param marca por la que filtrar las gasolineras favoritas
     * @return la lista de gasolineras filtradas
     */
    public List<Gasolinera> filtrarGasolinerasFavMarca(String marca){
        return BrandExtractorUtil.applyFilter(marca, gasolineras);
    }

    /**
     * @return Devuelve una lista con las marcas de las gasolineras guardadas como favoritas
     */
    public List<String> getMarcasFavoritas(){
        return BrandExtractorUtil.extractBrands(gasolineras);
    }

    /**
     * Filtra las gasolineras favoritas por la localidad
     * @param localidad por la que filtrar las gasolineras favoritas
     * @return la lista de gasolineras filtradas
     */
    public List<Gasolinera> filtrarGasolinerasFavLocal(String localidad){
        return ExtractorLocalidadUtil.aplicaFiltro(localidad, gasolineras);
    }

    /**
     *
     * @return Devuelve una lista con las localidades de las gasolineras guardadas como favoritas
     */
    public List<String> getLocalidadesFavoritas(){
        return ExtractorLocalidadUtil.extraeLocalidades(gasolineras);
    }

    /**
     * Datos de prueba para comprobar ciertas funcionalidades del presenter
     */
    private void gasolinerasDummy(){
        this.gasolineras.add(new Gasolinera(1000,SANTANDER,SANTANDER, "Av Valdecilla", 1.299,1.359,"AVIA"));
        this.gasolineras.add(new Gasolinera(1053,SANTANDER,SANTANDER, "Plaza Matias Montero", 0,1.349,"CAMPSA"));
        this.gasolineras.add(new Gasolinera(420,SANTANDER,SANTANDER, "Area Arrabal Puerto de Raos", 0,1.279,"E.E.S.S. MAS, S.L."));
        this.gasolineras.add(new Gasolinera(9564,SANTANDER,SANTANDER, "Av Parayas", 1.189,0,"EASYGAS"));
        this.gasolineras.add(new Gasolinera(1025,SANTANDER,SANTANDER, "Calle el Empalme", 1.259,0,"CARREFOUR"));
    }
}
