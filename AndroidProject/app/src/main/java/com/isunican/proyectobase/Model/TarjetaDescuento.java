package com.isunican.proyectobase.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Clase almacenadora de la informacion de una tarjeta de descuento.
 * Author: Adrian Celis Fernandez.
 * Date: 27/10/2020.
 */
public class TarjetaDescuento{
    private String nombre;
    private String descripcion;

    /**
     * Constructor de la clase
     */
    public TarjetaDescuento (String nombre, String descripcion){
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    /**
     * Constructor vacio. Necesario para la implementacion de la
     * interfaz Parcelable en las clases hijas
     */
    public TarjetaDescuento() {
    }

    /**
     * Conjunto de getters y setters para la modificacion de los elementos
     */
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }


}
