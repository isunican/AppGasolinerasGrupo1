package com.isunican.proyectobase.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Clase almacenadora de la informacion de una tarjeta de descuento
 * cuyo descuento se de a traves de cada litro comprado.
 * Hereda de la clase TarjetaDescuento e implementa los elementos unicos del tipo de tarjeta.
 * Implementa la interfaz Parceable para que despues se puedan pasar objetos
 * de este tipo entre interfaces a traves de intent.
 * Author: Adrian Celis Fernandez.
 * Date: 27/10/2020.
 */
public class TarjetaDescuentoPorLitro extends TarjetaDescuento implements Parcelable{

    private double descuentoPorLitro;

    public TarjetaDescuentoPorLitro(String nombre, String descripcion, String marca, double descuentoPorLitro) {
        super(nombre, descripcion, marca);
        this.descuentoPorLitro = descuentoPorLitro;
    }

    public double getDescuentoPorLitro() { return descuentoPorLitro; }
    public void setDescuentoPorLitro(double descuentoPorLitro) { this.descuentoPorLitro = descuentoPorLitro; }

    /**
     * Definicion del metodo toString para la clase TarjetaDescuentoPorLitro
     * para poder obtener los datos en texto
     * @return String
     */
    @Override
    public String toString(){
        String textoTarjetaDescuento = "";
        textoTarjetaDescuento +=
                "Nombre: " + getNombre() + "\n"+
                "Descripcion: " + getDescripcion() + "\n"+
                "Marca: "+ getMarca()+ "\n"+
                "Descuento obtenido por litro: " + getDescuentoPorLitro() + " " + "\n\n";

        return textoTarjetaDescuento;
    }
    /**
     * interfaz Parcelable
     *
     * Métodos necesarios para implementar la interfaz Parcelable
     * que nos permitirá pasar objetos del tipo TarjetaDescuentoPorLitro
     * directamente entre actividades utilizando intents
     * Se enviarían utilizando putExtra
     * myIntent.putExtra("id", objeto TarjetaDescuentoPorLitro);
     * y recibiéndolos con
     * TarjetaDescuentoPorLitro tdp = getIntent().getExtras().getParcelable("id")
     */

    protected TarjetaDescuentoPorLitro(Parcel in) {
        super();
        setDescuentoPorLitro(in.readDouble());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeDouble(getDescuentoPorLitro());
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<TarjetaDescuentoPorLitro> CREATOR = new Parcelable.Creator<TarjetaDescuentoPorLitro>() {
        @Override
        public TarjetaDescuentoPorLitro createFromParcel(Parcel in) {
            return new TarjetaDescuentoPorLitro(in);
        }

        @Override
        public TarjetaDescuentoPorLitro[] newArray(int size) {
            return new TarjetaDescuentoPorLitro[size];
        }
    };

    public boolean equals(Object o){
        TarjetaDescuentoPorLitro t = (TarjetaDescuentoPorLitro) o;

        if(super.equals(t) && this.descuentoPorLitro == t.descuentoPorLitro) return true;
        return false;
    }

}
