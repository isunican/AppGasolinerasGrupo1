package com.isunican.proyectobase.DAO;

import android.os.Build;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import com.isunican.proyectobase.Database.AppDatabaseTest;
import com.isunican.proyectobase.Model.Gasolinera;

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
        Assert.assertTrue(db.gasolineraDAO().getAll().size() == 0);

        // Caso 2: Tabla con 1 solo resgistro
        Gasolinera sut = new Gasolinera(1, "Santander", "Cantabria", "Los Castros Nº1", 1.00, 1.00, "CEPSA");
        db.gasolineraDAO().insertOne(sut);
        List<Gasolinera> lista = db.gasolineraDAO().getAll();
        Assert.assertTrue(lista.size() == 1);
        Assert.assertTrue(lista.get(0).equals(sut));

        // Caso 3: Tabla con mas de 1 registro
        Gasolinera sut2 = new Gasolinera(2, "Torrelavega", "Cantabria", "La inmobiliaria", 1.50, 1.39, "CEPSA");
        db.gasolineraDAO().insertOne(sut2);
        lista = db.gasolineraDAO().getAll();
        Assert.assertTrue(lista.size() == 2);
        Assert.assertTrue(lista.get(0).equals(sut));
        Assert.assertTrue(lista.get(1).equals(sut2));
    }

    @Test
    public void findByIdTest(){
        // Caso 1: Tabla vacia o no encuentra el registro buscado
        List<Gasolinera> lista = db.gasolineraDAO().findById(5);
        Assert.assertTrue(lista.size() == 0);

        // Caso 2: Tabla contiene el elemento buscado
        Gasolinera sut = new Gasolinera(1, "Santander", "Cantabria", "Los Castros Nº1", 1.00, 1.00, "CEPSA");
        int res = db.gasolineraDAO().getIdFromRowId(db.gasolineraDAO().insertOne(sut));
        lista = db.gasolineraDAO().findById(res);
        Assert.assertTrue(lista.size() == 1);
        Assert.assertTrue(lista.get(0).equals(sut));

        //Caso3: Dos o mas elementos en Tabla
        Gasolinera sut2 = new Gasolinera(2, "Torrelavega", "Cantabria", "La inmobiliaria", 1.50, 1.39, "CEPSA");
        res = db.gasolineraDAO().getIdFromRowId(db.gasolineraDAO().insertOne(sut2));
        System.out.println(res);
        lista = db.gasolineraDAO().findById(res);
        Assert.assertTrue(lista.size() == 1);
        Assert.assertTrue(lista.get(0).equals(sut2));
    }

    @Test
    public void insertOneTest(){
        // Caso 1: Objeto nulo
        try{
            db.gasolineraDAO().insertOne(null);
            ;
        } catch (Exception e)
        {
            Assert.assertTrue(db.gasolineraDAO().getAll().size()==0);
        }
        // Caso 2: Objeto correcto
        Gasolinera sut = new Gasolinera(3, "Santander", "Cantabria", "Los Castros Nº1", 1.00, 1.00, "CEPSA");
        int preInsert = db.gasolineraDAO().getAll().size();
        db.gasolineraDAO().insertOne(sut);
        List<Gasolinera> lista = db.gasolineraDAO().getAll();
        Assert.assertTrue((lista.size()-preInsert) == 1);
        Assert.assertTrue(lista.get(lista.size()-1).equals(sut));
    }

    @Test
    public void deleteTest(){
        //Caso 1: Eliminar nulo
        try{
            db.gasolineraDAO().delete(null);
            Assert.fail();
        } catch (Exception e)
        {
            Assert.assertTrue(db.gasolineraDAO().getAll().size()==0);
        }
        //Caso 2: Eliminar objeto no existente
        Gasolinera sut = new Gasolinera(1, "Santander", "Cantabria", "Los Castros Nº1", 1.00, 1.00, "CEPSA");
        db.gasolineraDAO().delete(sut);
        Assert.assertTrue(db.gasolineraDAO().getAll().size()==0);

        //Caso 3: Eliminar objeto existente
        long rowid = db.gasolineraDAO().insertOne(sut);
        sut.setId(db.gasolineraDAO().getIdFromRowId(rowid));
        db.gasolineraDAO().delete(sut);
        Assert.assertTrue(db.gasolineraDAO().getAll().size()==0);
    }

    @Test
    public void nukeTest(){
        // Caso 1: Tabla vacia
        db.gasolineraDAO().nuke();
        Assert.assertTrue(db.gasolineraDAO().getAll().size() == 0);
        //Caso 2: Tabla con 1 elemento
        Gasolinera sut = new Gasolinera(1, "Santander", "Cantabria", "Los Castros Nº1", 1.00, 1.00, "CEPSA");
        db.gasolineraDAO().insertOne(sut);
        db.gasolineraDAO().nuke();
        Assert.assertTrue(db.gasolineraDAO().getAll().size() == 0);
        // Caso 3: Tabla con 2 o mas elementos
        Gasolinera sut2 = new Gasolinera(2, "Torrelavega", "Cantabria", "La inmobiliaria", 1.50, 1.39, "CEPSA");
        Gasolinera sut3 = new Gasolinera(3, "Reinosa", "Cantabria", "Sin nombre", 1.30, 1.35, "CEPSA");
        db.gasolineraDAO().insertOne(sut);
        db.gasolineraDAO().insertOne(sut2);
        db.gasolineraDAO().insertOne(sut3);
        db.gasolineraDAO().nuke();
        Assert.assertTrue(db.gasolineraDAO().getAll().size() == 0);
    }

    @Test
    public void getIdFromRowIdTest(){
        // Caso 1: No existente (Puedo poner el ID que quiera debido a que la tabla se encuentra vacia)
        int res = db.gasolineraDAO().getIdFromRowId(27);
        Assert.assertTrue(res == 0);
        // Caso 2: Correcto
        Gasolinera sut = new Gasolinera(1, "Santander", "Cantabria", "Los Castros Nº1", 1.00, 1.00, "CEPSA");
        long rest = db.gasolineraDAO().insertOne(sut);
        Assert.assertTrue(rest == 1);
    }

    @Test
    public void findByIdEESSTest(){
        // Caso 1: No existente
        Gasolinera sut = new Gasolinera(1, "Santander", "Cantabria", "Los Castros Nº1", 1.00, 1.00, "CEPSA");
        db.gasolineraDAO().insertOne(sut);
        db.gasolineraDAO().findByIdEESS(2);
        // Caso 2: Correcto con 1 elemento en tabla
        List<Gasolinera> lista = db.gasolineraDAO().findByIdEESS(1);
        Assert.assertTrue(lista.size()==1);
        Assert.assertTrue(lista.get(0).equals(sut));
        // Caso 3: Correcto con 2 o mas elementos en tabla
        Gasolinera sut2 = new Gasolinera(2, "Torrelavega", "Cantabria", "La inmobiliaria", 1.50, 1.39, "CEPSA");
        Gasolinera sut3 = new Gasolinera(3, "Reinosa", "Cantabria", "Sin nombre", 1.30, 1.35, "CEPSA");
        db.gasolineraDAO().insertOne(sut2);
        db.gasolineraDAO().insertOne(sut3);
        lista = db.gasolineraDAO().findByIdEESS(2);
        Assert.assertTrue(lista.size()==1);
        Assert.assertTrue(lista.get(0).equals(sut2));
        lista = db.gasolineraDAO().findByIdEESS(3);
        Assert.assertTrue(lista.size()==1);
        Assert.assertTrue(lista.get(0).equals(sut3));
    }
}
