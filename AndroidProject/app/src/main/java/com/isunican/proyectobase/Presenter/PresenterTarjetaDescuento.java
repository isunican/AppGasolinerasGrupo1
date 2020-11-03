package com.isunican.proyectobase.Presenter;

import android.util.Log;

import com.isunican.proyectobase.Model.Gasolinera;
import com.isunican.proyectobase.Model.TarjetaDescuento;
import com.isunican.proyectobase.Model.TarjetaDescuentoPorLitro;
import com.isunican.proyectobase.Model.TarjetaDescuentoPorcentaje;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
/**
 * -----------------------------------------------------
 * Presenter utilizado para la gestion de tarjetas de
 * descuento de la aplicacion
 * -----------------------------------------------------
 *
 * @author Adrian Celis
 * @version 0.1.4
 */
public class PresenterTarjetaDescuento {

    private ArrayList<TarjetaDescuento> listaTarjetasDescuento;

    /**
     * Constructor que inicializa la tarjeta de descuentos
     * para que pueda se utilizada
     */
    public PresenterTarjetaDescuento(){
        this.listaTarjetasDescuento = new ArrayList<TarjetaDescuento>();
    }

    /**
     * Retorna la lista de tarjetas de descuento
     * @return ArrayList de TarjetaDescuento
     */
    public ArrayList<TarjetaDescuento> getListaDeTarjetasDelUsuario() { return listaTarjetasDescuento; }

    /**
     * Establece la lista de tarjetas de descuento
     * @param listaDeTarjetasDelUsuario lista de tarjetas a establecer
     */
    public void setListaDeTarjetasDelUsuario(ArrayList<TarjetaDescuento> listaDeTarjetasDelUsuario) {
        this.listaTarjetasDescuento = listaDeTarjetasDelUsuario;
    }

    /**
     * Crea una nueva tarjeta con los parametros otorgados
     * e incorpora la tarjeta sobre la lista
     * @param nombre nombre de la tarjeta
     * @param descripcion descripcion sobre la tarjeta
     * @param marca marca sobre la que se aplican los descuentos
     * @param tipoTarjeta tipo de tarjeta de descuento
     * @param descuento descuento que se aplica
     * @return true si la tarjeta se ha guardado correactemente, si no false.
     */
    public boolean anhadirNuevaTarjeta(String nombre, String descripcion, String marca, String tipoTarjeta, String descuento)
    {
        TarjetaDescuento tarjetaNueva;
        double discount;
        try{
            discount = Double.parseDouble(descuento);
        }catch (Exception e){
            return false;
        }
        if (tipoTarjeta.equals("Porcentual")){
            if (discount>100 || discount<0) return false;
            if (discount>1) discount = discount/100;
            tarjetaNueva = new TarjetaDescuentoPorcentaje(nombre, descripcion, marca, discount);
        } else if (tipoTarjeta.equals("cts/Litro")) {
            tarjetaNueva = new TarjetaDescuentoPorLitro(nombre, descripcion, marca, discount);
        }else {
            return false;
        }
        listaTarjetasDescuento.add(tarjetaNueva);
        System.out.println(tarjetaNueva.toString());
        return listaTarjetasDescuento.contains(tarjetaNueva);
    }

    /**
     * Actualiza la lista de precios haciendo uso de la
     * tarjetas que se encuentran en la lista
     * @param gasolineras listado de tarjetas a actualizar
     * @return listado de gasolineras con precios actualizados
     */
    public List<Gasolinera> actualizarListaDePrecios(List<Gasolinera> gasolineras){
        for (Gasolinera g: gasolineras) {
            g = cambioPrecios(g);
        }
        return new ArrayList<Gasolinera>(gasolineras);
    }

    /**
     * Metodo privado que recive una gasolinera y actualiza sus precios
     * dependiendo de las tarjetas de descuento que se encuentren en la lista
     * @param gasolinera gasolinera a actualizar
     * @return gasolinera con los precios actualizados
     */
    //A la hora de aplicar el cambio de precios realiza los descuentos de cts/Litro y posteriormente el porcentaje
    private Gasolinera cambioPrecios (Gasolinera gasolinera) {
        double descuentoTotal = 0;
        double gasoleoA = 0;
        double gasolina95 = 0;
        for (TarjetaDescuento tarjetaDescuento: listaTarjetasDescuento){
            if (gasolinera.getRotulo().equals(tarjetaDescuento.getMarca())) {
                if (tarjetaDescuento instanceof TarjetaDescuentoPorcentaje){
                    descuentoTotal = ((TarjetaDescuentoPorcentaje) tarjetaDescuento).getDescuentoPorcentaje();
                } else if(tarjetaDescuento instanceof  TarjetaDescuentoPorLitro) {
                    gasoleoA = gasolinera.getGasoleoA() - ((TarjetaDescuentoPorLitro) tarjetaDescuento).getDescuentoPorLitro();
                    gasolina95 = gasolinera.getGasolina95() - ((TarjetaDescuentoPorLitro) tarjetaDescuento).getDescuentoPorLitro();
                    gasolinera.setGasoleoA(Math.round(gasoleoA*1000.0)/1000.0);
                    gasolinera.setGasolina95(Math.round(gasolina95*1000.0)/1000.0);
                }
            }
        }
        gasoleoA = gasolinera.getGasoleoA() -(gasolinera.getGasoleoA()*descuentoTotal);
        gasolina95 = gasolinera.getGasolina95() - (gasolinera.getGasolina95()*descuentoTotal);
        gasolinera.setGasoleoA(Math.round(gasoleoA*1000.0)/1000.0);
        gasolinera.setGasolina95(Math.round(gasolina95*1000.0)/1000.0);
        return gasolinera;
    }
}
