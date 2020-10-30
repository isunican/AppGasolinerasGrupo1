package com.isunican.proyectobase;

import com.isunican.proyectobase.Model.Gasolinera;
import com.isunican.proyectobase.Presenter.PresenterGasolineras;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class TestFiltroTipoGasolina {


    @Test
    public void testFiltrosTipoGasolina(){
        PresenterGasolineras presenter=new PresenterGasolineras();
        //UGIC.1a
        try {
            presenter.filtraGasolinerasTipoCombustible("Diesel ", null);
            fail();
        }catch(NullPointerException e){
            Assert.assertTrue(true);
        }
        //UGIC.1b
        List<Gasolinera>listaSinDiesel=new ArrayList<Gasolinera>();
        List<Gasolinera>listaVacia=new ArrayList<Gasolinera>();
        listaSinDiesel.add(new Gasolinera(1,"localidad1","provincia1","direccion1",0,1.0,"gasolinera1"));
        assertEquals(presenter.filtraGasolinerasTipoCombustible("Diesel ",listaSinDiesel), listaVacia);
        //UGIC.1c
        List<Gasolinera>listaConDiesel=new ArrayList<Gasolinera>();
        listaConDiesel.add(new Gasolinera(1,"localidad1","provincia1","direccion1",0,1.0,"gasolinera1"));
        listaConDiesel.add(new Gasolinera(2,"localidad2","provincia2","direccion2",1,0.0,"gasolinera2"));

        //UGIC.1d
    }
    @Test
    public void testTiposGasolina(){
        //UID.1a
        //UID.1b
        //UID.1c
        //UID.1d
    }
}
