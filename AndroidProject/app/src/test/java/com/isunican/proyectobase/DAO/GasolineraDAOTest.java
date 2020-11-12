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

import java.util.ArrayList;
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
        db.gasolineraDAO().insertOne(sut);
        lista = db.gasolineraDAO().findById(1);
        Assert.assertTrue(lista.size() == 1);
        Assert.assertTrue(lista.get(0).getIdeess() == 1);
    }

    @Test
    public void insertOneTest(){
        // Caso 1: Objeto nulo
        try{
            db.gasolineraDAO().insertOne(null);
            Assert.fail();
        } catch (Exception e)
        {
            Assert.assertTrue(true);
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
    public void insertAllTest(){
        //al insertAll se le pasan lasgasolineras como parametros, no una lista
        // Caso 1: Lista vacia
        // Caso 2: Null
        // Caso 3: Lista con 1 elemento
        // Caso 4: Lista con mas 2 o mas elementos
    }
}
