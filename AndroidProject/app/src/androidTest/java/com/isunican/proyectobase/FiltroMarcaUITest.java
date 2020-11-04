package com.isunican.proyectobase;


import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.isunican.proyectobase.Views.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;



@RunWith(AndroidJUnit4.class)
public class FiltroMarcaUITest {

    @Rule
    public IntentsTestRule<MainActivity> activityRule =
            new IntentsTestRule<>(MainActivity.class);


    @Test
    public void filtroTipoMarcaTest()  {
        //Caso IVF.1.a: campo con la marca correcta
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getContext());
        onView(withText("Filtro marca")).perform(click());
        onView(withId(R.id.txtMarca)).perform(typeText("CEPSA"));
        onView(withText("OK")).perform(click());

        //Caso IVF.1.b: campo vacio
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getContext());
        onView(withText("Filtro marca")).perform(click());
        onView(withText("OK")).perform(click());
        onView(withId(R.id.txtMarca)).check(matches(hasErrorText("Campo vacío")));
        onView(withText("CANCEL")).perform(click());


        //Caso IVF.1.c: campo con marca invalida
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getContext());
        onView(withText("Filtro marca")).perform(click());
        onView(withId(R.id.txtMarca)).perform(typeText("Cep"));
        onView(withText("OK")).perform(click());
        onView(withId(R.id.txtMarca)).check(matches(hasErrorText("Marca inválida")));
        onView(withText("CANCEL")).perform(click());

        //Caso boton cancelar
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getContext());
        onView(withText("Filtro marca")).perform(click());
        onView(withText("CANCEL")).perform(click());
    }

}
