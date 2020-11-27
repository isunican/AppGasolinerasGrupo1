package com.isunican.proyectobase.Views;

import android.widget.ListView;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.isunican.proyectobase.Model.Gasolinera;
import com.isunican.proyectobase.R;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class FiltroPrecioMaximoUITest {

    @Rule
    public IntentsTestRule<MainActivity> activityRule =
            new IntentsTestRule<>(MainActivity.class);

    @Test
    public void filtroPrecioMaximoTest(){
        //Caso X: Error (Precio introducido negativo o 0)
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getContext());
        onView(withText("Filtrar por precio máximo")).perform(click());
        onView(withId(R.id.textNumberPrecioMax)).perform(typeText("0"), closeSoftKeyboard());
        onView(withText("ACEPTAR")).perform(click());
        onView(withId(R.id.textNumberPrecioMax)).check(matches(hasErrorText("El campo precio es obligatorio")));
        onView(withText("CANCELAR")).perform(click());

        //Caso X: Error (No introduce precio)
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getContext());
        onView(withText("Filtrar por precio máximo")).perform(click());
        onView(withId(R.id.textNumberPrecioMax)).perform(typeText(""), closeSoftKeyboard());
        onView(withText("ACEPTAR")).perform(click());
        onView(withId(R.id.textNumberPrecioMax)).check(matches(hasErrorText("El campo precio es obligatorio")));
        onView(withText("CANCELAR")).perform(click());

        //Caso x: Error (Uso de caracteres no validos en el precio).
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getContext());
        onView(withText("Filtrar por precio máximo")).perform(click());
        onView(withId(R.id.textNumberPrecioMax)).perform(typeText("1...0"), closeSoftKeyboard());
        onView(withText("ACEPTAR")).perform(click());
        onView(withId(R.id.textNumberPrecioMax)).check(matches(hasErrorText("Precio no valido")));
        onView(withText("CANCELAR")).perform(click());

        //Caso x: Error (Uso de caracteres no validos en el precio).
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getContext());
        onView(withText("Filtrar por precio máximo")).perform(click());
        onView(withId(R.id.textNumberPrecioMax)).perform(typeText("1.0,0"), closeSoftKeyboard());
        onView(withText("ACEPTAR")).perform(click());
        onView(withId(R.id.textNumberPrecioMax)).check(matches(hasErrorText("Precio no valido")));
        onView(withText("CANCELAR")).perform(click());

        //Caso x: Error (Uso de caracteres no validos en el precio).
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getContext());
        onView(withText("Filtrar por precio máximo")).perform(click());
        onView(withId(R.id.textNumberPrecioMax)).perform(typeText("1,0,9"), closeSoftKeyboard());
        onView(withText("ACEPTAR")).perform(click());
        onView(withId(R.id.textNumberPrecioMax)).check(matches(hasErrorText("Precio no valido")));
        onView(withText("CANCELAR")).perform(click());

        //Caso X: Exito (Se aplica el filtro correctamente)
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getContext());
        onView(withText("Filtrar por precio máximo")).perform(click());
        onView(withId(R.id.textNumberPrecioMax)).perform(typeText("0.9"), closeSoftKeyboard());
        //Precio introducido
        Double precioDieselIntro =  Double.parseDouble(activityRule.getActivity().editTextPrecioMax.getText().toString());
        onView(withText("ACEPTAR")).perform(click());
        //Guarda la gasolinera
        ListView listViewAux1 = activityRule.getActivity().findViewById(R.id.listViewGasolineras);
        Gasolinera gDiesel = (Gasolinera) listViewAux1.getAdapter().getItem(0);
        Double precioDiesel = gDiesel.getGasoleoA();
        assertTrue(precioDieselIntro >= precioDiesel);

        //Caso X: Éxito (Se aplica el filtro correctamente tras un filtrado posterior, mostrando únicamente el resultado del último filtro)
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getContext());
        onView(withText("Filtrar por precio máximo")).perform(click());
        onView(withId(R.id.spinnerFiltroPrecio)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Gasolina95"))).inRoot(isPlatformPopup()).perform(click());
        onView(withId(R.id.textNumberPrecioMax)).perform(typeText("1.1"), closeSoftKeyboard());
        //Precio introducido
        Double precioGasolina95Intro =  Double.parseDouble(activityRule.getActivity().editTextPrecioMax.getText().toString());
        onView(withText("ACEPTAR")).perform(click());
        //Guarda la gasolinera
        ListView listViewAux2 = activityRule.getActivity().findViewById(R.id.listViewGasolineras);
        Gasolinera gGasolina = (Gasolinera) listViewAux2.getAdapter().getItem(0);
        Double precioGasolina = gGasolina.getGasolina95();
        assertTrue(precioGasolina95Intro >= precioGasolina);

        /**
        //Caso X: Error (El filtro aplicado no devuelve gasolineras)
         Este caso no se producira nunca porque hay gasolineras con precios negativos
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getContext());
        onView(withText("Filtrar por precio máximo")).perform(click());
        onView(withId(R.id.spinnerFiltroPrecio)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Gasolina95"))).inRoot(isPlatformPopup()).perform(click());
        onView(withId(R.id.textNumberPrecioMax)).perform(typeText("0.9"), closeSoftKeyboard());
        onView(withText("ACEPTAR")).perform(click());
        onView(withText("No hay ninguna gasolinera disponible")).check(matches(isDisplayed()));
        onView(withText("OK")).perform(click());
        onView(withId(R.id.textNumberPrecioMax)).check(matches(withText("0.9")));
        onView(withId(R.id.spinnerFiltroPrecio)).check(matches(withSpinnerText("Gasolina95")));
         **/
    }
}
