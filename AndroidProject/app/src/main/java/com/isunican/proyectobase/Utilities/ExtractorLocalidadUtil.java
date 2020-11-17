package com.isunican.proyectobase.Utilities;

import com.isunican.proyectobase.Model.Gasolinera;

import java.util.ArrayList;
import java.util.List;

/**
 * -----------------------------------------------------
 * Clase que extrae las localidades de gasolineras,
 * ademas de filtrar dichas gasolineras por la localidad
 * escogida.
 * -----------------------------------------------------
 *
 * @author Luis Cruz
 * @version 0.0.1
 */
public class ExtractorLocalidadUtil {

    private ExtractorLocalidadUtil(){}

    /**
     * Extrae una lista de localidades de la lista de gasolineras pasada como parametro
     * @param gasolineras de las que extraer las localidades
     * @return lista de las localidades de las gasolineras
     */
    public static List<String> extraeLocalidades(List<Gasolinera> gasolineras){
        ArrayList<String> localidades = new ArrayList<>();

        for(Gasolinera g: gasolineras){
            if(!localidades.contains(g.getLocalidad().trim())) localidades.add(g.getLocalidad().trim());
        }

        return localidades;
    }

    /**
     * Filtra las gasolineras por la localidad seleccionada.
     * @param localidad escogida para filtrar
     * @param listaGasolineras lista de las gasolineras a filtrar
     * @return lista de las gasolineras filtradas
     */
    public static List<Gasolinera> aplicaFiltro(String localidad, List<Gasolinera> listaGasolineras){
        ArrayList<Gasolinera> filtradas = new ArrayList<>();
        if(listaGasolineras == null){ return filtradas; }
        for(Gasolinera g: listaGasolineras){
            if(g.getLocalidad().equalsIgnoreCase(localidad))filtradas.add(g);
        }

        return  filtradas;
    }

}
