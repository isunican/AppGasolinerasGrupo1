package com.isunican.proyectobase.Presenter;

import android.content.Context;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class PresenterTarjetaDescuentoTest {
    PresenterTarjetaDescuento sut;

    public PresenterTarjetaDescuentoTest(){
        this.sut = new PresenterTarjetaDescuento();
    }

    @Test
    public void anahdirNuevaTarjetaTest() {
        //Caso 1: "test", "test", "Cepsa", "Porcentual", "33.3"
        Assert.assertTrue(sut.anhadirNuevaTarjeta("test", "test", "Cepsa", "Porcentual", "33.3"));
        //Caso 2: "test", "test", "Cepsa", "Porcentual", "465"
        Assert.assertFalse(sut.anhadirNuevaTarjeta("test", "test", "Cepsa", "Porcentual", "456"));

    }
}
