package com.isunican.proyectobase.Views;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.isunican.proyectobase.R;
import com.isunican.proyectobase.Views.MainActivity;

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
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withResourceName;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4.class)
public class AnhadeTarjetaDescuentoUITest {

    @Rule
    public IntentsTestRule<MainActivity> activityRule =
            new IntentsTestRule<>(MainActivity.class);

    @Test
    public void anahdeTarjetaDescuentoTest() {

        // Interfaz con el estilo deseado
        //onView(withId(R.id.button_test_anhadeTarjetaDescuento)).perform(click());
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getContext());
        onView(withText("Añade tarjeta descuento")).perform(click());
        onView(ViewMatchers.withId(R.id.txtNombreTarjeta)).check(matches(withText("Nombre:")));
        onView(withId(R.id.txtMarcaGasolinera)).check(matches(withText("Marca:")));
        onView(withId(R.id.txtTipoDescuento)).check(matches(withText("Tipo Descuento:")));
        onView(withId(R.id.txtDescuento)).check(matches(withText("Descuento:")));
        onView(withId(R.id.txtComentarios)).check(matches(withText("Comentarios:")));
        onView(withText("CANCELAR")).perform(click());

        // Datos correctos para descuento porcentual
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getContext());
        onView(withText("Añade tarjeta descuento")).perform(click());
        onView(withId(R.id.nombreTarjeta)).perform(typeText("Tarjeta CAMPSA"), closeSoftKeyboard());
        onView(withId(R.id.spnMarcas)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("CAMPSA"))).inRoot(isPlatformPopup()).perform(click());
        onView(withId(R.id.spnTipoDescuento)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Porcentual"))).inRoot(isPlatformPopup()).perform(click());
        onView(withId(R.id.descuento)).perform(typeText("25"),closeSoftKeyboard());
        onView(withId(R.id.comentarios)).perform(typeText("Tarjeta de descuento CAMPSA con 25% de descuento."),closeSoftKeyboard());
        onView(withText("GUARDAR")).perform(click());
    }

}
