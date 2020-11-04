package com.isunican.proyectobase.Model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TipoGasolinaTest {
    private Gasolinera gasolineraConDiesel=new Gasolinera(2,"localidad2","provincia2","direccion2",1.0,0.0,"gasolinera2");
    private Gasolinera gasolineraSinDiesel=new Gasolinera(1,"localidad1","provincia1","direccion1",0.0,1.0,"gasolinera1");
    private Gasolinera gasolineraDieselGasolina=new Gasolinera(3,"localidad3","provincia3","direccion3",1.0,1.0,"gasolinera3");
    private Gasolinera gasolineraSin=new Gasolinera(4,"localidad4","provincia4","direccion4",0.0,0.0,"gasolinera4");

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
