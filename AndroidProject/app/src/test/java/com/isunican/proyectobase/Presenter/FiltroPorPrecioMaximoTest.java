package com.isunican.proyectobase.Presenter;

import com.isunican.proyectobase.Model.Gasolinera;

import org.junit.Test;

import java.util.List;

/**
 * Pruebas unitarias sobre el metodo Filtrar por precio maximo.
 * @author Carolay Corales Acosta
 *
 */
public class FiltroPorPrecioMaximoTest {


    private Gasolinera gasolineraConDiesel=new Gasolinera(2,"localidad2","provincia2","direccion2",1.0,0.0,"gasolinera2");
    private Gasolinera gasolineraSinDiesel=new Gasolinera(1,"localidad1","provincia1","direccion1",0.0,1.0,"gasolinera1");
    private Gasolinera gasolineraDieselGasolina=new Gasolinera(3,"localidad3","provincia3","direccion3",1.0,1.0,"gasolinera3");
    private Gasolinera gasolineraSin=new Gasolinera(4,"localidad4","provincia4","direccion4",0.0,0.0,"gasolinera4");
    private List<Gasolinera> listaBajoPrueba;
    private List<Gasolinera>listaEsperada;

    @Test
    public void filtroPorPrecioMaximo(){
        //Caso 1:

        //Caso 2:
    }

}
