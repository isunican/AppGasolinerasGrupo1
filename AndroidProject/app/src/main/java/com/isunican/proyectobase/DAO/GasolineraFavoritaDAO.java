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

    @Query("SELECT * FROM gasolinera_favorita WHERE id_gasolinera LIKE :id_gasolinera ")
    GasolineraFavorita findByGasolineraId(int id_gasolinera);

    @Insert
    void insertOne(GasolineraFavorita e);

    @Insert
    void insertAll(GasolineraFavorita... e);

    @Delete
    void delete(GasolineraFavorita e);

    @Update
    void update(GasolineraFavorita e);
}
