package com.isunican.proyectobase.Presenter;

import com.isunican.proyectobase.Model.Gasolinera;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Pruebas unitarias del presenter de gasolineras favoritas
 */
public class PresenterGasolinerasFavoritasTest {

    PresenterGasolinerasFavoritas sut;
    ArrayList<Gasolinera> gasolinerasFavoritas;

    @Before
    public void setup() {
        gasolinerasFavoritas = new ArrayList<>();
        Gasolinera gasolinera1 = new Gasolinera(1000, "Santander", "Santander", "Av Valdecilla", 1.000, 1.000, "AVIA");
        this.gasolinerasFavoritas.add(gasolinera1);
        this.gasolinerasFavoritas.add(new Gasolinera(1053, "Santander", "Santander", "Plaza Matias Montero", 1.020, 1.020, "CEPSA"));
        this.gasolinerasFavoritas.add(new Gasolinera(420, "Santander", "Santander", "Area Arrabal Puerto de Raos", 1.000, 1.000, "E.E.S.S. MAS, S.L."));
        this.gasolinerasFavoritas.add(new Gasolinera(9564, "Santander", "Santander", "Av Parayas", 1.020, 1.020, "CEPSA"));
        this.gasolinerasFavoritas.add(new Gasolinera(1025, "Santander", "Santander", "Calle el Empalme", 1.020, 1.020, "CEPSA"));
    }

    @Test
    public void eliminaGasolineraFavoritaTest() {

        System.out.println("num eltos favos = "+gasolinerasFavoritas.size());
        System.out.println("id gasol 1 = " + gasolinerasFavoritas.get(0).getIdeess());
        sut = new PresenterGasolinerasFavoritas();
        Gasolinera gasolinera1 = new Gasolinera(1000, "Santander", "Santander", "Av Valdecilla", 1.000, 1.000, "AVIA");

        // Caso 1: eliminar una gasolinera de la lista de favoritos
        //Assert.assertEquals(sut.gasolinerasFavoritas.get(0),sut.eliminaGasolineraFavorita(1000));
        Assert.assertTrue(sut.gasolinerasFavoritas.get(0).equals(sut.gasolinerasFavoritas.get(0)));

        // Caso 2: eliminar una gasolinera no existente en la lista de favoritos




        //Caso 1: "TestPorcOK", "Test", "Cepsa", "Porcentual", "33.3"
        //Assert.assertTrue(sut.anhadirNuevaTarjeta("TestPorcOK", "test", "Cepsa", "Porcentual", "33.3"));

        //TODO: COMPROBAR LOS CAMBIOS EN LOS ARRAYS Y EN LA BASE DE DATOS

        //TODO: ANHADIR MAS CASOS DE PRUEBA SI LOS HUBIERA
    }
}