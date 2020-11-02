package com.isunican.proyectobase.Presenter;

import com.isunican.proyectobase.Model.Gasolinera;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;

public class PresenterFiltroMarcasTest {

    PresenterFiltroMarcas sut;
    ArrayList<Gasolinera> gasolineras= new ArrayList<>();
    ArrayList<Gasolinera> filtradas= new ArrayList<>();
    ArrayList<String> marcas= new ArrayList<>();


    @Before
    public void setup(){
        this.gasolineras.add(new Gasolinera(1000,"Santander","Santander", "Av Valdecilla", 1.299,1.359,"AVIA"));
        this.gasolineras.add(new Gasolinera(1053,"Santander","Santander", "Plaza Matias Montero", 1.270,1.349,"CAMPSA"));
        this.gasolineras.add(new Gasolinera(420,"Santander","Santander", "Area Arrabal Puerto de Raos", 1.249,1.279,"CAMPSA"));
        this.gasolineras.add(new Gasolinera(9564,"Santander","Santander", "Av Parayas", 1.189,1.269,"EASYGAS"));
        this.gasolineras.add(new Gasolinera(1025,"Santander","Santander", "Calle el Empalme", 1.259,1.319,"CARREFOUR"));
        this.filtradas.add(gasolineras.get(1));
        this.filtradas.add(gasolineras.get(2));
        this.marcas.add("AVIA");
        this.marcas.add("CAMPSA");
        this.marcas.add("EASYGAS");
        this.marcas.add("CARREFOUR");


    }

    @Test

    public void testGetMarcas(){
        //TODO: Implementar test

    }

    @Test
    public void testGetListaGasolineras(){
        //TODO: Implementar test
    }

    @Test
    public void testFiltraGasolineras() {
        //TODO: Implementar test
    }

}
