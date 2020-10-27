package com.isunican.proyectobase.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class TarjetaDescuentoPorLitro extends TarjetaDescuento implements Parcelable{

    private double descuentoPorLitro;

    public TarjetaDescuentoPorLitro(String nombre, String descripcion, double descuentoPorLitro) {
        super(nombre, descripcion);
        this.descuentoPorLitro = descuentoPorLitro;
    }

    public double getDescuentoPorLitro() { return descuentoPorLitro; }
    public void setDescuentoPorLitro(double descuentoPorLitro) { this.descuentoPorLitro = descuentoPorLitro; }

    /**
     * Definicion del metodo toString para la clase TarjetaDescuento
     * para poder obtener los datos en texto
     * @return String
     */
    @Override
    public String toString(){
        String textoTarjetaDescuento = "";
        textoTarjetaDescuento +=
                "Nombre: " + getNombre() + "\n"+
                "Descripcion: " + getDescripcion() + "\n"+
                "Descuento obtenido por litro: " + getDescuentoPorLitro() + " " + "\n\n";

        return textoTarjetaDescuento;
    }

    protected TarjetaDescuentoPorLitro(Parcel in) {
        setNombre(in.readString());
        setDescripcion(in.readString());
        setDescuentoPorLitro(in.readDouble());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getNombre());
        dest.writeString(getDescripcion());
        dest.writeDouble(descuentoPorLitro);

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
}
