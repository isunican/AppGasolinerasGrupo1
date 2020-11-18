package com.isunican.proyectobase.Presenter;

import android.os.Build;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import com.isunican.proyectobase.Database.AppDatabaseTest;
import com.isunican.proyectobase.Model.Gasolinera;
import com.isunican.proyectobase.Model.GasolineraFavorita;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {Build.VERSION_CODES.O_MR1})
public class PresenterGasolinerasFavoritasTest {

    private AppDatabaseTest db = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(),
            AppDatabaseTest.class).allowMainThreadQueries().build();
    private Gasolinera gasolinera1;
    private Gasolinera gasolinera2;
    private Gasolinera gasolinera3;
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
        sut.setListaGasolinerasFavoritas(new ArrayList<GasolineraFavorita>());

        gasolinera1 = new Gasolinera(1,"localidad1","provincia1",
                "direccion1",1.0,1.0,"gasolinera1");
        gasolinera1.setId(1);
        gasolinera2 = new Gasolinera(2,"localidad2","provincia2",
                "direccion2",1.0,1.0,"gasolinera2");
        gasolinera2.setId(2);
        gasolinera3 = new Gasolinera(3,"localidad3","provincia3",
                "direccion·",1.0,1.0,"gasolinera3");
        gasolinera2.setId(3);
        gasolineraFavorita1 = new GasolineraFavorita(COMENTARIO1, gasolinera1.getId());
        gasolineraFavorita2 = new GasolineraFavorita(COMENTARIO2, gasolinera2.getId());

    }

    @After
    public void close(){
        db.gasolineraFavoritaDAO().nuke();
        db.gasolineraDAO().nuke();
    }

    @Test
    public void anhadeGasolineraFavoritaTest() {

        // UT.1.a: En lista vacia de gasolineras favoritas TODO revisar que el identificador es correcto
        Assert.assertEquals(sut.anhadirGasolineraFavorita(gasolinera1.getId(), COMENTARIO1,
                db.gasolineraFavoritaDAO()), gasolineraFavorita1);

        // UT.1.b: En lista con una gasolinera favorita TODO revisar que el identificador es correcto
        Assert.assertEquals(sut.anhadirGasolineraFavorita(gasolinera2.getId(), COMENTARIO2,
                db.gasolineraFavoritaDAO()), gasolineraFavorita2);

        // UT.1.c: Insercion de una gasolinera favorita con un id_gasolinera ya existente en la tabla (comprobacion de unicidad)
        try {
            sut.anhadirGasolineraFavorita(gasolinera2.getId(), COMENTARIO2,
                db.gasolineraFavoritaDAO());
            Assert.fail();
        } catch (Exception e){
            Assert.assertEquals(2, db.gasolineraFavoritaDAO().getAll().size());
            Assert.assertEquals(2, sut.getListaGasolinerasFavoritas().size());
        }

        // UT.1.d: Paso nulo en GasolineraFavoritaDAO
        Assert.assertNull(sut.anhadirGasolineraFavorita(gasolinera1.getId(), COMENTARIO1, null));
    }

    @Test
    public void modificaGasolineraFavoritaTest() {

        // UT.2.a: Modificar gasolinera favorita no existente en la base de datos
        Assert.assertNull(sut.modificarGasolineraFavorita(gasolinera1.getId(), COMENTARIO1, db.gasolineraFavoritaDAO()));
        Assert.assertEquals(0, db.gasolineraFavoritaDAO().getAll().size());
        Assert.assertEquals(0, sut.getListaGasolinerasFavoritas().size());

        // UT.2.b: En lista vacia de gasolineras favoritas TODO revisar que el identificador es correcto
        sut.anhadirGasolineraFavorita(gasolinera1.getId(), COMENTARIO1,
                db.gasolineraFavoritaDAO());
        gasolineraFavorita1 = new GasolineraFavorita(COMENTARIO4, gasolinera1.getId());
        Assert.assertEquals( gasolineraFavorita1, sut.modificarGasolineraFavorita(gasolinera1.getId(), COMENTARIO4,
                db.gasolineraFavoritaDAO()));

        // UT.2.c: En lista con una gasolinera favorita TODO revisar que el identificador es correcto
        sut.anhadirGasolineraFavorita(gasolinera2.getId(), COMENTARIO2,
                db.gasolineraFavoritaDAO());
        gasolineraFavorita2 = new GasolineraFavorita(COMENTARIO4, gasolinera2.getId());
        Assert.assertEquals(gasolineraFavorita2, sut.modificarGasolineraFavorita(gasolinera2.getId(), COMENTARIO4,
                db.gasolineraFavoritaDAO()));

        // UT.2.d: Paso nulo en GasolineraFavoritaDAO
        Assert.assertNull(sut.modificarGasolineraFavorita(gasolinera1.getId(), COMENTARIO1, null));
    }

    @Test
    public void getGasolineraFavoritaPorIdTest(){
        // UT.3.a: Paso nulo en GasolineraFavoritaDAO
        Assert.assertNull(sut.getGasolineraFavoritaPorId(1,null));

        // UT.3.b: Paso de id de una gasolinera que no es favorita
        Assert.assertNull(sut.getGasolineraFavoritaPorId(1, db.gasolineraFavoritaDAO()));

        // UT.3.c: Paso de id correcto
        sut.anhadirGasolineraFavorita(gasolinera1.getId(), COMENTARIO1,
                db.gasolineraFavoritaDAO());
        Assert.assertEquals(gasolineraFavorita1, sut.getGasolineraFavoritaPorId(1, db.gasolineraFavoritaDAO()));
    }


    @Test
    public void eliminaGasolineraFavoritaTest() {

        db.gasolineraDAO().insertOne(gasolinera1);
        db.gasolineraFavoritaDAO().insertOne(gasolineraFavorita1);

        // Caso 1: eliminar una gasolinera de la lista de favoritos
        Assert.assertEquals(gasolineraFavorita1, sut.eliminaGasolineraFavorita(gasolinera1, db.gasolineraDAO(), db.gasolineraFavoritaDAO()));
        Assert.assertEquals(0,db.gasolineraDAO().findById(gasolinera1.getIdeess()).size());
        Assert.assertEquals(0,db.gasolineraFavoritaDAO().findById(gasolinera1.getIdeess()).size());

        // Caso 2: eliminar una gasolinera no existente en la lista de favoritos
        Assert.assertEquals(null, sut.eliminaGasolineraFavorita(gasolinera3, db.gasolineraDAO(), db.gasolineraFavoritaDAO()));
        Assert.assertEquals(0,db.gasolineraFavoritaDAO().findById(gasolinera3.getIdeess()).size());
        Assert.assertEquals(0,db.gasolineraDAO().findById(gasolinera3.getIdeess()).size());

        // Caso 3: eliminar una gasolinera con una de las DAOs nulas
        Assert.assertEquals(null, sut.eliminaGasolineraFavorita(gasolinera1, null, db.gasolineraFavoritaDAO()));
        Assert.assertEquals(0,db.gasolineraDAO().findById(gasolinera1.getIdeess()).size());

        Assert.assertEquals(0,db.gasolineraFavoritaDAO().findById(gasolinera1.getIdeess()).size());
        Assert.assertEquals(null, sut.eliminaGasolineraFavorita(gasolinera1, db.gasolineraDAO(), null));
        Assert.assertEquals(0,db.gasolineraDAO().findById(gasolinera1.getIdeess()).size());
        Assert.assertEquals(0,db.gasolineraFavoritaDAO().findById(gasolinera1.getIdeess()).size());
    }
}