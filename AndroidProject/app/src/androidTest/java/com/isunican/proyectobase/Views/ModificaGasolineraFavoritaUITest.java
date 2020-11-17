package com.isunican.proyectobase.Views;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.isunican.proyectobase.R;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withTagValue;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;

/*
 * @author:
 * Clase de prueba de interfaz de modificar una gasolinera favorita
 */
@RunWith(AndroidJUnit4.class)
public class ModificaGasolineraFavoritaUITest {

    @Rule
    public IntentsTestRule<FiltroFavoritosActivity> activityRule =
            new IntentsTestRule<>(FiltroFavoritosActivity.class);

    @BeforeClass
    public static void cleanUp(){
        ApplicationProvider.getApplicationContext().deleteDatabase("RoomDatabase");
    }
    @Before
    public void setUp(){
        // Guardamos una gasolinera en favoritos
        // Hacemos clic en la primera gasolinera de la lista
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.listFavGasolineras)).atPosition(0).perform(click());
        // Hacemos clic en el botón de añadir favorito
        onView(withId(R.id.favButton)).perform(click());
        // Escribimos un comentario
        onView(withId(R.id.textBox_anhadeComentario)).perform(typeText("Comentario de texto"), closeSoftKeyboard());
        // Pulsamos guardar
        onView(withId(android.R.id.button1)).perform(click());

        Espresso.pressBack();
    }

    @Test
    public void modificaFavorito() {
        // ID1: Cancelamos al modificar una gasolinera favorita
        // Hacemos clic en la primera gasolinera de la lista en el boton de modificar
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.listFavGasolineras)).atPosition(0).
                onChildView(withId(R.id.buttonModifica)).perform(click());
        // Comprobamos que en la caja de texto aparece el comentario antiguo
        onView(withId(R.id.textBox_modificaComentario)).check(matches(withText("Comentario de texto")));
        // Pulsamos cancelar
        onView(withId(android.R.id.button2)).perform(click());
        // Comprobamos que el comentario no ha cambiado
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.listFavGasolineras)).atPosition(0).perform(click());
        // Comprobamos que el comentario sale correctamente en la gasolinera
        onView(withId(R.id.comentarioText)).check(matches(withText("Comentario:\nComentario de texto")));

        Espresso.pressBack();

        // ID2: Guardamos al modificar una gasolinera favorita
        // Hacemos clic en la primera gasolinera de la lista en el boton de modificar
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.listFavGasolineras)).atPosition(0).
                onChildView(withId(R.id.buttonModifica)).perform(click());
        // Comprobamos que en la caja de texto aparece el comentario antiguo
        onView(withId(R.id.textBox_modificaComentario)).check(matches(withText("Comentario de texto")));
        // Escribimos un comentario
        onView(withId(R.id.textBox_modificaComentario)).perform(clearText(), typeText("Nuevo comentario de texto"), closeSoftKeyboard());
        // Pulsamos guardar
        onView(withId(android.R.id.button1)).perform(click());
        // Comprobamos que el comentario no ha cambiado
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.listFavGasolineras)).atPosition(0).perform(click());
        // Comprobamos que el comentario sale correctamente en la gasolinera
        onView(withId(R.id.comentarioText)).check(matches(withText("Comentario:\nNuevo comentario de texto")));

    }

}