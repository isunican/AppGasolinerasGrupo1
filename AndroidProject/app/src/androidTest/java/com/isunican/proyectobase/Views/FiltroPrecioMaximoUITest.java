package com.isunican.proyectobase.Views;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.isunican.proyectobase.R;

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
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4.class)
public class FiltroPrecioMaximoUITest {

    @Rule
    public IntentsTestRule<MainActivity> activityRule =
            new IntentsTestRule<>(MainActivity.class);

    @Test
    public void filtroPrecioMaximoTest(){
        //Caso X: El precio es 0

        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getContext());
        onView(withText("Filtrar por precio máximo")).perform(click());
        onView(withId(R.id.textNumberPrecioMax)).perform(typeText("0"), closeSoftKeyboard());
        onView(withText("ACEPTAR")).perform(click());
        onView(withId(R.id.textNumberPrecioMax)).check(matches(hasErrorText("El campo precio es obligatorio")));
        onView(withText("CANCELAR")).perform(click());
        //Caso X: El campo precio esta vacio

        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getContext());
        onView(withText("Filtrar por precio máximo")).perform(click());
        onView(withId(R.id.textNumberPrecioMax)).perform(typeText(""), closeSoftKeyboard());
        onView(withText("ACEPTAR")).perform(click());
        onView(withId(R.id.textNumberPrecioMax)).check(matches(hasErrorText("El campo precio es obligatorio")));

        /**
        //Caso X: No hay ninguna gasolinera con esos parametros (Comprobamos que dialogo se mantiene con los datos)
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getContext());
        onView(withText("Filtrar por precio máximo")).perform(click());
        onView(withId(R.id.spinnerFiltroPrecio)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Gasoleo 95"))).inRoot(isPlatformPopup()).perform(click());
        onView(withId(R.id.textNumberPrecioMax)).perform(typeText("1.2"), closeSoftKeyboard());
        onView(withText("ACEPTAR")).perform(click());
        onView(withText("No hay ninguna gasolineras disponible")).check(matches(isDisplayed()));
        onView(withText("OK")).perform(click());
        onView(withId(R.id.textNumberPrecioMax)).check(matches(withText("1.2")));
        onView(withId(R.id.spinnerFiltroPrecio)).check(matches(withSpinnerText("Gasoleo 95")));
         **/
    }
}
