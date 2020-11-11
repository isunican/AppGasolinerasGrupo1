package com.isunican.proyectobase.Presenter;

import com.isunican.proyectobase.Model.Gasolinera;

import java.util.ArrayList;
import java.util.List;

public class PresenterGasolinerasFavoritas {

    List<Gasolinera> gasolinerasFavoritas;

    public PresenterGasolinerasFavoritas(){
        this.gasolinerasFavoritas = new ArrayList<>();
    }

    public Gasolinera eliminaGasolineraFavorita(int idGasolinera){

        Gasolinera gasolineraEliminar = null;

        for (int i = 0; i<gasolinerasFavoritas.size(); i++){
            if (gasolinerasFavoritas.get(i).getIdeess() == idGasolinera){
                gasolineraEliminar = gasolinerasFavoritas.get(i);
                gasolinerasFavoritas.remove(i);
            }
        }

        // TODO: Quitar gasolinera de la lista de la BD

        // TODO: Añadir mensaje toast en caso de éxito y de fallo
        
        return gasolineraEliminar;
    }
}
