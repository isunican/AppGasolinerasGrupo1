package com.isunican.proyectobase.Utilities;

import com.isunican.proyectobase.Model.Gasolinera;
import com.isunican.proyectobase.Presenter.PresenterGasolineras;

import java.util.ArrayList;
import java.util.List;

/**
 * -----------------------------------------------------
 * Clase que realiza una búsqueda de las posibles marcas
 * de gasolineras en base a un input de búsqueda
 * -----------------------------------------------------
 *
 * @author Luis Cruz
 * @version 0.0.1
 */
public class BrandSearch {

    /**
    *Lee la lista y retorna los elementos de la lista que coinciden parcialmente
    *con el parámetro de búsqueda proporcionado
    *@param search String: parte del nombre de la marca(s) a buscar
     * @param list ArrayList: lista de las marcas a comparar
     * @returns List<String>: lista de marcas que coinciden con el parámetro de búsqueda
     */
    public static List<String> searchList(String search, ArrayList<String> list){

        ArrayList<String> searchResults = new ArrayList<>(); //Lista de resultados

        for( String s: list){
            if(s.toLowerCase().startsWith(search))searchResults.add(s);
        }

        return searchResults;
    }

}
