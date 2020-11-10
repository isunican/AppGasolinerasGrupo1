package com.isunican.proyectobase.Model;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.isunican.proyectobase.Converters.DateConverter;

import java.time.OffsetDateTime;
import java.util.Date;
import java.util.Objects;

@Entity(tableName = "gasolinera_favorita")
public class GasolineraFavorita {

    @PrimaryKey(autoGenerate = true)
    private int id ;
    @ColumnInfo
    @TypeConverters({DateConverter.class})
    private Date date;
    @ColumnInfo
    private String comentario;
    @ColumnInfo(index = true, name = "id_gasolinera")
    private int idGasolinera;

    public GasolineraFavorita(String comentario, int idGasolinera){
        this.comentario = comentario;
        this.idGasolinera = idGasolinera;
    }
    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public Date getDate() { return date; }

    public int getIdGasolinera() { return idGasolinera; }

    public String getComentario() { return comentario; }

    public void setComentario(String comentario) { this.comentario = comentario; }

    public void setDate(Date date) { this.date = date; }

    public void setIdGasolinera(int idGasolinera) { this.idGasolinera = idGasolinera; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GasolineraFavorita that = (GasolineraFavorita) o;
        return idGasolinera == that.idGasolinera &&
                Objects.equals(date, that.date) &&
                Objects.equals(comentario, that.comentario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, comentario, idGasolinera);
    }
}
