package com.isunican.proyectobase.DAO;

import android.os.Build;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import com.isunican.proyectobase.Database.AppDatabaseTest;
import com.isunican.proyectobase.Model.Gasolinera;
import com.isunican.proyectobase.Model.GasolineraFavorita;

import org.junit.After;
import org.junit.Assert;;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.List;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {Build.VERSION_CODES.O_MR1})
public class GasolineraDAOTest   {
    private AppDatabaseTest db = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), AppDatabaseTest.class).allowMainThreadQueries().build();

    @After
    public void cleanUp(){
        db.gasolineraDAO().nuke();
    }

    @Test
    public void getAllTest(){
        // Caso 1: Tabla sin registros
        Assert.assertEquals(0, db.gasolineraDAO().getAll().size());

        // Caso 2: Tabla con 1 solo resgistro
        Gasolinera sut = new Gasolinera(1, "Santander", "Cantabria", "Los Castros Nº1", 1.00, 1.00, "CEPSA");
        db.gasolineraDAO().insertOne(sut);
        List<Gasolinera> lista = db.gasolineraDAO().getAll();
        Assert.assertEquals(1, lista.size());
        Assert.assertEquals(lista.get(0), sut);

        // Caso 3: Tabla con mas de 1 registro
        Gasolinera sut2 = new Gasolinera(2, "Torrelavega", "Cantabria", "La inmobiliaria", 1.50, 1.39, "CEPSA");
        db.gasolineraDAO().insertOne(sut2);
        lista = db.gasolineraDAO().getAll();
        Assert.assertEquals(2, lista.size());
        Assert.assertEquals(lista.get(0), sut);
        Assert.assertEquals(lista.get(1), sut2);
    }

    @Test
    public void findByIdTest(){
        // Caso 1: Tabla vacia o no encuentra el registro buscado
        List<Gasolinera> lista = db.gasolineraDAO().findById(5);
        Assert.assertEquals(0, lista.size());

        // Caso 2: Tabla contiene el elemento buscado
        Gasolinera sut = new Gasolinera(1, "Santander", "Cantabria", "Los Castros Nº1", 1.00, 1.00, "CEPSA");
        int res = db.gasolineraDAO().getIdFromRowId(db.gasolineraDAO().insertOne(sut));
        lista = db.gasolineraDAO().findById(res);
        Assert.assertEquals(1, lista.size());
        Assert.assertEquals(lista.get(0), sut);

        //Caso3: Dos o mas elementos en Tabla
        Gasolinera sut2 = new Gasolinera(2, "Torrelavega", "Cantabria", "La inmobiliaria", 1.50, 1.39, "CEPSA");
        res = db.gasolineraDAO().getIdFromRowId(db.gasolineraDAO().insertOne(sut2));
        lista = db.gasolineraDAO().findById(res);
        Assert.assertEquals(1, lista.size());
        Assert.assertEquals(lista.get(0), sut2);
    }

    @Test
    public void insertOneTest(){
        // Caso 1: Objeto nulo
        try{
            db.gasolineraDAO().insertOne(null);
            ;
        } catch (Exception e)
        {
            Assert.assertEquals(0, db.gasolineraDAO().getAll().size());
        }
        // Caso 2: Objeto correcto
        Gasolinera sut = new Gasolinera(3, "Santander", "Cantabria", "Los Castros Nº1", 1.00, 1.00, "CEPSA");
        int preInsert = db.gasolineraDAO().getAll().size();
        db.gasolineraDAO().insertOne(sut);
        List<Gasolinera> lista = db.gasolineraDAO().getAll();
        Assert.assertEquals(1, (lista.size() - preInsert));
        Assert.assertEquals(lista.get(lista.size() - 1), sut);
    }

    @Test
    public void deleteTest(){
        //Caso 1: Eliminar nulo
        try{
            db.gasolineraDAO().delete(null);
            Assert.fail();
        } catch (Exception e)
        {
            Assert.assertEquals(0, db.gasolineraDAO().getAll().size());
        }
        //Caso 2: Eliminar objeto no existente
        Gasolinera sut = new Gasolinera(1, "Santander", "Cantabria", "Los Castros Nº1", 1.00, 1.00, "CEPSA");
        db.gasolineraDAO().delete(sut);
        Assert.assertEquals(0, db.gasolineraDAO().getAll().size());

        //Caso 3: Eliminar objeto existente
        long rowid = db.gasolineraDAO().insertOne(sut);
        sut.setId(db.gasolineraDAO().getIdFromRowId(rowid));
        db.gasolineraDAO().delete(sut);
        Assert.assertEquals(0, db.gasolineraDAO().getAll().size());
    }

    @Test
    public void nukeTest(){
        // Caso 1: Tabla vacia
        db.gasolineraDAO().nuke();
        Assert.assertEquals(0, db.gasolineraDAO().getAll().size());
        //Caso 2: Tabla con 1 elemento
        Gasolinera sut = new Gasolinera(1, "Santander", "Cantabria", "Los Castros Nº1", 1.00, 1.00, "CEPSA");
        db.gasolineraDAO().insertOne(sut);
        db.gasolineraDAO().nuke();
        Assert.assertEquals(0, db.gasolineraDAO().getAll().size());
        // Caso 3: Tabla con 2 o mas elementos
        Gasolinera sut2 = new Gasolinera(2, "Torrelavega", "Cantabria", "La inmobiliaria", 1.50, 1.39, "CEPSA");
        Gasolinera sut3 = new Gasolinera(3, "Reinosa", "Cantabria", "Sin nombre", 1.30, 1.35, "CEPSA");
        db.gasolineraDAO().insertOne(sut);
        db.gasolineraDAO().insertOne(sut2);
        db.gasolineraDAO().insertOne(sut3);
        db.gasolineraDAO().nuke();
        Assert.assertEquals(0, db.gasolineraDAO().getAll().size());
    }

    @Test
    public void getIdFromRowIdTest(){
        // Caso 1: No existente (Puedo poner el ID que quiera debido a que la tabla se encuentra vacia)
        int res = db.gasolineraDAO().getIdFromRowId(27);
        Assert.assertEquals(0, res);
        // Caso 2: Correcto
        Gasolinera sut = new Gasolinera(1, "Santander", "Cantabria", "Los Castros Nº1", 1.00, 1.00, "CEPSA");
        long rest = db.gasolineraDAO().insertOne(sut);
        Assert.assertEquals(1, rest);
    }

    @Test
    public void findByIdEESSTest(){
        // Caso 1: No existente
        Gasolinera sut = new Gasolinera(1, "Santander", "Cantabria", "Los Castros Nº1", 1.00, 1.00, "CEPSA");
        db.gasolineraDAO().insertOne(sut);
        List<Gasolinera> lista = db.gasolineraDAO().findByIdEESS(2);
        Assert.assertEquals(0, lista.size());
        // Caso 2: Correcto con 1 elemento en tabla
        lista = db.gasolineraDAO().findByIdEESS(1);
        Assert.assertEquals(1, lista.size());
        Assert.assertEquals(lista.get(0), sut);
        // Caso 3: Correcto con 2 o mas elementos en tabla
        Gasolinera sut2 = new Gasolinera(2, "Torrelavega", "Cantabria", "La inmobiliaria", 1.50, 1.39, "CEPSA");
        Gasolinera sut3 = new Gasolinera(3, "Reinosa", "Cantabria", "Sin nombre", 1.30, 1.35, "CEPSA");
        db.gasolineraDAO().insertOne(sut2);
        db.gasolineraDAO().insertOne(sut3);
        lista = db.gasolineraDAO().findByIdEESS(2);
        Assert.assertEquals(1, lista.size());
        Assert.assertEquals(lista.get(0), sut2);
        lista = db.gasolineraDAO().findByIdEESS(3);
        Assert.assertEquals(1, lista.size());
        Assert.assertEquals(lista.get(0), sut3);
    }

    @Test
    public void updateTest(){
        // Caso 1: Nulo
        try {
            db.gasolineraDAO().update(null);
            Assert.fail();
        } catch ( Exception e )
        {
            Assert.assertTrue(true);
        }

        // Caso 2: Intentar actualizar una dupla que no existe en la base de datos
        Gasolinera sut = new Gasolinera(1, "Santander", "Cantabria", "Los Castros Nº1", 1.00, 1.00, "CEPSA");
        db.gasolineraDAO().update(sut);
        Assert.assertEquals(0, db.gasolineraDAO().getAll().size());
        // Caso 3: Tabla con 1 elemento
        long rowid = db.gasolineraDAO().insertOne(sut);
        sut.setId(db.gasolineraDAO().getIdFromRowId(rowid));
        sut.setDireccion("Pues al final no estaba tan cerca de casa pero esta bien");
        db.gasolineraDAO().update(sut);
        List<Gasolinera> lista = db.gasolineraDAO().getAll();
        Assert.assertEquals(1, lista.size());
        Assert.assertEquals(lista.get(0), sut);

        // Caso 3: Tabla con 2 o mas elementos
        Gasolinera sut2 = new Gasolinera(2, "Torrelavega", "Cantabria", "La inmobiliaria", 1.50, 1.39, "CEPSA");
        Gasolinera sut3 = new Gasolinera(3, "Reinosa", "Cantabria", "Sin nombre", 1.30, 1.35, "CEPSA");
        db.gasolineraDAO().insertOne(sut2);
        rowid = db.gasolineraDAO().insertOne(sut3);
        sut3.setId(db.gasolineraDAO().getIdFromRowId(rowid));
        sut3.setLocalidad("No esta a medio camino, esta casi en Castro, pero esta genial!!!");
        db.gasolineraDAO().update(sut3);
        Assert.assertEquals(3, db.gasolineraDAO().getAll().size());
        Assert.assertEquals(db.gasolineraDAO().findById(db.gasolineraDAO().getIdFromRowId(rowid)).get(0), sut3);
    }
}
