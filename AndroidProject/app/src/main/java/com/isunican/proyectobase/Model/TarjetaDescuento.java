package com.isunican.proyectobase.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Clase almacenadora de la informacion de una tarjeta de descuento.
 * Implementa la interfaz Parceable para que despues se puedan pasar objetos
 * de este tipo entre interfaces a traves de intent
 * Author: Adrian Celis Fernandez
 * Date: 27/10/2020
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
