package com.isunican.proyectobase.Presenter;


import android.os.Build;

import com.isunican.proyectobase.Model.Gasolinera;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;

/**
 * Prueba de integracion del presenter del filtro de marcas con los datos de la BD y la utility de
 * extracción de marcas
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.O_MR1)
public class PresenterFiltroMarcasITest {

    private PresenterFiltroMarcas sut;
    private PresenterGasolineras sut2;

    @Before
    public void init(){
        sut2 = new PresenterGasolineras();
    }


    @Test
    public void testFiltraGasolineras(){
        sut2.cargaDatosGasolineras();
        ArrayList<Gasolinera> gasolineras = (ArrayList<Gasolinera>) sut2.getGasolineras();

        sut = new PresenterFiltroMarcas(gasolineras);


        //Caso de prueba 1: Datos correctos
        ArrayList<Gasolinera> filtradas = (ArrayList<Gasolinera>) sut.filtraGasolineras("Repsol");

        for(Gasolinera g: filtradas){
            Assert.assertTrue(g.getRotulo().equals("REPSOL"));
            Assert.assertTrue(gasolineras.contains(g));
        }


        //Caso de prueba 2: Marca no existente
        filtradas = (ArrayList<Gasolinera>) sut.filtraGasolineras("Ersolp");
        Assert.assertArrayEquals(gasolineras.toArray(),filtradas.toArray());
    }

}
