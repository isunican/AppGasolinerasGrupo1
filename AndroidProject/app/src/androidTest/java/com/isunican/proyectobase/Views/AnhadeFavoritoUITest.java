package com.isunican.proyectobase.Views;

import android.media.Image;
import android.widget.ImageButton;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.isunican.proyectobase.R;

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
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;

/*
 * @author: Adrian Celis*
 * Clase de prueba de interfaz de añadir una gasolinera favorita
 */
@RunWith(AndroidJUnit4.class)
public class AnhadeFavoritoUITest {

    @Rule
    public IntentsTestRule<MainActivity> activityRule =
            new IntentsTestRule<>(MainActivity.class);

    @Test
    public void anhadeFavorito(){
        // Hacemos clic en la primera gasolinera de la lista
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.listViewGasolineras)).atPosition(0).perform(click());
        // Hacemos clic en el botón de añadir favorito
        onView(withId(R.id.favButton)).perform(click());
        // Escribimos un comentario
        onView(withId(R.id.textBox_anhadeComentario)).perform(typeText("Comentario de texto"), closeSoftKeyboard());
        // Pulsamos guardar
        onView(withText("GUARDAR")).perform(click());
        // Comprobamos que el comentario sale correctamente en la gasolinera
        onView(withId(R.id.comentarioText)).check(matches(withText("Comentario:\nComentario de texto")));
        // No se puede comprobar el image resource del boton
    }

}
