package com.isunican.proyectobase.Views;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.isunican.proyectobase.R;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withTagValue;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;

/*
 * @author: Adrian Celis*
 * Clase de prueba de interfaz de añadir una gasolinera favorita
 */
@RunWith(AndroidJUnit4.class)
public class AnhadeGasolineraFavoritaUITest {

    @Rule
    public IntentsTestRule<MainActivity> activityRule =
            new IntentsTestRule<>(MainActivity.class);

    @Test
    public void anhadeFavorito(){
        // ID1: Cancelar al anhadir comentario
        // Hacemos clic en la primera gasolinera de la lista
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.listViewGasolineras)).atPosition(0).perform(click());
        // Hacemos clic en el botón de añadir favorito
        onView(withId(R.id.favButton)).perform(click());
        // Pulsamos cancelar
        onView(withId(android.R.id.button2)).perform(click());
        // Comprobamos que el comentario sale correctamente en la gasolinera
        onView(withId(R.id.comentarioText)).check(matches(withText("")));
        // Se comprueba que el boton no ha cambiado
        onView(withId(R.id.favButton)).check(matches(withTagValue(Matchers.<Object>equalTo(R.drawable.favorito_desactivado))));

        Espresso.pressBack(); //Volvemos a la vista anterior para los siguientes casos de prueba

        // ID2: Guardar al anhadir comentario
        // Hacemos clic en la primera gasolinera de la lista
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.listViewGasolineras)).atPosition(0).perform(click());
        // Hacemos clic en el botón de añadir favorito
        onView(withId(R.id.favButton)).perform(click());
        // Escribimos un comentario
        onView(withId(R.id.textBox_anhadeComentario)).perform(typeText("Comentario de texto"), closeSoftKeyboard());
        // Pulsamos guardar
        onView(withId(android.R.id.button1)).perform(click());
        // Comprobamos que el comentario sale correctamente en la gasolinera
        onView(withId(R.id.comentarioText)).check(matches(withText("Comentario:\nComentario de texto")));
        // Se comprueba que el boton no ha cambiado
        onView(withId(R.id.favButton)).check(matches(withTagValue(Matchers.<Object>equalTo(R.drawable.favorito_activado))));
    }

}