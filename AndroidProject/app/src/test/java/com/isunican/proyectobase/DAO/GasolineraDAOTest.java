package com.isunican.proyectobase.DAO;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import com.isunican.proyectobase.Database.AppDatabase;
import com.isunican.proyectobase.Model.Gasolinera;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

import java.util.List;

public class GasolineraDAOTest   {
    private GasolineraDAO gasolineraDao;
    private AppDatabase db;

    @Before
    public void createDb() {
        gasolineraDao = AppDatabase.getInstance(ApplicationProvider.getApplicationContext()).gasolineraDAO();
    }

    @After
    public void close(){
        AppDatabase.getInstance(ApplicationProvider.getApplicationContext()).close();
    }

    public void getAllTest(){
        //Caso 1: Tabla sin registros
        //Esto puede devolver EmptyResultSetException
        Assert.assertTrue(gasolineraDao.getAll().size() == 0);

        //Caso 2: Tabla con 1 solo resgistro
        Gasolinera sut = new Gasolinera(1, "Santander", "Cantabria", "Los Castros Nº1", 1.00, 1.00, "CEPSA");
        gasolineraDao.insertOne(sut);
        List<Gasolinera> lista = gasolineraDao.getAll();
        Assert.assertTrue(lista.size() == 1);
        //Esto puede que falle ya que la BD auto asigna la primary key y en el objeto que hemos creado no esta establecida
        //Se puede solucionar al realizar el metodo equals en la clase Gasolinera par que no tenga en cuenta el id
        Assert.assertTrue(lista.get(0).equals(sut));

        //Caso 3: Tabla con mas de 1 registro
        Gasolinera sut2 = new Gasolinera(2, "Torrelavega", "Cantabria", "La inmobiliaria", 1.50, 1.39, "CEPSA");
        gasolineraDao.insertOne(sut);
        lista = gasolineraDao.getAll();
        Assert.assertTrue(lista.size() == 2);
        Assert.assertTrue(lista.get(0).equals(sut) && lista.get(1).equals(sut2));
    }

    public void findByIdTest(){
        //Caso 1: Tabla vacia o no encuentra el registro buscado
        //Esto puede devolver EmptyResultSetException
        List<Gasolinera> lista = gasolineraDao.findById(5);
        Assert.assertTrue(lista.size() == 0);

        //Caso 2: Tabla contiene el elemento buscado
        lista = gasolineraDao.findById(1);
        Assert.assertTrue(lista.size() == 1);
        Assert.assertTrue(lista.get(0).getIdeess() == 1);
    }

    public void insertOne(){
        //Caso 1: Objeto nulo
        try{
            gasolineraDao.insertOne(null);
            Assert.fail();
        } catch (Exception e)
        {
            Assert.assertTrue(true);
        }
        //Caso 2: Objeto correcto
        Gasolinera sut = new Gasolinera(3, "Santander", "Cantabria", "Los Castros Nº1", 1.00, 1.00, "CEPSA");
        int preInsert = gasolineraDao.getAll().size();
        gasolineraDao.insertOne(sut);
    }
}
