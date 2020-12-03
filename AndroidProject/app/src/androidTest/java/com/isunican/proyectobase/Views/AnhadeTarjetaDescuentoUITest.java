package com.isunican.proyectobase.Views;

import android.widget.ListView;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.isunican.proyectobase.Model.Gasolinera;
import com.isunican.proyectobase.Presenter.PresenterTarjetaDescuento;
import com.isunican.proyectobase.R;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4.class)
public class AnhadeTarjetaDescuentoUITest {

    @Rule
    public IntentsTestRule<MainActivity> activityRule =
            new IntentsTestRule<>(MainActivity.class);

    PresenterTarjetaDescuento presenterTarjetaDescuento = PresenterTarjetaDescuento.getInstance();


    @Test
    public void anahdeTarjetaDescuentoTest() {

        // Interfaz con el estilo deseado
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getContext());
        onView(withText("Añadir tarjeta descuento")).perform(click());
        onView(ViewMatchers.withId(R.id.txtNombreTarjeta)).check(matches(withText("Nombre:")));
        onView(withId(R.id.txtMarcaGasolinera)).check(matches(withText("Marca:")));
        onView(withId(R.id.txtTipoDescuento)).check(matches(withText("Tipo Descuento:")));
        onView(withId(R.id.txtDescuento)).check(matches(withText("Descuento:")));
        onView(withId(R.id.txtComentarios)).check(matches(withText("Comentarios:")));
        onView(withText("CANCELAR")).perform(click());

        // UIT.1.a: Datos correctos para descuento porcentual
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getContext());
        onView(withText("Añadir tarjeta descuento")).perform(click());
        onView(withId(R.id.nombreTarjeta)).perform(typeText("Tarjeta de prueba porcentual"), closeSoftKeyboard());
        onView(withId(R.id.spnMarcas)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("CEPSA"))).inRoot(isPlatformPopup()).perform(click());
        onView(withId(R.id.spnTipoDescuento)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Porcentual"))).inRoot(isPlatformPopup()).perform(click());
        onView(withId(R.id.descuento)).perform(typeText("25"),closeSoftKeyboard());
        onView(withId(R.id.comentarios)).perform(typeText("Tarjeta de descuento CAMPSA con 25% de descuento."),closeSoftKeyboard());
        onView(withText("GUARDAR")).perform(click());
        //TODO: comprobar que la tarjeta se ha añadido correctamente

        // Vuelve a la Main Activity para comprobar los precios actualizados
        List<Gasolinera> lista = new ArrayList<>();
        ListView listView = activityRule.getActivity().findViewById(R.id.listViewGasolineras);
        lista.add((Gasolinera) listView.getAdapter().getItem(0));
        lista = presenterTarjetaDescuento.actualizarListaDePrecios(lista);
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.listViewGasolineras)).atPosition(0).onChildView(ViewMatchers.withId(R.id.textViewGasoleoA)).check(matches(withText(" "+lista.get(0).getGasoleoA()+"€")));
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.listViewGasolineras)).atPosition(0).onChildView(ViewMatchers.withId(R.id.textViewGasolina95)).check(matches(withText(" "+lista.get(0).getGasolina95()+"€")));


        // UIT.1.b: Campo de descuento vacio para descuento porcentual
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getContext());
        onView(withText("Añadir tarjeta descuento")).perform(click());
        onView(withId(R.id.nombreTarjeta)).perform(typeText("Tarjeta de prueba porcentual vacía"), closeSoftKeyboard());
        onView(withId(R.id.spnMarcas)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("CEPSA"))).inRoot(isPlatformPopup()).perform(click());
        onView(withId(R.id.spnTipoDescuento)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Porcentual"))).inRoot(isPlatformPopup()).perform(click());
        onView(withId(R.id.descuento)).perform(typeText(""),closeSoftKeyboard());
        onView(withId(R.id.descuento)).check(matches(hasErrorText("El campo Descuento es obligatorio")));
        onView(withId(R.id.comentarios)).perform(typeText(""),closeSoftKeyboard());
        onView(withText("GUARDAR")).perform(click());
        //TODO: comprobar que la tarjeta  no se ha añadido


        // UIT.2.a: Datos correctos para descuento de centimos/litro
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getContext());
        onView(withText("Añadir tarjeta descuento")).perform(click());
        onView(withId(R.id.nombreTarjeta)).perform(typeText("Tarjeta de prueba por litro"), closeSoftKeyboard());
        onView(withId(R.id.spnMarcas)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("CAMPSA"))).inRoot(isPlatformPopup()).perform(click());
        onView(withId(R.id.spnTipoDescuento)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("cts/L"))).inRoot(isPlatformPopup()).perform(click());
        onView(withId(R.id.descuento)).perform(typeText("7"),closeSoftKeyboard());
        onView(withId(R.id.comentarios)).perform(typeText(""),closeSoftKeyboard());
        onView(withText("GUARDAR")).perform(click());
        //TODO: comprobar que la tarjeta se ha añadido correctamente


        // UIT.2.b: Campo de descuento vacio para descuento de centimos/litro
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getContext());
        onView(withText("Añadir tarjeta descuento")).perform(click());
        onView(withId(R.id.nombreTarjeta)).perform(typeText("Tarjeta de prueba por litro vacía"), closeSoftKeyboard());
        onView(withId(R.id.spnMarcas)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("CAMPSA"))).inRoot(isPlatformPopup()).perform(click());
        onView(withId(R.id.spnTipoDescuento)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("cts/L"))).inRoot(isPlatformPopup()).perform(click());
        onView(withId(R.id.descuento)).perform(typeText(""),closeSoftKeyboard());
        onView(withId(R.id.descuento)).check(matches(hasErrorText("El campo Descuento es obligatorio")));
        onView(withId(R.id.comentarios)).perform(typeText(""),closeSoftKeyboard());
        onView(withText("GUARDAR")).perform(click());
        //TODO: comprobar que la tarjeta  no se ha añadido
        //TODO: comprobar que aparece el mensaje de error
    }

}
