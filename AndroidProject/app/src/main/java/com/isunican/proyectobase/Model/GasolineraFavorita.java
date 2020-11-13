package com.isunican.proyectobase.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.isunican.proyectobase.Converters.DateConverter;

import java.util.Date;
import java.util.Objects;

@Entity(tableName = "gasolinera_favorita", indices = @Index(value = {"id_gasolinera"}, unique = true))
public class GasolineraFavorita {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo
    private String comentario;
    @ColumnInfo(name = "id_gasolinera")
    private int idGasolinera;

    public GasolineraFavorita(String comentario, int idGasolinera){
        this.comentario = comentario;
        this.idGasolinera = idGasolinera;
    }
    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public int getIdGasolinera() { return idGasolinera; }

    public String getComentario() { return comentario; }

    public void setComentario(String comentario) { this.comentario = comentario; }

    public void setIdGasolinera(int idGasolinera) { this.idGasolinera = idGasolinera; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GasolineraFavorita that = (GasolineraFavorita) o;
        return idGasolinera == that.idGasolinera &&
                Objects.equals(comentario, that.comentario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(comentario, idGasolinera);
    }

    @Override
    public String toString(){
        String textoGasolinerasFavorita = "";
        textoGasolinerasFavorita +=
                "autoID: "+getId()+"\n"+
                "IdGasolinera: "+getIdGasolinera()+"\n"+
                "Comentario: "+getComentario();

        return textoGasolinerasFavorita;
    }
}
