package com.isunican.proyectobase.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.isunican.proyectobase.Model.Gasolinera;

import java.util.List;
@Dao
public interface GasolineraDAO {
    @Query("SELECT * FROM gasolinera")
    List<Gasolinera> getAll();

    @Query("SELECT * FROM gasolinera WHERE ideess LIKE :id ")
    List<Gasolinera> findById(int id);

    @Insert
    void insertOne(Gasolinera e);

    @Insert
    void insertAll(Gasolinera... e);

    @Delete
    void delete(Gasolinera e);

    @Update
    void update(Gasolinera e);

    @Query("DELETE FROM gasolinera")
    void nuke();

}
