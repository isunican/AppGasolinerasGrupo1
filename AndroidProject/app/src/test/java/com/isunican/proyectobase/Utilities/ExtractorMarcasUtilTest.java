package com.isunican.proyectobase.Utilities;

import com.isunican.proyectobase.Model.Gasolinera;

import org.junit.*;

import java.util.ArrayList;

/**
 * Clase de tests que contiene las pruebas unitarias de la utilidad Brand Extractor
 * @author Luis Cruz
 * @version 0.0.2
 */
public class ExtractorMarcasUtilTest {

    static ArrayList<Gasolinera> gasolineras;
    static ArrayList<Gasolinera> filtradas;

    /*
     * Preparacion de las listas usadas para los tests
     */
    @Before
    public void setup(){
        gasolineras = new ArrayList<>();
        filtradas = new ArrayList<>();
        this.gasolineras.add(new Gasolinera(1000,"Santander","Santander", "Av Valdecilla", 1.299,1.359,"AVIA"));
        this.gasolineras.add(new Gasolinera(1053,"Santander","Santander", "Plaza Matias Montero", 1.270,1.349,"CAMPSA"));
        this.gasolineras.add(new Gasolinera(420,"Santander","Santander", "Area Arrabal Puerto de Raos", 1.249,1.279,"CAMPSA"));
        this.gasolineras.add(new Gasolinera(9564,"Santander","Santander", "Av Parayas", 1.189,1.269,"EASYGAS"));
        this.gasolineras.add(new Gasolinera(1025,"Santander","Santander", "Calle el Empalme", 1.259,1.319,"CARREFOUR"));
        this.filtradas.add(gasolineras.get(1));
        this.filtradas.add(gasolineras.get(2));


    }

    /*
    *Test del método extractBrands
     */
    @Test
    public void testExtractBrands(){
        //Caso de prueba 1: funcionamiento normal
        String[] brands = new String[]{"AVIA","CAMPSA","EASYGAS","CARREFOUR"};
        ArrayList<String> marcas = (ArrayList<String>) ExtractorMarcasUtil.extraeMarcas(gasolineras);

        Assert.assertArrayEquals(brands, marcas.toArray());

        //Caso de prueba 2: Lista de gasolineras vacía
        marcas = (ArrayList<String>) ExtractorMarcasUtil.extraeMarcas(new ArrayList<Gasolinera>());

        Assert.assertTrue(marcas.isEmpty());

        //Caso de prueba 3: Lista de gasolineras nula
        marcas = (ArrayList<String>) ExtractorMarcasUtil.extraeMarcas(null);
        Assert.assertTrue(marcas.isEmpty());

    }

    /*
    *Test del método applyFiter
    */
    @Test
    public void testApplyFilter(){
        //Caso de prueba 1: funcionamiento normal
        ArrayList<Gasolinera> filtered = (ArrayList<Gasolinera>) ExtractorMarcasUtil.aplicaFiltro("CAMPSA", gasolineras);
        Assert.assertArrayEquals(filtered.toArray(),filtradas.toArray());

        filtered = (ArrayList<Gasolinera>) ExtractorMarcasUtil.aplicaFiltro("campsa", gasolineras);
        Assert.assertArrayEquals(filtered.toArray(),filtradas.toArray());

        filtered = (ArrayList<Gasolinera>) ExtractorMarcasUtil.aplicaFiltro("Campsa", gasolineras);
        Assert.assertArrayEquals(filtered.toArray(),filtradas.toArray());

        //Caso de prueba 2: marca no existente

        Assert.assertTrue(ExtractorMarcasUtil.aplicaFiltro("REPSOL",gasolineras).isEmpty());
        Assert.assertTrue(ExtractorMarcasUtil.aplicaFiltro("repsol",gasolineras).isEmpty());
        Assert.assertTrue(ExtractorMarcasUtil.aplicaFiltro("Repsol",gasolineras).isEmpty());

        //Caso de prueba 3: lista vacía
        Assert.assertTrue(ExtractorMarcasUtil.aplicaFiltro("AVIA", new ArrayList<Gasolinera>()).isEmpty());

        //Caso de prueba 4: lista nula
        Assert.assertTrue(ExtractorMarcasUtil.aplicaFiltro("AVIA", null).isEmpty());

    }
}
