package com.isunican.proyectobase.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.isunican.proyectobase.DAO.GasolineraDAO;
import com.isunican.proyectobase.DAO.GasolineraFavoritaDAO;
import com.isunican.proyectobase.Model.Gasolinera;
import com.isunican.proyectobase.Model.GasolineraFavorita;

@Database(entities = {Gasolinera.class, GasolineraFavorita.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;

    public abstract GasolineraDAO gasolineraDAO();

    public abstract GasolineraFavoritaDAO gasolineraFavoritaDAO();

    public static synchronized AppDatabase getInstance(Context context){
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, "RoomDatabase").allowMainThreadQueries().build();
        }
        return INSTANCE;
    }
}
