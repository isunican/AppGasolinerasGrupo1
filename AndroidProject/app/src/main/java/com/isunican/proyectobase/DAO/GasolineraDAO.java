package com.isunican.proyectobase.DAO;

import androidx.lifecycle.LiveData;
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

    @Query("SELECT * FROM gasolinera WHERE id LIKE :id ")
    List<Gasolinera> findById(int id);

    @Insert
    long insertOne(Gasolinera e);

    @Delete
    void delete(Gasolinera e);

    @Update
    void update(Gasolinera e);

    @Query("DELETE FROM gasolinera")
    void nuke();

    @Query("SELECT id FROM gasolinera WHERE ROWID = :rowid")
    int getIdFromRowId(long rowid);

}
