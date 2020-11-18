package com.isunican.proyectobase.Utilities;


import com.isunican.proyectobase.Model.Gasolinera;


import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.*;

/**
 * Clase de tests que contiene las pruebas unitarias de la utilidad ExtractorLocalidadUtil
 * @author Carol Corales
 */
public class ExtractorLocalidadUtilTest {

    //Lista de gasolineras de la que se dispone
    static ArrayList<Gasolinera> gasolineras;
    //Lista de prueba de gasolineras filtradas
    static ArrayList<Gasolinera> filtradasLocalidad;
    //Lista de localidades
    ArrayList<String> localidades;
    //Lista a la que se le aplica el filtro
    ArrayList<Gasolinera> listaFiltrada;

    /*
     * Preparacion de las listas usadas para los tests
     */
    @Before
    public  void setup(){
        gasolineras = new ArrayList<>();
        filtradasLocalidad = new ArrayList<>();
        localidades = new ArrayList<String>();

        //Lista de gasolineras
        this.gasolineras.add(new Gasolinera(1000,"Santander","Santander", "Av Valdecilla", 1.289,1.059,"CEPSA"));
        this.gasolineras.add(new Gasolinera(1020,"Santander","Santander", "Plaza Matias Montero", 1.370,1.049,"CEPSA"));
        this.gasolineras.add(new Gasolinera(1030,"Santander","Santander", "Plaza Matias Montero", 1.270,1.349,"AVIA"));
        this.gasolineras.add(new Gasolinera(1040,"Colindres","Santander", "Calle Melendez Pelayo", 1.110,1.281,"CARREFOUR"));
        this.gasolineras.add(new Gasolinera(1050,"Colindres","Santander", "Calle Santander", 1.270,1.349,"EASYGAS"));
        this.gasolineras.add(new Gasolinera(1060,"Maliaño","Santander", "Calle Maliaño", 1.070,1.149,"CAMPSA"));

        //Gasolineras que tienen la misma localidad
        this.filtradasLocalidad.add(new Gasolinera(1000,"Santander","Santander", "Av Valdecilla", 1.289,1.059,"CEPSA"));
        this.filtradasLocalidad.add(new Gasolinera(1020,"Santander","Santander", "Plaza Matias Montero", 1.370,1.049,"CEPSA"));
        this.filtradasLocalidad.add(new Gasolinera(1030,"Santander","Santander", "Plaza Matias Montero", 1.270,1.349,"AVIA"));

        //Localidades existentes
        localidades.add("Santander");
        localidades.add("Colindres");
        localidades.add("Maliaño");
    }
    @Test
    public void aplicaFiltroTest(){

        //Caso 1: Filtro con localidad existente, lista de gasolineras
        listaFiltrada = (ArrayList<Gasolinera>) ExtractorLocalidadUtil.aplicaFiltro("Santander", gasolineras);
        assertTrue(Arrays.equals(listaFiltrada.toArray(), filtradasLocalidad.toArray()));

        //Caso 2: localidad no existente, Lista de gasolineras
        assertTrue(ExtractorLocalidadUtil.aplicaFiltro("Torrelavega", gasolineras).isEmpty());

        //Caso 3: lista vacia
        assertTrue(ExtractorLocalidadUtil.aplicaFiltro("Santander", new ArrayList<Gasolinera>()).isEmpty());

        //Caso 4: Lista nula
        assertTrue(ExtractorLocalidadUtil.aplicaFiltro("Santander", null).isEmpty());
    }
   @Test
   public void extraeLocalidadesTest(){

        //Caso 1: Lista de gasolineras
      assertTrue(Arrays.equals(localidades.toArray(), ExtractorLocalidadUtil.extraeLocalidades(gasolineras).toArray()));
       //Caso 2: Lista vacia
       assertTrue(ExtractorLocalidadUtil.extraeLocalidades(Collections.<Gasolinera>emptyList()).isEmpty());
       //Caso 3: Lista nula
       assertTrue(ExtractorLocalidadUtil.extraeLocalidades(null).isEmpty());
   }
}

