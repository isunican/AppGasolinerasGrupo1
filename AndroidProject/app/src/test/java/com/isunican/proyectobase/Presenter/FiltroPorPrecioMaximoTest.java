package com.isunican.proyectobase.Presenter;

import com.isunican.proyectobase.Model.Gasolinera;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


/**
 * Pruebas unitarias sobre el metodo Filtrar por precio maximo.
 * @author Carolay Corales Acosta
 *
 */
public class FiltroPorPrecioMaximoTest {

    private Gasolinera gasolinera1;
    private Gasolinera gasolinera2;
    private Gasolinera gasolinera3;
    private Gasolinera gasolinera4;
    List<Gasolinera> gasolineras;
    List<Gasolinera> gasolinerasFiltradasGasolina95;
    PresenterGasolineras presenter;
    List<Gasolinera> gasolinerasFiltradasDiesel;



    @Before
    public void setUp(){

        gasolineras = new ArrayList<>();
        presenter = new PresenterGasolineras();
        gasolinerasFiltradasGasolina95 = new ArrayList<>();
        gasolinerasFiltradasDiesel = new ArrayList<>();
        //Gasolineras
        Gasolinera gasolinera1 = new Gasolinera(1,"localidad1","provincia1","direccion1",1.07,1.12,"gasolinera1");
        Gasolinera gasolinera2 = new Gasolinera(2,"localidad2","provincia2","direccion2",0.9,1.05,"gasolinera2");
        Gasolinera gasolinera3 = new Gasolinera(3,"localidad3","provincia3","direccion3",1.24,1.041,"gasolinera3");
        Gasolinera gasolinera4 = new Gasolinera(4,"localidad4","provincia4","direccion4",0.85,1.09,"gasolinera4");

        //Lista gasolineras
        gasolineras.add(new Gasolinera(1,"localidad1","provincia1","direccion1",1.07,1.12,"gasolinera1"));
        gasolineras.add(new Gasolinera(2,"localidad2","provincia2","direccion2",0.9,1.05,"gasolinera2"));
        gasolineras.add(new Gasolinera(3,"localidad3","provincia3","direccion3",1.24,1.041,"gasolinera3"));
        gasolineras.add(new Gasolinera(4,"localidad4","provincia4","direccion4",0.85,1.09,"gasolinera4"));

        //Lista fitrada Gasolina 95
        gasolinerasFiltradasGasolina95.add(gasolinera2);
        gasolinerasFiltradasGasolina95.add(gasolinera3);

        //Lista filtrada Diesel
        gasolinerasFiltradasDiesel.add(gasolinera2);
        gasolinerasFiltradasDiesel.add(gasolinera4);
    }

    @Test
    public void filtroPorPrecioMaximoTest() {
        //399740UT.1.a: Lista de gasolineras filtrada Gasolina95
        assertTrue(Arrays.equals(presenter.filtrarGasolineraPorPrecioMaximo("Gasolina95", gasolineras, 1.069).toArray(), gasolinerasFiltradasGasolina95.toArray()));

        //399740UT.1.b: Lista de gasolineras filtrada Gasolina95
        assertTrue(Arrays.equals(presenter.filtrarGasolineraPorPrecioMaximo("Diesel", gasolineras, 1.00).toArray(), gasolinerasFiltradasDiesel.toArray()));

        //399740UT.1.c: Tipo de combustible incorrecto
        assertTrue(presenter.filtrarGasolineraPorPrecioMaximo("Gasolina98", gasolineras, 1.00).isEmpty());

        //399740UT.1.d: Lista null
        try {
            presenter.filtrarGasolineraPorPrecioMaximo("Gasolina95", null, 1.00);
            fail();
        } catch (NullPointerException e) {
            assertTrue(true);
        }

        //399740UT.1.e: lista de gasolineras con precio del tipo de gasolina seleccionado, mayor al introducido
        assertTrue(presenter.filtrarGasolineraPorPrecioMaximo("Gasolina95", gasolineras, 0.01).isEmpty());

        //399740UT.1.f: lista de gasolineras con precio del tipo de gasolina seleccionado, mayor al introducido
        assertTrue(presenter.filtrarGasolineraPorPrecioMaximo("Diesel", gasolineras, 0.01).isEmpty());

    }
}
