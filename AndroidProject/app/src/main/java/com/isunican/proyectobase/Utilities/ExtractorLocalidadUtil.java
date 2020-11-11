package com.isunican.proyectobase.Utilities;

import com.isunican.proyectobase.Model.Gasolinera;

import java.util.ArrayList;
import java.util.List;

public class ExtractorLocalidadUtil {

    public static List<String> extraeLocalidades(List<Gasolinera> gasolineras){
        ArrayList<String> localidades = new ArrayList<>();

        for(Gasolinera g: gasolineras){
            if(!localidades.contains(g.getLocalidad().trim())) localidades.add(g.getLocalidad().trim());
        }

        return localidades;
    }

    public static List<Gasolinera> aplicaFiltro(String localidad, List<Gasolinera> gasolineras){
        ArrayList<Gasolinera> filtradas = new ArrayList<>();

        for(Gasolinera g: gasolineras){
            if(g.getLocalidad().equalsIgnoreCase(localidad))filtradas.add(g);
        }

        return  filtradas;
    }

}
