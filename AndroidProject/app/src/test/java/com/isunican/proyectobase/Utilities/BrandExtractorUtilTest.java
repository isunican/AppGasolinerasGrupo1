package com.isunican.proyectobase.Utilities;

import com.isunican.proyectobase.Model.Gasolinera;

import org.junit.*;

import java.util.ArrayList;

public class BrandExtractorUtilTest {

    static ArrayList<Gasolinera> gasolineras;
    static ArrayList<Gasolinera> filtradas;

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
        ArrayList<String> marcas = (ArrayList<String>) BrandExtractorUtil.extractBrands(gasolineras);

        Assert.assertArrayEquals(brands, marcas.toArray());

        //Caso de prueba 2: Lista de gasolineras vacía
        marcas = (ArrayList<String>) BrandExtractorUtil.extractBrands(new ArrayList<Gasolinera>());

        Assert.assertTrue(marcas.isEmpty());


    }

    /*
    *Test del método applyFiter
    * TODO: Descubrir por qué aunque los objetos tienen el mismo contenido, los tests fallan
     */
    @Test
    public void testApplyFilter(){
        //Caso de prueba 1: funcionamiento normal
        ArrayList<Gasolinera> filtered = (ArrayList<Gasolinera>) BrandExtractorUtil.applyFilter("CAMPSA", gasolineras);

        Assert.assertArrayEquals(filtered.toArray(),filtradas.toArray());

        filtered = (ArrayList<Gasolinera>) BrandExtractorUtil.applyFilter("campsa", gasolineras);

        Assert.assertArrayEquals(filtered.toArray(),filtradas.toArray());

        filtered = (ArrayList<Gasolinera>) BrandExtractorUtil.applyFilter("Campsa", gasolineras);

        Assert.assertArrayEquals(filtered.toArray(),filtradas.toArray());

        //Caso de prueba 2: marca no existente

        Assert.assertTrue(BrandExtractorUtil.applyFilter("REPSOL",gasolineras).isEmpty());
        Assert.assertTrue(BrandExtractorUtil.applyFilter("repsol",gasolineras).isEmpty());
        Assert.assertTrue(BrandExtractorUtil.applyFilter("Repsol",gasolineras).isEmpty());

        //Caso de prueba 3: lista vacía
        Assert.assertTrue(BrandExtractorUtil.applyFilter("AVIA", new ArrayList<Gasolinera>()).isEmpty());

    }
}
