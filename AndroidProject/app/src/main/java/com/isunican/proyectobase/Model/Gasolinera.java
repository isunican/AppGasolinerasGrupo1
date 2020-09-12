package com.isunican.proyectobase.Model;

import android.os.Parcel;
import android.os.Parcelable;


/*
------------------------------------------------------------------
    Clase que almacena la informacion de una gasolinera
    Implementa la interfaz Parceable, que permite que luego podamos
    pasar objetos de este tipo entre activities a traves de una llamada intent
------------------------------------------------------------------
*/

public class Gasolinera implements Parcelable {
    private int ideess;
    private String localidad;
    private String provincia;
    private String direccion;
    private double gasoleoA;
    private double gasolina95;
    private String rotulo;


    /**
     * Constructor, getters y setters
     */
    public Gasolinera (int ideess, String localidad, String provincia, String direccion, double gasoleoA, double gasolina95, String rotulo){
        this.ideess = ideess;
        this.localidad = localidad;
        this.provincia = provincia;
        this.direccion = direccion;
        this.gasoleoA = gasoleoA;
        this.gasolina95 = gasolina95;
        this.rotulo = rotulo;
    }

    public int getIdeess() { return ideess; }
    public void setIdeess(int ideess) { this.ideess = ideess; }

    public String getLocalidad() { return localidad; }
    public void setLocalidad(String localidad) { this.localidad = localidad; }

    public String getProvincia() { return provincia; }
    public void setProvincia(String provincia) { this.provincia = provincia; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public double getGasoleoA() { return gasoleoA; }
    public void setGasoleoA(double gasoleoA) { this.gasoleoA = gasoleoA; }

    public String getRotulo() { return rotulo; }
    public void setRotulo(String rotulo) { this.rotulo = rotulo; }

    public double getGasolina95() { return gasolina95; }
    public void setGasolina95(double gasolina95) { this.gasolina95 = gasolina95; }


    /**
     * toString
     *
     * Redefine el método toString para obtener los datos
     * de una Gasolinera en formato texto
     *
     * @param
     * @return String
     */
    @Override
    public String toString(){
        String textoGasolineras = "";
        textoGasolineras +=
                getRotulo() + "\n"+
                getDireccion() + "\n" +
                getLocalidad() + "\n" +
                "Precio diesel: " + getGasoleoA() + " " + "\n" +
                "Precio gasolina 95: " + getGasolina95() + " " + "\n\n";

        return textoGasolineras;
    }


    /**
     * interfaz Parcelable
     *
     * Métodos necesarios para implementar la interfaz Parcelable
     * que nos permitirá pasar objetos del tipo Gasolinera
     * directamente entre actividades utilizando intents
     * Se enviarían utilizando putExtra
     * myIntent.putExtra("id", objeto gasolinera);
     * y recibiéndolos con
     * Gasolinera g = getIntent().getExtras().getParcelable("id")
     */
    protected Gasolinera(Parcel in) {
        ideess = in.readInt();
        localidad = in.readString();
        provincia = in.readString();
        direccion = in.readString();
        gasoleoA = in.readDouble();
        gasolina95 = in.readDouble();
        rotulo = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ideess);
        dest.writeString(localidad);
        dest.writeString(provincia);
        dest.writeString(direccion);
        dest.writeDouble(gasoleoA);
        dest.writeDouble(gasolina95);
        dest.writeString(rotulo);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Gasolinera> CREATOR = new Parcelable.Creator<Gasolinera>() {
        @Override
        public Gasolinera createFromParcel(Parcel in) {
            return new Gasolinera(in);
        }

        @Override
        public Gasolinera[] newArray(int size) {
            return new Gasolinera[size];
        }
    };
}