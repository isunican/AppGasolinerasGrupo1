package com.isunican.proyectobase.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.isunican.proyectobase.Model.GasolineraFavorita;

import java.util.List;
@Dao
public interface GasolineraFavoritaDAO {
    @Query("SELECT * FROM gasolinera_favorita")
    List<GasolineraFavorita> getAll();

    @Query("SELECT * FROM gasolinera_favorita WHERE id LIKE :id ")
    List<GasolineraFavorita> findById(int id);

    @Insert
    long insertOne(GasolineraFavorita e);

    @Delete
    void delete(GasolineraFavorita e);

    @Update
    void update(GasolineraFavorita e);

    @Query("DELETE FROM gasolinera_favorita")
    void nuke();

    @Query("SELECT id FROM gasolinera_favorita WHERE ROWID = :rowid")
    int getIdFromRowId(long rowid);
}
