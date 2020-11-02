package com.isunican.proyectobase;

import android.widget.ListView;

import androidx.test.rule.ActivityTestRule;

import com.isunican.proyectobase.Model.Gasolinera;
import com.isunican.proyectobase.Views.MainActivity;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isSelected;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

public class NuevaTarjetaDescuentoActivityUITest {

    private Gasolinera g;

    @Rule
    public ActivityTestRule<MainActivity> activityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void nuevaTarjetaDescuento(){

        // Comprobar el aspecto de la interfaz
        //ovView(withId(R.id.txtNombreTarjeta)).check(matches(withText("Nombre")));
        onView(withId(R.id.btnCancelar)).check(matches(isDisplayed()));
        onView(withId(R.id.btnGuardar)).check(matches(isDisplayed()));

        // Caso de prueba UC.1.a - Exito de anhadir una tarjeta de descuento por porcentaje
        onView(withId(R.id.nombreTarjeta)).perform(typeText("Tarjeta de prueba porcentual"));
        onView(withId(R.id.marcaGasolinera)).perform(typeText("CAMPSA"));
        onView(withId(R.id.spnTipoDescuento)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Porcentual"))).perform(click());
        onView(withId(R.id.spnTipoDescuento)).check(matches(withSpinnerText(containsString("Porcentual"))));
        onView(withId(R.id.descuento)).perform(typeText("1.25"));
        onView(withId(R.id.comentarios)).perform(typeText("Esto es una prueba"));

 // Pruebas de los toast



    }
}
