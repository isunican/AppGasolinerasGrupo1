package com.isunican.proyectobase.Utilities;


import com.isunican.proyectobase.Model.Gasolinera;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Clase de tests que contiene las pruebas unitarias de la utilidad ExtractorLocalidadUtil
 * @author Carol Corales
 */
public class ExtractorLocalidadUtilTest {

    //Lista de gasolineras de la que se dispone
    static ArrayList<Gasolinera> gasolineras;
    //Lista de gasolineras filtrada de la anterior
    static ArrayList<Gasolinera> filtradasLocalidad;

    /*
     * Preparacion de las listas usadas para los tests
     */
    @Before
    public  void setup(){
        gasolineras = new ArrayList<>();
        filtradasLocalidad = new ArrayList<>();
        //TODO: añadir gasolineras de localidades distintas
        this.gasolineras.add(new Gasolinera(1000,"Santander","Santander", "Av Valdecilla", 1.299,1.359,"AVIA"));
        this.gasolineras.add(new Gasolinera(1053,"Santander","Santander", "Plaza Matias Montero", 1.270,1.349,"CAMPSA"));
        this.gasolineras.add(new Gasolinera(420,"Santander","Santander", "Area Arrabal Puerto de Raos", 1.249,1.279,"CAMPSA"));
        this.gasolineras.add(new Gasolinera(9564,"Santander","Santander", "Av Parayas", 1.189,1.269,"EASYGAS"));
        this.gasolineras.add(new Gasolinera(1025,"Santander","Santander", "Calle el Empalme", 1.259,1.319,"CARREFOUR"));
        this.filtradasLocalidad.add(gasolineras.get(1));
        this.filtradasLocalidad.add(gasolineras.get(2));
    }
    @Test
    public void aplicaFiltroTest(){
        //Caso 1
    }
   @Test
   public void extraeLocalidadesTest(){

   }
}

