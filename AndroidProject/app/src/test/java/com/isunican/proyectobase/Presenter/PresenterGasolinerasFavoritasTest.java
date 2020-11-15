package com.isunican.proyectobase.Presenter;

import android.os.Build;

import androidx.test.core.app.ApplicationProvider;

import com.isunican.proyectobase.DAO.GasolineraDAO;
import com.isunican.proyectobase.DAO.GasolineraFavoritaDAO;
import com.isunican.proyectobase.Database.AppDatabase;
import com.isunican.proyectobase.Model.Gasolinera;
import com.isunican.proyectobase.Model.GasolineraFavorita;
import com.isunican.proyectobase.Views.DetailActivity;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;
@RunWith(RobolectricTestRunner.class)
@Config(sdk = {Build.VERSION_CODES.O_MR1})
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

    private GasolineraDAO gasolineraDAO;
    private GasolineraFavoritaDAO gasolineraFavoritaDAO;

    @Before
    public void setUp() {
        sut = new PresenterGasolinerasFavoritas();
        sut.setListaGasolinerasFavoritas(new ArrayList<GasolineraFavorita>());

        // TODO: pasar bien el contexto
        //gasolineraDAO = AppDatabase.getInstance(contexto).gasolineraDAO();
        //gasolineraFavoritaDAO = AppDatabase.getInstance(contexto).gasolineraFavoritaDAO();

        gasolinera1 = new Gasolinera(1, "localidad1", "provincia1",
                "direccion1", 1.0, 1.0, "gasolinera1");
        gasolinera2 = new Gasolinera(2, "localidad2", "provincia2",
                "direccion2", 1.0, 1.0, "gasolinera2");
        gasolineraFavorita1 = new GasolineraFavorita(COMENTARIO1, gasolinera1.getIdeess());
        gasolineraFavorita2 = new GasolineraFavorita(COMENTARIO2, gasolinera2.getIdeess());

        gasolineraDAO.insertOne(gasolinera1);
        gasolineraFavoritaDAO.insertOne(gasolineraFavorita1);
    }

    @Test
    public void eliminaGasolineraFavoritaTest() {

        // Caso 1: eliminar una gasolinera de la lista de favoritos
        Assert.assertEquals(gasolinera1, sut.eliminaGasolineraFavorita(gasolinera1.getIdeess(), gasolineraDAO, gasolineraFavoritaDAO));
        Assert.assertTrue(gasolineraDAO.findById(gasolinera1.getIdeess()).size() == 0);
        Assert.assertTrue(gasolineraFavoritaDAO.findByGasolineraId(gasolinera1.getIdeess()).size() == 0);

        // Caso 2: eliminar una gasolinera no existente en la lista de favoritos
        Assert.assertEquals(null, sut.eliminaGasolineraFavorita(gasolinera2.getIdeess(), gasolineraDAO, gasolineraFavoritaDAO));
        Assert.assertTrue(gasolineraDAO.findById(gasolinera1.getIdeess()).size() == 0);
        Assert.assertTrue(gasolineraFavoritaDAO.findByGasolineraId(gasolinera1.getIdeess()).size() == 0);


        // Caso 3: eliminar una gasolinera con una de las DAOs nulas
        Assert.assertEquals(null, sut.eliminaGasolineraFavorita(gasolinera1.getIdeess(), null, gasolineraFavoritaDAO));
        Assert.assertTrue(gasolineraDAO.findById(gasolinera1.getIdeess()).size() == 0);
        Assert.assertTrue(gasolineraFavoritaDAO.findByGasolineraId(gasolinera1.getIdeess()).size() == 0);

        Assert.assertEquals(null, sut.eliminaGasolineraFavorita(gasolinera1.getIdeess(), gasolineraDAO, null));
        Assert.assertTrue(gasolineraDAO.findById(gasolinera1.getIdeess()).size() == 0);
        Assert.assertTrue(gasolineraFavoritaDAO.findByGasolineraId(gasolinera1.getIdeess()).size() == 0);

        //TODO: ANHADIR MAS CASOS DE PRUEBA SI LOS HUBIERA
    }
}