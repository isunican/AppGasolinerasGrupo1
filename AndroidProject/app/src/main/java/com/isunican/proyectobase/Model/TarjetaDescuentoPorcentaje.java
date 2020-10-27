package com.isunican.proyectobase.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class TarjetaDescuentoPorcentaje extends TarjetaDescuento implements Parcelable {

    private double descuentoPorcentaje;

    public TarjetaDescuentoPorcentaje(String nombre, String descripcion, double descuentoPorcentaje) {
        super(nombre, descripcion);
        this.descuentoPorcentaje = descuentoPorcentaje;
    }

    /**
     * Constructor de la clase
     *
     * @param nombre
     * @param descripcion
     */
    public TarjetaDescuentoPorcentaje(String nombre, String descripcion) {
        super(nombre, descripcion);
    }

    public double getDescuentoPorcentaje() { return descuentoPorcentaje; }
    public void setDescuentoPorcentaje(double descuentoPorcentaje) { this.descuentoPorcentaje = descuentoPorcentaje; }

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
                "Descuento obtenido en porcentaje (precio total): " + getDescuentoPorcentaje() + " " + "\n\n";

        return textoTarjetaDescuento;
    }

    /**
     * interfaz Parcelable
     *
     * Métodos necesarios para implementar la interfaz Parcelable
     * que nos permitirá pasar objetos del tipo Gasolinera
     * directamente entre actividades utilizando intents
     * Se enviarían utilizando putExtra
     * myIntent.putExtra("id", objeto TarjetaDescuento);
     * y recibiéndolos con
     * TarjetaDescuento td = getIntent().getExtras().getParcelable("id")
     */

    protected TarjetaDescuentoPorcentaje(Parcel in) {
        setNombre(in.readString());
        setDescripcion(in.readString());
        setDescuentoPorcentaje(in.readDouble());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getNombre());
        dest.writeString(getDescripcion());
        dest.writeDouble(descuentoPorcentaje);

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
