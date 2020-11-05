package com.isunican.proyectobase.Presenter;


import com.isunican.proyectobase.Model.Gasolinera;
import com.isunican.proyectobase.Utilities.BrandExtractorUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ---------------------------------------------------------------------------
 * Clase encargada de listar las marcas (rotulos) de gasolineras disponibles,
 * filtrar la lista de gasolineras por una marca escogida y devolver la lista
 * actualizada de gasolineras conteniendo solo las que concuerdan con la marca
 * especificada, si las hay
 *----------------------------------------------------------------------------
 *
 * @author Luis Cruz
 * @version 0.0.2
 */
public class PresenterFiltroMarcas {

    private ArrayList<Gasolinera> listaGasolineras;
    private ArrayList<Gasolinera> listaActualizada;
    private ArrayList<String> marcas;

    /**
     * Constructor de la clase presenter
     * @param lista ArrayList: lista de gasolineras disponibles
     */
    public PresenterFiltroMarcas(List<Gasolinera> lista){
        this.listaActualizada = new ArrayList<>();
        this.listaGasolineras = new ArrayList<>(lista);
        marcas = (ArrayList<String>) BrandExtractorUtil.extractBrands(listaGasolineras);
        Collections.sort(marcas);
        marcas.add(0,"(Ninguno)");
    }

    /**
     * @return marcas(rotulos) de gasolineras
     */
    public List<String> getMarcas() {
        return marcas;
    }


    /**
     * Filtra las gasolineras por la marca seleccionada
     * @param marca  nombre completo de la marca seleccionada
     * @return lista original de gasolineras si no se selecciona ninguna gasolinera o la marca no existe
     * @return lista actualizada de gasolineras si la operacion se completa correctamente
     */
    public List<Gasolinera> filtraGasolineras(String marca){
        listaActualizada = (ArrayList<Gasolinera>) BrandExtractorUtil.applyFilter(marca,listaGasolineras);
        if(listaActualizada.isEmpty()) return listaGasolineras;
        return listaActualizada;
    }

    /**
     * @return lista original de gasolineras
     */
    public List<Gasolinera> getListaGasolineras(){
        return listaGasolineras;
    }
}
