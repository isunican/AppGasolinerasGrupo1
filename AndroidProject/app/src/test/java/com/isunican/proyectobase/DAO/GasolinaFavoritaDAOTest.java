package com.isunican.proyectobase.DAO;

import android.os.Build;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import com.isunican.proyectobase.Database.AppDatabaseTest;
import com.isunican.proyectobase.Model.GasolineraFavorita;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.List;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {Build.VERSION_CODES.O_MR1})
public class GasolinaFavoritaDAOTest {
    private AppDatabaseTest db = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), AppDatabaseTest.class).allowMainThreadQueries().build();

    @After
    public void cleanUp(){
        db.gasolineraFavoritaDAO().nuke();
    }

    @Test
    public void getAllTest(){
        // Caso 1: Tabla sin registros
        Assert.assertTrue(db.gasolineraFavoritaDAO().getAll().size() == 0);

        // Caso 2: Tabla con 1 solo resgistro
        GasolineraFavorita sut = new GasolineraFavorita("Gasolinera cercana a casa y de pago rapido", 1);
        db.gasolineraFavoritaDAO().insertOne(sut);
        List<GasolineraFavorita> lista = db.gasolineraFavoritaDAO().getAll();
        Assert.assertTrue(lista.size() == 1);
        Assert.assertTrue(lista.get(0).equals(sut));

        // Caso 3: Tabla con mas de 1 registro
        GasolineraFavorita sut2 = new GasolineraFavorita("Gasolinera en la entrada de Santander", 5);
        db.gasolineraFavoritaDAO().insertOne(sut2);
        lista = db.gasolineraFavoritaDAO().getAll();
        Assert.assertTrue(lista.size() == 2);
        Assert.assertTrue(lista.get(0).equals(sut));
        Assert.assertTrue(lista.get(1).equals(sut2));
    }

    @Test
    public void findByIdTest(){
        // Caso 1: Tabla vacia o no encuentra el registro buscado
        List<GasolineraFavorita> lista = db.gasolineraFavoritaDAO().findById(5);
        Assert.assertTrue(lista.size() == 0);

        // Caso 2: Tabla contiene el elemento buscado
        GasolineraFavorita sut = new GasolineraFavorita("Gasolinera cercana a casa y de pago rapido", 1);
        int res = db.gasolineraFavoritaDAO().getIdFromRowId(db.gasolineraFavoritaDAO().insertOne(sut));
        lista = db.gasolineraFavoritaDAO().findById(res);
        Assert.assertTrue(lista.size() == 1);
        Assert.assertTrue(lista.get(0).equals(sut));

        //Caso3: Dos o mas elementos en Tabla
        GasolineraFavorita sut2 = new GasolineraFavorita("Gasolinera en la entrada de Santander", 5);
        res = db.gasolineraFavoritaDAO().getIdFromRowId(db.gasolineraFavoritaDAO().insertOne(sut2));
        lista = db.gasolineraFavoritaDAO().findById(res);
        Assert.assertTrue(lista.size() == 1);
        Assert.assertTrue(lista.get(0).equals(sut2));
    }

    @Test
    public void insertOneTest(){

        // Caso 1: Objeto nulo
        try{
            db.gasolineraFavoritaDAO().insertOne(null);
            ;
        } catch (Exception e)
        {
            Assert.assertTrue(db.gasolineraFavoritaDAO().getAll().size()==0);
        }

        // Caso 2: Objeto correcto
        GasolineraFavorita sut = new GasolineraFavorita("Gasolinera cercana a casa y de pago rapido", 1);
        int preInsert = db.gasolineraFavoritaDAO().getAll().size();
        db.gasolineraFavoritaDAO().insertOne(sut);
        List<GasolineraFavorita> lista = db.gasolineraFavoritaDAO().getAll();
        Assert.assertTrue((lista.size()-preInsert) == 1);
        Assert.assertTrue(lista.get(lista.size()-1).equals(sut));
    }

    @Test
    public void deleteTest(){

        //Caso 1: Eliminar nulo
        try{
            db.gasolineraFavoritaDAO().delete(null);
            Assert.fail();
        } catch (Exception e)
        {
            Assert.assertTrue(db.gasolineraFavoritaDAO().getAll().size()==0);
        }

        //Caso 2: Eliminar objeto no existente
        GasolineraFavorita sut = new GasolineraFavorita("Gasolinera cercana a casa y de pago rapido", 1);
        db.gasolineraFavoritaDAO().delete(sut);
        Assert.assertTrue(db.gasolineraFavoritaDAO().getAll().size()==0);

        //Caso 3: Eliminar objeto existente
        long rowid = db.gasolineraFavoritaDAO().insertOne(sut);
        sut.setId(db.gasolineraFavoritaDAO().getIdFromRowId(rowid));
        db.gasolineraFavoritaDAO().delete(sut);
        Assert.assertTrue(db.gasolineraFavoritaDAO().getAll().size()==0);
    }

    @Test
    public void nukeTest(){

        // Caso 1: Tabla vacia
        db.gasolineraFavoritaDAO().nuke();
        Assert.assertTrue(db.gasolineraFavoritaDAO().getAll().size() == 0);
        //Caso 2: Tabla con 1 elemento
        GasolineraFavorita sut = new GasolineraFavorita("Gasolinera cercana a casa y de pago rapido", 1);
        db.gasolineraFavoritaDAO().insertOne(sut);
        db.gasolineraFavoritaDAO().nuke();
        Assert.assertTrue(db.gasolineraFavoritaDAO().getAll().size() == 0);

        // Caso 3: Tabla con 2 o mas elementos
        GasolineraFavorita sut2 = new GasolineraFavorita("Gasolinera en la entrada de Santander", 5);
        GasolineraFavorita sut3 = new GasolineraFavorita("A medio camino de Bilbao", 8);
        db.gasolineraFavoritaDAO().insertOne(sut);
        db.gasolineraFavoritaDAO().insertOne(sut2);
        db.gasolineraFavoritaDAO().insertOne(sut3);
        db.gasolineraFavoritaDAO().nuke();
        Assert.assertTrue(db.gasolineraFavoritaDAO().getAll().size() == 0);
    }

    @Test
    public void getIdFromRowIdTest(){

        // Caso 1: No existente (Puedo poner el ID que quiera debido a que la tabla se encuentra vacia)
        long res = db.gasolineraFavoritaDAO().getIdFromRowId(27);
        Assert.assertTrue(res == 0);

        // Caso 2: Correcto
        GasolineraFavorita sut = new GasolineraFavorita("Gasolinera cercana a casa y de pago rapido", 1);
        res = db.gasolineraFavoritaDAO().insertOne(sut);
        Assert.assertTrue(res == 1);
    }

    @Test
    public void updateTest(){
        // Caso 1: Nulo
        try {
            db.gasolineraFavoritaDAO().update(null);
            Assert.fail();
        } catch ( Exception e )
        {
            Assert.assertTrue(true);
        }

        // Caso 2: Intentar actualizar una dupla que no existe en la base de datos
        GasolineraFavorita sut = new GasolineraFavorita("Gasolinera cercana a casa y de pago rapido", 1);
        db.gasolineraFavoritaDAO().update(sut);
        Assert.assertTrue(db.gasolineraFavoritaDAO().getAll().size() == 0);
        // Caso 3: Tabla con 1 elemento
        long rowid = db.gasolineraFavoritaDAO().insertOne(sut);
        sut.setId(db.gasolineraFavoritaDAO().getIdFromRowId(rowid));
        sut.setComentario("Pues al final no estaba tan cerca de casa pero esta bien");
        db.gasolineraFavoritaDAO().update(sut);
        List<GasolineraFavorita> lista = db.gasolineraFavoritaDAO().getAll();
        Assert.assertTrue(lista.size() == 1);
        Assert.assertTrue(lista.get(0).equals(sut));

        // Caso 3: Tabla con 2 o mas elementos
        GasolineraFavorita sut2 = new GasolineraFavorita("Gasolinera en la entrada de Santander", 5);
        GasolineraFavorita sut3 = new GasolineraFavorita("A medio camino de Bilbao", 8);
        db.gasolineraFavoritaDAO().insertOne(sut2);
        rowid = db.gasolineraFavoritaDAO().insertOne(sut3);
        sut3.setId(db.gasolineraFavoritaDAO().getIdFromRowId(rowid));
        sut3.setComentario("No esta a medio camino, esta casi en Castro, pero esta genial!!!");
        db.gasolineraFavoritaDAO().update(sut3);
        Assert.assertTrue(db.gasolineraFavoritaDAO().getAll().size() == 3);
        Assert.assertTrue(db.gasolineraFavoritaDAO().findById(db.gasolineraFavoritaDAO().getIdFromRowId(rowid)).get(0).equals(sut3));
    }
}
