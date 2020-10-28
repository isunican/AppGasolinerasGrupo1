package com.isunican.proyectobase.Presenter;

import android.util.Log;

import com.isunican.proyectobase.Model.Gasolinera;
import com.isunican.proyectobase.Model.TarjetaDescuento;
import com.isunican.proyectobase.Model.TarjetaDescuentoPorLitro;
import com.isunican.proyectobase.Model.TarjetaDescuentoPorcentaje;

import java.util.ArrayList;

public class PresenterTarjetaDescuento {

    private ArrayList<TarjetaDescuento> listaDeTarjetasDelUsuario;

    public PresenterTarjetaDescuento(){
        // Esto no deberia de ir aqui si no a la hora de crear la aplicacion
        //TODO: Realizar una lectura y escritura de archivos para poder guardar las tarjetas y obtenerlas cuando se abra y cierre la aplicacion
        //TODO: Esta funcionalidad no tiene que estar aqui, tiene que estar en la main activity
        cargarListaDeTarjetasGuardadas();
        // Esto no deberia de crear una lista nueva
        // Hay que implementar la funcionalidad de lectura y escritura de datos
    }

    public ArrayList<TarjetaDescuento> getListaDeTarjetasDelUsuario() {
        return listaDeTarjetasDelUsuario;
    }

    public boolean anhadirNuevaTarjeta(String nombre, String descripcion, String tipoTarjeta, String descuento)
    {
        TarjetaDescuento tarjetaNueva;
        if (tipoTarjeta.equals("Porcentual")){
            tarjetaNueva = new TarjetaDescuentoPorcentaje(nombre, descripcion, Double.parseDouble(descuento));
        } else {
            tarjetaNueva = new TarjetaDescuentoPorLitro(nombre, descripcion, Double.parseDouble(descuento));
        }
        listaDeTarjetasDelUsuario.add(tarjetaNueva);
        return listaDeTarjetasDelUsuario.contains(tarjetaNueva);
    }

    public ArrayList<Gasolinera> actualizarListaDePrecios(ArrayList<Gasolinera> gasolineras){
        for (Gasolinera g: gasolineras) {
            for (TarjetaDescuento tarjetaDescuento: listaDeTarjetasDelUsuario){
                if (g.getRotulo().equals(tarjetaDescuento.getMarca())) {
                    g = cambioPrecios(tarjetaDescuento, g);
                }
            }
        }
        return gasolineras;
    }

    private Gasolinera cambioPrecios (TarjetaDescuento tarjetaDescuento, Gasolinera gasolinera) {
        if (tarjetaDescuento instanceof TarjetaDescuentoPorcentaje){
            gasolinera.setGasoleoA(
                    gasolinera.getGasoleoA()* ((TarjetaDescuentoPorcentaje) tarjetaDescuento).getDescuentoPorcentaje()
            );
            gasolinera.setGasolina95(
                    gasolinera.getGasolina95()* ((TarjetaDescuentoPorcentaje) tarjetaDescuento).getDescuentoPorcentaje()
            );
        } else if(tarjetaDescuento instanceof  TarjetaDescuentoPorLitro) {
            gasolinera.setGasoleoA(
                    gasolinera.getGasoleoA()- ((TarjetaDescuentoPorLitro) tarjetaDescuento).getDescuentoPorLitro()
            );
            gasolinera.setGasolina95(
                    gasolinera.getGasolina95()- ((TarjetaDescuentoPorLitro) tarjetaDescuento).getDescuentoPorLitro()
            );
        }
        return gasolinera;
    }


    public boolean cargarListaDeTarjetasGuardadas(){
        try {
            listaDeTarjetasDelUsuario = new ArrayList<>();
            listaDeTarjetasDelUsuario.add(new TarjetaDescuentoPorcentaje("TarjetaDescuento Repsol", "descripcion basica", 0.1));
            System.out.println("Carga correcta");
            return true;
        } catch (Exception e)
        {
            Log.e("ERROR", "No se ha podido cargar la lista de tarjetas guardadas");
            return false;
        }
    }
}
