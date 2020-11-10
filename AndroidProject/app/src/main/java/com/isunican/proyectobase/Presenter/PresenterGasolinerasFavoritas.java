package com.isunican.proyectobase.Presenter;

import com.isunican.proyectobase.Model.Gasolinera;
import com.isunican.proyectobase.Utilities.BrandExtractorUtil;
import com.isunican.proyectobase.Utilities.ExtractorLocalidadUtil;

import java.util.ArrayList;
import java.util.List;

import static com.isunican.proyectobase.Presenter.PresenterGasolineras.SANTANDER;

public class PresenterGasolinerasFavoritas {

    private ArrayList<Gasolinera> gasolineras;

    public PresenterGasolinerasFavoritas(){
        gasolineras= new ArrayList<>();
        //Cargar datos de la BD
        gasolinerasDummy();
    }


    public Gasolinera eliminaGasolineraFavorita(Gasolinera gasolinera){

        // hago cosas
        return gasolinera;
    }

    public List<Gasolinera> getGasolinerasFavoritas(){
        return gasolineras;
    }

    public List<Gasolinera> filtrarGasolinerasFavMarca(String marca){
        return BrandExtractorUtil.applyFilter(marca, gasolineras);
    }

    public List<String> getMarcasFavoritas(){
        return BrandExtractorUtil.extractBrands(gasolineras);
    }

    public List<Gasolinera> filtrarGasolinerasFavLocal(String localidad){
        return ExtractorLocalidadUtil.aplicaFiltro(localidad, gasolineras);
    }

    public List<String> getLocalidadesFavoritas(){
        return ExtractorLocalidadUtil.extraeLocalidades(gasolineras);
    }

    private void gasolinerasDummy(){
        this.gasolineras.add(new Gasolinera(1000,SANTANDER,SANTANDER, "Av Valdecilla", 1.299,1.359,"AVIA"));
        this.gasolineras.add(new Gasolinera(1053,SANTANDER,SANTANDER, "Plaza Matias Montero", 0,1.349,"CAMPSA"));
        this.gasolineras.add(new Gasolinera(420,SANTANDER,SANTANDER, "Area Arrabal Puerto de Raos", 0,1.279,"E.E.S.S. MAS, S.L."));
        this.gasolineras.add(new Gasolinera(9564,SANTANDER,SANTANDER, "Av Parayas", 1.189,0,"EASYGAS"));
        this.gasolineras.add(new Gasolinera(1025,SANTANDER,SANTANDER, "Calle el Empalme", 1.259,0,"CARREFOUR"));
    }

}
