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

    public boolean anhadirNuevaTarjeta(String nombre, String descripcion, String marca, String tipoTarjeta, String descuento)
    {
        TarjetaDescuento tarjetaNueva;
        if (tipoTarjeta.equals("Porcentual")){
            tarjetaNueva = new TarjetaDescuentoPorcentaje(nombre, descripcion, marca, Double.parseDouble(descuento));
        } else {
            tarjetaNueva = new TarjetaDescuentoPorLitro(nombre, descripcion, marca, Double.parseDouble(descuento));
        }
        listaDeTarjetasDelUsuario.add(tarjetaNueva);
        return listaDeTarjetasDelUsuario.contains(tarjetaNueva);
    }

    public ArrayList<Gasolinera> actualizarListaDePrecios(ArrayList<Gasolinera> gasolineras){
        for (Gasolinera g: gasolineras) {
            g = cambioPrecios(g);
        }
        return gasolineras;
    }
    //A la hora de aplicar el cambio de precios realiza los descuentos de cts/Litro y posteriormente el porcentaje
    private Gasolinera cambioPrecios (Gasolinera gasolinera) {
        double descuentoTotal = 0;
        for (TarjetaDescuento tarjetaDescuento: listaDeTarjetasDelUsuario){
            if (gasolinera.getRotulo().equals(tarjetaDescuento.getMarca())) {
                if (tarjetaDescuento instanceof TarjetaDescuentoPorcentaje){
                    descuentoTotal = ((TarjetaDescuentoPorcentaje) tarjetaDescuento).getDescuentoPorcentaje();
                } else if(tarjetaDescuento instanceof  TarjetaDescuentoPorLitro) {
                    gasolinera.setGasoleoA(
                            gasolinera.getGasoleoA() - ((TarjetaDescuentoPorLitro) tarjetaDescuento).getDescuentoPorLitro()
                    );
                    gasolinera.setGasolina95(
                            gasolinera.getGasolina95() - ((TarjetaDescuentoPorLitro) tarjetaDescuento).getDescuentoPorLitro()
                    );
                }
            }
        }
        gasolinera.setGasoleoA(
                gasolinera.getGasoleoA() -(gasolinera.getGasoleoA()*descuentoTotal)
        );
        gasolinera.setGasolina95(
                gasolinera.getGasolina95() - (gasolinera.getGasolina95()*descuentoTotal)
        );
        return gasolinera;
    }


    public boolean cargarListaDeTarjetasGuardadas(){
        try {
            listaDeTarjetasDelUsuario = new ArrayList<>();
            listaDeTarjetasDelUsuario.add(new TarjetaDescuentoPorcentaje("TarjetaDescuento Repsol", "descripcion basica", "Repsol",0.1));
            System.out.println("Carga correcta");
            return true;
        } catch (Exception e)
        {
            Log.e("ERROR", "No se ha podido cargar la lista de tarjetas guardadas");
            return false;
        }
    }
}
