package com.isunican.proyectobase.Utilities;

import com.isunican.proyectobase.Model.Gasolinera;
import com.isunican.proyectobase.Presenter.PresenterGasolineras;

import java.util.ArrayList;
import java.util.List;

/**
 * -----------------------------------------------------
 * Clase que realiza extrae las maras de gasolineras,
 * además de filtrar dichas gasolineras por la marca
 * escogida.
 * -----------------------------------------------------
 *
 * @author Luis Cruz
 * @version 0.0.2
 */
public class BrandExtractorUtil {

    /**
     * Extrae una lista de marcas (rótulos) de la lista de gasolineras pasada como parámetro
     * @param gasolineras de las que extraer las marcas
     * @return lista de las marcas de las gasolineras
     */
    public static List<String> extractBrands(ArrayList<Gasolinera> gasolineras){
        ArrayList<String> marcas = new ArrayList<>();
        for(Gasolinera g: gasolineras){
            if(!marcas.contains(g.getRotulo().trim()))marcas.add(g.getRotulo().trim());

        }
        return marcas;
    }

    /**
     * Aplica el filtro las gasolineras por la marca(rótulo) seleccionada.
     * @param marca escogida para filtrar
     * @param listaGasolineras lista de las gasolineras a filtrar
     * @return false si la marca no existe, true si la marca existe y la operacion se completa correctamente
     */
    public static List<Gasolinera> applyFilter(String marca, ArrayList<Gasolinera> listaGasolineras){
        ArrayList<Gasolinera> listaActualizada = new ArrayList<>();
        for(Gasolinera g: listaGasolineras){
            if(g.getRotulo().trim().toLowerCase().equals(marca.toLowerCase().trim())) listaActualizada.add(g);
        }
        return listaActualizada;
    }


}
