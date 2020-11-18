package com.isunican.proyectobase.Views;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.isunican.proyectobase.R;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withTagValue;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;

/*
 * @author: Adrian Celis
 * Clase de prueba de interfaz de añadir una gasolinera favorita
 */
@RunWith(AndroidJUnit4.class)
public class AnhadeGasolineraFavoritaUITest {

    private static final String COMENTARIOFUERALIMITE = "iiiiiiiiiiiiiiiiiiii" +
            "iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii" +
            "iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii" +
            "iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii" +
            "iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii";

    @Rule
    public IntentsTestRule<MainActivity> activityRule =
            new IntentsTestRule<>(MainActivity.class);

    @BeforeClass
    public static void cleanUp(){
        ApplicationProvider.getApplicationContext().deleteDatabase("RoomDatabase");
    }

    @Test
    public void anhadeFavorito() {
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

        Espresso.pressBack();

        // ID4: Guardar al anhadir comentario mayor de 240 caracteres (error)
        // Hacemos clic en la primera gasolinera de la lista
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.listViewGasolineras)).atPosition(0).perform(click());
        // Hacemos clic en el botón de añadir favorito
        onView(withId(R.id.favButton)).perform(click());
        // Escribimos un comentario
        onView(withId(R.id.textBox_anhadeComentario)).perform(typeText(COMENTARIOFUERALIMITE), closeSoftKeyboard());
        // Pulsamos guardar
        onView(withId(android.R.id.button1)).perform(click());
        // Obtenemos mensaje de error
        onView(withId(R.id.textBox_anhadeComentario)).check(matches(hasErrorText("El comentario debe ser menor de 240 carácteres")));

        Espresso.pressBack();
        Espresso.pressBack();

        // ID3: Guardar al anhadir comentario menor de 240 caracteres
        // Hacemos clic en la primera gasolinera de la lista
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.listViewGasolineras)).atPosition(1).perform(click());
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

        Espresso.pressBack();

        // ID2: Guardar al anhadir comentario vacio
        // Hacemos clic en la primera gasolinera de la lista
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.listViewGasolineras)).atPosition(0).perform(click());
        // Hacemos clic en el botón de añadir favorito
        onView(withId(R.id.favButton)).perform(click());
        // Pulsamos guardar
        onView(withId(android.R.id.button1)).perform(click());
        // Comprobamos que el comentario sale correctamente en la gasolinera
        onView(withId(R.id.comentarioText)).check(matches(withText("Comentario:\n")));
        // Se comprueba que el boton no ha cambiado
        onView(withId(R.id.favButton)).check(matches(withTagValue(Matchers.<Object>equalTo(R.drawable.favorito_activado))));
    }

}