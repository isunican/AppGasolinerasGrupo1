package com.isunican.proyectobase.Presenter;

import android.content.Context;
import com.isunican.proyectobase.DAO.GasolineraFavoritaDAO;
import com.isunican.proyectobase.Utilities.BrandExtractorUtil;
import com.isunican.proyectobase.Utilities.ExtractorLocalidadUtil;

import static com.isunican.proyectobase.Presenter.PresenterGasolineras.SANTANDER;
import com.isunican.proyectobase.Database.AppDatabase;
import com.isunican.proyectobase.Model.Gasolinera;
import com.isunican.proyectobase.Model.GasolineraFavorita;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

    private List<GasolineraFavorita> gasolineraFavoritaList;
    
       /**
     * Crea el presenter, inicializando la lista de gasolineras favoritas
     * @param contexto de la aplicación (Necesario para la BD)
     */
    public PresenterGasolinerasFavoritas(Context contexto){
        gasolineras= new ArrayList<>();        //Cargar datos de la BD
        gasolineraFavoritaList = new ArrayList<>();

        this.contexto = contexto;

        //gasolinerasDummy();
    }

    public List<GasolineraFavorita> getListaGasolinerasFavoritas() { return gasolineraFavoritaList; }
    public void setListaGasolinerasFavoritas(List<GasolineraFavorita> gasolineraFavoritaList) {
        this.gasolineraFavoritaList = gasolineraFavoritaList;
    }

    public GasolineraFavorita getGasolineraFavoritaPorId(int id, GasolineraFavoritaDAO gasolineraFavoritaDAO){
        if (gasolineraFavoritaDAO == null) return null;
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
    public void cargaGasolineras(){
        ArrayList<GasolineraFavorita> favoritas = (ArrayList<GasolineraFavorita>) AppDatabase.getInstance(contexto).gasolineraFavoritaDAO().getAll();
        for(GasolineraFavorita g: favoritas){
            gasolineras.add(AppDatabase.getInstance(contexto).gasolineraDAO().findById(g.getIdGasolinera()).get(0));
        }
        System.out.println("Gasolineras cargadas "+ favoritas.size());
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