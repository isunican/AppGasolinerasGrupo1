package com.isunican.proyectobase.Utilities;


import com.isunican.proyectobase.Model.Gasolinera;


import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

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
        this.gasolineras.add(new Gasolinera(1053,"Santander","Santander", "Plaza Matias Montero", 1.270,1.349,"AVIA"));
        this.filtradasLocalidad.add(gasolineras.get(0));
        this.filtradasLocalidad.add(gasolineras.get(1));
    }
    @Test
    public void aplicaFiltroTest(){
        ArrayList<Gasolinera> listaFiltrada;
        //Caso 1: Filtro con localidad existente y lista de gasolineras
        listaFiltrada = (ArrayList<Gasolinera>) ExtractorLocalidadUtil.aplicaFiltro("AVIA", gasolineras);

        //Caso 2: localidad no existente, Lista de gasolineras
        assertTrue(ExtractorLocalidadUtil.aplicaFiltro("Torrelavega", gasolineras).isEmpty());
        //Caso 3: lista vacia
        assertTrue(ExtractorLocalidadUtil.aplicaFiltro("Santander", new ArrayList<Gasolinera>()).isEmpty());
        //Caso 4: Lista nula
        assertTrue(ExtractorLocalidadUtil.aplicaFiltro("Santander", null).isEmpty());
    }
   @Test
   public void extraeLocalidadesTest(){


   }
}

