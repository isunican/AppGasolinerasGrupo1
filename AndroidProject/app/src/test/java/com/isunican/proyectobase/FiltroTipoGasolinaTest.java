package com.isunican.proyectobase;

import com.isunican.proyectobase.Model.Gasolinera;
import com.isunican.proyectobase.Presenter.PresenterGasolineras;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Pruebas unitarias realizadas sobre la lógica del filtrado por tipo de combsutible, se prueban 2 métodos que se han añadido para
 * hacer funcionar el filtro, estos son:
 * -filtraGasolinerasTipoCombstibles(String tipo, ArrayList<Gasolinera> lista):ArrayList<Gasolinera>: este método se encarga de generar una nueva lista de gasolineras filtradas a partir de una
 * original, apoyandose en el método tiposGasolina().
 * -tiposGasolina(): String: este método se encarga de determinar que tipos de gasolinas tiene la gasolinera en cuestión.
 *
 * @author Jaime López-Agudo Higuera (pruebas unitarias)
 *
 */
public class FiltroTipoGasolinaTest {


    private Gasolinera gasolineraConDiesel=new Gasolinera(2,"localidad2","provincia2","direccion2",1.0,0.0,"gasolinera2");
    private Gasolinera gasolineraSinDiesel=new Gasolinera(1,"localidad1","provincia1","direccion1",0.0,1.0,"gasolinera1");
    private Gasolinera gasolineraDieselGasolina=new Gasolinera(3,"localidad3","provincia3","direccion3",1.0,1.0,"gasolinera3");
    private Gasolinera gasolineraSin=new Gasolinera(4,"localidad4","provincia4","direccion4",0.0,0.0,"gasolinera4");
    private List<Gasolinera>listaBajoPrueba;
    private List<Gasolinera>listaEsperada;


    @Test
    public void filtrosTipoGasolinaTest(){
        PresenterGasolineras presenter=new PresenterGasolineras();
        //UGIC.1a
        try {
            presenter.filtraGasolinerasTipoCombustible("Diesel ", null);
            fail();
        }catch(NullPointerException e){
            Assert.assertTrue(true);
        }
        //UGIC.1b
        listaBajoPrueba=new ArrayList<Gasolinera>();
        listaEsperada=new ArrayList<Gasolinera>();
        listaBajoPrueba.add(gasolineraSinDiesel);
        assertEquals(presenter.filtraGasolinerasTipoCombustible("Diesel ",listaBajoPrueba), listaEsperada);
        //UGIC.1c
        listaBajoPrueba=new ArrayList<Gasolinera>();
        listaEsperada=new ArrayList<Gasolinera>();
        listaBajoPrueba.add(gasolineraConDiesel);
        listaBajoPrueba.add(gasolineraSinDiesel);
        listaEsperada.add(gasolineraConDiesel);
        assertEquals(presenter.filtraGasolinerasTipoCombustible("Diesel ",listaBajoPrueba),listaEsperada);
        //UGIC.1d
        listaBajoPrueba=new ArrayList<Gasolinera>();
        listaBajoPrueba.add(gasolineraConDiesel);
        listaBajoPrueba.add(gasolineraConDiesel);
        listaBajoPrueba.add(gasolineraConDiesel);
        listaEsperada=new ArrayList<Gasolinera>();
        listaEsperada.add(gasolineraConDiesel);
        listaEsperada.add(gasolineraConDiesel);
        listaEsperada.add(gasolineraConDiesel);
        assertEquals(presenter.filtraGasolinerasTipoCombustible("Diesel ",listaBajoPrueba),listaEsperada);

    }
    @Test
    public void tiposGasolinaTest(){
        //UID.1a
        assertEquals("Diesel Gasolina95 ", gasolineraDieselGasolina.tiposGasolina());
        //UID.1b
        assertEquals("Diesel ", gasolineraConDiesel.tiposGasolina());
        //UID.1c
        assertEquals("Gasolina95 ", gasolineraSinDiesel.tiposGasolina());
        //UID.1d
        assertEquals("", gasolineraSin.tiposGasolina());
    }
}
