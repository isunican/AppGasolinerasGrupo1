package com.isunican.proyectobase.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Clase almacenadora de la informacion de una tarjeta de descuento
 * cuyo descuento se de en porcentaje sobre el precio final.
 * Hereda de la clase TarjetaDescuento e implementa los elementos unicos del tipo de tarjeta.
 * Implementa la interfaz Parceable para que despues se puedan pasar objetos
 * de este tipo entre interfaces a traves de intent.
 * Author: Adrian Celis Fernandez.
 * Date: 27/10/2020.
 */
public class TarjetaDescuentoPorcentaje extends TarjetaDescuento implements Parcelable {

    private double descuentoPorcentaje;

    /**
     * Constructor de la clase
     *
     * @param nombre
     * @param descripcion
     * @param marca
     * @param descuentoPorcentaje
     */
    public TarjetaDescuentoPorcentaje(String nombre, String descripcion, String marca, double descuentoPorcentaje) {
        super(nombre, descripcion, marca);
        this.descuentoPorcentaje = descuentoPorcentaje;
    }



    /**
     * Getter y Setter de descuentoPorcentaje
     */
    public double getDescuentoPorcentaje() { return descuentoPorcentaje; }
    public void setDescuentoPorcentaje(double descuentoPorcentaje) { this.descuentoPorcentaje = descuentoPorcentaje; }

    /**
     * Definicion del metodo toString para la clase TarjetaDescuentoPorcentaje
     * para poder obtener los datos en texto
     * @return String
     */
    @Override
    public String toString(){
        String textoTarjetaDescuento = "";
        textoTarjetaDescuento +=
                "Nombre: " + getNombre() + "\n"+
                "Descripcion: " + getDescripcion() + "\n"+
                "Descuento obtenido en porcentaje (precio total): " + getDescuentoPorcentaje() + " " + "\n\n";

        return textoTarjetaDescuento;
    }

    /**
     * interfaz Parcelable
     *
     * Métodos necesarios para implementar la interfaz Parcelable
     * que nos permitirá pasar objetos del tipo TarjetaDescuentoPorcentaje
     * directamente entre actividades utilizando intents
     * Se enviarían utilizando putExtra
     * myIntent.putExtra("id", objeto TarjetaDescuentoPorcentaje);
     * y recibiéndolos con
     * TarjetaDescuentoPorcentaje tdp = getIntent().getExtras().getParcelable("id")
     */

    protected TarjetaDescuentoPorcentaje(Parcel in) {
        super();
        setDescuentoPorcentaje(in.readDouble());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeDouble(getDescuentoPorcentaje());

    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<TarjetaDescuentoPorcentaje> CREATOR = new Parcelable.Creator<TarjetaDescuentoPorcentaje>() {
        @Override
        public TarjetaDescuentoPorcentaje createFromParcel(Parcel in) {
            return new TarjetaDescuentoPorcentaje(in);
        }

        @Override
        public TarjetaDescuentoPorcentaje[] newArray(int size) {
            return new TarjetaDescuentoPorcentaje[size];
        }
    };

}
