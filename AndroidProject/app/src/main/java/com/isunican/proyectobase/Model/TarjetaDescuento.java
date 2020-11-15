package com.isunican.proyectobase.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Clase almacenadora de la informacion de una tarjeta de descuento.
 * Author: Adrian Celis Fernandez.
 * Date: 27/10/2020.
 */
public abstract class TarjetaDescuento implements Parcelable{
    private String nombre;
    private String descripcion;
    private String marca;

    /**
     * Constructor de la clase
     */
    protected TarjetaDescuento (String nombre, String descripcion, String marca){
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.marca = marca;
    }

    /**
     * Constructor vacio. Necesario para la implementacion de la
     * interfaz Parcelable en las clases hijas
     */
    protected TarjetaDescuento() {
    }

    /**
     * Conjunto de getters y setters para la modificacion de los elementos
     */
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    protected TarjetaDescuento(Parcel in) {
        setNombre(in.readString());
        setDescripcion(in.readString());
        setMarca(in.readString());
    }

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getNombre());
        dest.writeString(getDescripcion());
        dest.writeString(getMarca());
    }

    public static final Parcelable.Creator<TarjetaDescuento> CREATOR = new Parcelable.Creator<TarjetaDescuento>() {
        @Override
        public TarjetaDescuento createFromParcel(Parcel in) {
            return null;
        }

        @Override
        public TarjetaDescuento[] newArray(int size) {
            return new TarjetaDescuento[0];
        }
    };

    @Override
    public boolean equals(Object o){
        TarjetaDescuento t = (TarjetaDescuento)o;

        if(this.descripcion.equals(t.descripcion) && this.marca.equals(t.marca)
                && this.nombre.equals(t.nombre)) return true;

        return false;
    }
}
