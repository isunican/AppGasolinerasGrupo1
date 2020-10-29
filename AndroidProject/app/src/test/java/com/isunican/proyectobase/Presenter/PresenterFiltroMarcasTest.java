package com.isunican.proyectobase.Presenter;

import com.isunican.proyectobase.Model.Gasolinera;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class PresenterFiltroMarcasTest {

    PresenterFiltroMarcas sut;
    ArrayList<Gasolinera> gasolineras= new ArrayList<>();
    ArrayList<Gasolinera> filtradas= new ArrayList<>();
    @Before
    public void setup(){
        this.gasolineras.add(new Gasolinera(1000,"Santander","Santander", "Av Valdecilla", 1.299,1.359,"AVIA"));
        this.gasolineras.add(new Gasolinera(1053,"Santander","Santander", "Plaza Matias Montero", 1.270,1.349,"CAMPSA"));
        this.gasolineras.add(new Gasolinera(420,"Santander","Santander", "Area Arrabal Puerto de Raos", 1.249,1.279,"CAMPSA"));
        this.gasolineras.add(new Gasolinera(9564,"Santander","Santander", "Av Parayas", 1.189,1.269,"EASYGAS"));
        this.gasolineras.add(new Gasolinera(1025,"Santander","Santander", "Calle el Empalme", 1.259,1.319,"CARREFOUR"));
        this.filtradas.add(new Gasolinera(420,"Santander","Santander", "Area Arrabal Puerto de Raos", 1.249,1.279,"E.E.S.S. MAS, S.L."));
        this.filtradas.add(new Gasolinera(9564,"Santander","Santander", "Av Parayas", 1.189,1.269,"EASYGAS"));

    }

    @Test
    public void testGetMarcas(){
        //TODO: Implementar test correctamente
        sut= new PresenterFiltroMarcas(gasolineras);
        ArrayList<String> marcas = (ArrayList)sut.getMarcas();
        for(Gasolinera g: gasolineras){
            assertTrue(marcas.contains(g.getRotulo()));
        }

    }

    @Test
    public void testBuscaMarcas(){
        //TODO: Implementar test correctamente
        sut = new PresenterFiltroMarcas(gasolineras);
        ArrayList<String> marcasFiltradas=(ArrayList<String>)sut.buscaMarcas("E");

        for(String s: marcasFiltradas){
            System.out.println(s);
            assertTrue(s.toLowerCase().startsWith("e"));
        }
    }

    @Test
    public void testGetListaGasolineras(){
        //TODO: Implementar test correctamente
        sut=new PresenterFiltroMarcas(gasolineras);

        assertEquals(sut.getListaGasolineras(), gasolineras);
    }

    @Test
    public void testFiltraGasolineras(){
        //TODO: Implementar test correctamente
        sut= new PresenterFiltroMarcas(gasolineras);
        ArrayList<Gasolinera>filtradas=(ArrayList)sut.filtraGasolineras("campsa");
        for(Gasolinera g: filtradas){
            assertTrue(g.getRotulo().toLowerCase().equals("campsa"));
        }
    }

}
