package com.isunican.proyectobase.Presenter;

import android.content.Context;

import com.isunican.proyectobase.Model.Gasolinera;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;


/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class PresenterTarjetaDescuentoTest {

    PresenterTarjetaDescuento sut;
    ArrayList<Gasolinera> gasolineras;
    ArrayList<Gasolinera> gasolineraCompatible;
    ArrayList<Gasolinera> gasolineraIncompatible;

    @Before
    public void setup(){
        gasolineras = new ArrayList<>();
        this.gasolineras.add(new Gasolinera(1000,"Santander","Santander", "Av Valdecilla", 1.000,1.000,"AVIA"));
        this.gasolineras.add(new Gasolinera(1053,"Santander","Santander", "Plaza Matias Montero", 1.020,1.020,"CEPSA"));
        this.gasolineras.add(new Gasolinera(420,"Santander","Santander", "Area Arrabal Puerto de Raos", 1.000,1.000,"E.E.S.S. MAS, S.L."));
        this.gasolineras.add(new Gasolinera(9564,"Santander","Santander", "Av Parayas", 1.020,1.020,"CEPSA"));
        this.gasolineras.add(new Gasolinera(1025,"Santander","Santander", "Calle el Empalme", 1.020,1.020,"CEPSA"));
        gasolineraCompatible=new ArrayList<>();
        gasolineraCompatible.add(new Gasolinera(1053,"Santander","Santander", "Plaza Matias Montero", 1.020,1.020,"CEPSA"));
        gasolineraIncompatible=new ArrayList<>();
        gasolineraIncompatible.add(new Gasolinera(1000,"Santander","Santander", "Av Valdecilla", 1.000,1.000,"AVIA"));
    }

    @Test
    public void anahdirNuevaTarjetaTest() {

        sut = new PresenterTarjetaDescuento();

        //Caso 1: "TestPorcOK", "Test", "Cepsa", "Porcentual", "33.3"
        Assert.assertTrue(sut.anhadirNuevaTarjeta("TestPorcOK", "test", "Cepsa", "Porcentual", "33.3"));
        //Caso 2: "TestPorcFail", "Test", "Cepsa", "Porcentual", "465"
        Assert.assertFalse(sut.anhadirNuevaTarjeta("TestPorcFail", "test", "Cepsa", "Porcentual", "456"));
        //Caso 3: "TestPorcFail2", "Test", "Cepsa", "Porcentual", "Cincuenta"
        Assert.assertFalse(sut.anhadirNuevaTarjeta("TestPorFail2", "test", "Cepsa", "Porcentual", "cincuenta"));
        //Caso 4: "TestCtsOK", "Test", "Cepsa", "cts/Litro", "5"
        Assert.assertTrue(sut.anhadirNuevaTarjeta("TestCtsOK", "test", "Cepsa", "cts/Litro", "5"));
        //Caso 5: "TestCtsFail", "Test", "Cepsa", "cts/Litro", "cuatro"
        Assert.assertFalse(sut.anhadirNuevaTarjeta("TestCtsFail", "test", "Cepsa", "cts/Litro", "cuatro"));
        //Caso 6: "TestTipoFail", "Test", "Cepsa", "test", "3"
        Assert.assertFalse(sut.anhadirNuevaTarjeta("TestTipoFail", "Test", "Cepsa","test", "3"));

        //TODO: ANHADIR MAS CASOS DE PRUEBA SI LOS HUBIERA
    }

    @Test
    public void actualizarListaPreciosTest(){
        sut = new PresenterTarjetaDescuento();

        //Caso 1: Gasolineras=lista de gasolineras mixta  descuento=0 (no hay tarjetas)
        ArrayList<Gasolinera> testGasolineras = (ArrayList<Gasolinera>) sut.actualizarListaDePrecios(gasolineras);
        Assert.assertArrayEquals(testGasolineras.toArray(),gasolineras.toArray());

        //Anhadimos las tarjetas de descuento
        sut.anhadirNuevaTarjeta("T1","Tarjeta %","CEPSA","Porcentual","5");
        sut.anhadirNuevaTarjeta("T2", "Tarjeta cts","CEPSA", "cts/Litro","2");

        //Caso 2: Gasolineras=1 gasolinera compatible descuento="5%+2" TODO: ESTE TEST NO LO PASA
        testGasolineras = (ArrayList<Gasolinera>) sut.actualizarListaDePrecios(gasolineraCompatible);
        Assert.assertEquals(0.95, testGasolineras.get(0).getGasoleoA(),0.001);
        Assert.assertEquals(0.95, testGasolineras.get(0).getGasolina95(),0.001);

        //Caso 3: Gasolineras=1 gasolinera incompatible descuento="5%+2"
        testGasolineras = (ArrayList<Gasolinera>) sut.actualizarListaDePrecios(gasolineraIncompatible);
        Assert.assertEquals(1.000, testGasolineras.get(0).getGasoleoA(),0.001);
        Assert.assertEquals(1.000, testGasolineras.get(0).getGasolina95(),0.001);

        //Caso 4: Gasolineras= lista de gasolineras mixta descuento="5%+2"
        testGasolineras = (ArrayList<Gasolinera>) sut.actualizarListaDePrecios(gasolineras);
        for(Gasolinera g: testGasolineras){
            if(g.getRotulo().equals("CEPSA")) {
                Assert.assertEquals( 0.95, g.getGasolina95(),0.001);
                Assert.assertEquals( 0.95, g.getGasoleoA(), 0.001);
            }else{
                Assert.assertEquals(1.000, g.getGasoleoA(),0.001);
                Assert.assertEquals(1.000, g.getGasolina95(),0.001);
            }
        }

        //Caso 5: Gasolineras= lista vacía descuento="5%+2"
        Assert.assertTrue(sut.actualizarListaDePrecios(new ArrayList<Gasolinera>()).size()==0);

        //Caso 6: Gasolineras= lista nula descuento="5%+2" TODO: ESTE TIRA NULLPOINTER
        Assert.assertTrue(sut.actualizarListaDePrecios(null).size()==0);


    }
}
