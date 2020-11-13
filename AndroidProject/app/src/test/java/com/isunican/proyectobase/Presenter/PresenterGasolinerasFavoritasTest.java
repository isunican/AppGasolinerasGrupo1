package com.isunican.proyectobase.Presenter;

import android.app.Application;
import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import com.isunican.proyectobase.DAO.GasolineraDAO;
import com.isunican.proyectobase.DAO.GasolineraFavoritaDAO;
import com.isunican.proyectobase.Database.AppDatabase;
import com.isunican.proyectobase.Database.AppDatabaseTest;
import com.isunican.proyectobase.Model.Gasolinera;
import com.isunican.proyectobase.Model.GasolineraFavorita;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;
import java.util.List;

public class PresenterGasolinerasFavoritasTest {

    private Gasolinera gasolinera1;
    private Gasolinera gasolinera2;
    private final static String COMENTARIO1 = "Comenatario 1";
    private final static String COMENTARIO2 = "Comenatario 2";
    private final static String COMENTARIO3 = "Comenatario 3";
    private final static String COMENTARIO4 = "Comenatario 4";

    private PresenterGasolinerasFavoritas sut;
    private GasolineraFavorita gasolineraFavorita1;
    private GasolineraFavorita gasolineraFavorita2;

    @Before
    public void setUp(){
        sut = new PresenterGasolinerasFavoritas();
        gasolinera1 = new Gasolinera(1,"localidad1","provincia1",
                "direccion1",1.0,1.0,"gasolinera1");
        gasolinera2 = new Gasolinera(2,"localidad2","provincia2",
                "direccion2",1.0,1.0,"gasolinera2");
        gasolineraFavorita1 = new GasolineraFavorita(COMENTARIO1, gasolinera1.getIdeess());
        gasolineraFavorita2 = new GasolineraFavorita(COMENTARIO2, gasolinera2.getIdeess());
    }

    @After
    public void close(){
        AppDatabaseTest.getInstance(ApplicationProvider.getApplicationContext()).close();
    }

    @Test
    public void anhadeGasolineraFavoritaTest() {
        sut.setListaGasolinerasFavoritas(new ArrayList<GasolineraFavorita>());
        // UT.1.a: En lista vacia de gasolineras favoritas TODO revisar que el identificador es correcto
        Assert.assertEquals(sut.anhadirGasolineraFavorita(gasolinera1.getIdeess(), COMENTARIO1,
                AppDatabaseTest.getInstance(ApplicationProvider.getApplicationContext()).gasolineraFavoritaDAO()), gasolineraFavorita1);
        // UT.1.a: En lista con una gasolinera favorita TODO revisar que el identificador es correcto
        Assert.assertEquals(sut.anhadirGasolineraFavorita(gasolinera2.getIdeess(), COMENTARIO2,
                AppDatabaseTest.getInstance(ApplicationProvider.getApplicationContext()).gasolineraFavoritaDAO()), gasolineraFavorita2);
    }

    @Test
    public void modificaGasolineraFavoritaTest() {
        sut.setListaGasolinerasFavoritas(new ArrayList<GasolineraFavorita>());
        // UT.2.a: En lista vacia de gasolineras favoritas TODO revisar que el identificador es correcto
        gasolineraFavorita1 = new GasolineraFavorita(COMENTARIO3, gasolinera1.getIdeess());
        Assert.assertEquals(sut.modificarGasolineraFavorita(gasolinera1.getIdeess(), COMENTARIO3,
                AppDatabaseTest.getInstance(ApplicationProvider.getApplicationContext()).gasolineraFavoritaDAO()), gasolineraFavorita1);
        // UT.2.a: En lista con una gasolinera favorita TODO revisar que el identificador es correcto
        gasolineraFavorita2 = new GasolineraFavorita(COMENTARIO3, gasolinera2.getIdeess());
        Assert.assertEquals(sut.modificarGasolineraFavorita(gasolinera2.getIdeess(), COMENTARIO4,
                AppDatabaseTest.getInstance(ApplicationProvider.getApplicationContext()).gasolineraFavoritaDAO()), gasolineraFavorita2);
    }
}
