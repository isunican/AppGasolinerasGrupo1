package com.isunican.proyectobase.Utilities;

import com.isunican.proyectobase.Model.Gasolinera;

import java.util.ArrayList;
import java.util.List;

/**
 * -----------------------------------------------------
 * Clase que extrae las maras de gasolineras,
 * ademas de filtrar dichas gasolineras por la marca
 * escogida.
 * -----------------------------------------------------
 *
 * @author Luis Cruz
 * @version 0.0.2
 */
public class ExtractorMarcasUtil {

    private ExtractorMarcasUtil(){}

    /**
     * Extrae una lista de marcas (rotulos) de la lista de gasolineras pasada como parametro
     * @param gasolineras de las que extraer las marcas
     * @return lista de las marcas de las gasolineras
     */
    public static List<String> extraeMarcas(List<Gasolinera> gasolineras){
        if(gasolineras == null) return new ArrayList<>();

        ArrayList<String> marcas = new ArrayList<>();
        for(Gasolinera g: gasolineras){
            if(!marcas.contains(g.getRotulo().trim()))marcas.add(g.getRotulo().trim());

        }
        return marcas;
    }

    /**
     * Filtra las gasolineras por la marca(rótulo) seleccionada.
     * @param marca escogida para filtrar
     * @param listaGasolineras lista de las gasolineras a filtrar
     * @return lista de gasolineras filtradas
     */
    public static List<Gasolinera> aplicaFiltro(String marca, List<Gasolinera> listaGasolineras){
        if(listaGasolineras == null) return new ArrayList<>();
        ArrayList<Gasolinera> listaActualizada = new ArrayList<>();
        for(Gasolinera g: listaGasolineras){
            if(g.getRotulo().trim().equalsIgnoreCase(marca.trim())) listaActualizada.add(g);
        }
        return listaActualizada;
    }


}
