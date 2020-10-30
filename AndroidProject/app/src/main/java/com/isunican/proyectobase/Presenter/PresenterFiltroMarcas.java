package com.isunican.proyectobase.Presenter;


import com.isunican.proyectobase.Model.Gasolinera;
import com.isunican.proyectobase.Utilities.BrandSearch;

import java.util.ArrayList;
import java.util.List;

/**
 * //TODO: Actualizar este comentario, no me gusta
 * Clase encargada de listar las marcas (rótulos) de gasolineras disponibles,
 * buscar en esa lista y devolver la lista actualizada de gasolineras.
 *
 * @author Luis Cruz
 * @version 0.0.1
 */
public class PresenterFiltroMarcas {

    private ArrayList<Gasolinera> listaGasolineras;
    private ArrayList<Gasolinera> listaActualizada;
    private ArrayList<String> marcas;

    /**
     * Constructor de la clase presenter
     * @param lista ArrayList: lista de gasolineras disponibles
     */
    public PresenterFiltroMarcas(ArrayList<Gasolinera> lista){
        this.listaActualizada = new ArrayList<>();
        this.listaGasolineras = lista;
        marcas = new ArrayList<>();
        for(Gasolinera g: lista){
            if(!marcas.contains(g.getRotulo()))marcas.add(g.getRotulo());
        }
    }

    /**
     * @return marcas(rótulos) de gasolineras
     */
    public List<String> getMarcas() {
        return marcas;
    }

    /**
     * Devuelve las marcas que coinciden con un filtro
     * @param filtro para aplicar a la lista de marcas
     * @return nueva lista de marcas
     */
    @Deprecated
    public List<String> buscaMarcas(String filtro){

        return BrandSearch.searchList(filtro, marcas);
    }

    /**
     * Aplica el filtro las gasolineras por la marca(rótulo) seleccionada.
     * @param marca escogida para filtrar
     * @return false si la marca no existe, true si la marca existe y la operacio se completa correctamente
     */
    private void aplicaFiltro(String marca){
        listaActualizada = new ArrayList<>();
        for(Gasolinera g: listaGasolineras){
            if(g.getRotulo().toLowerCase().equals(marca.toLowerCase())) listaActualizada.add(g);
        }
    }

    /**
     * Filtra las gasolineras por la marca seleccionada
     * @param marca  nombre completo de la marca seleccionada
     * @return lista original de gasolineras si no se selecciona ninguna gasolinera
     * @return lista actualizada de gasolineras si la operacion se completa correctamente
     */
    public List<Gasolinera> filtraGasolineras(String marca){
        if(marca.isEmpty()) return listaGasolineras;
        aplicaFiltro(marca);
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
