package com.isunican.proyectobase.Views;

import android.media.Image;
import android.widget.ImageButton;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.espresso.matcher.ViewMatchers;
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
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;

/*
 * @author: Adrian Celis*
 * Clase de prueba de interfaz de modificar una gasolinera favorita
 */
@RunWith(AndroidJUnit4.class)
public class ModificaFavoritoUITest {

    @Rule
    public IntentsTestRule<MainActivity> activityRule =
            new IntentsTestRule<>(MainActivity.class);

    @Test
    public void modificaFavorito(){
        // Abre menu superior derecho para acceder al boton de mostrar lista de gasolineras favoritas
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getContext());
        onView(withText("Favoritos")).perform(click());
        // Click en modificar en la primera gasolinera de la lista
        onData(withId(R.id.buttonModifica)).inAdapterView(ViewMatchers.withId(R.id.listViewGasolineras)).atPosition(0).perform(click());
        // Modificamos el comentario reemplazando el texto que encontremos
        onView(withId(R.id.textBox_anhadeComentario)).perform(replaceText("Nuevo comentario"));
        // Pulsamos guardar
        onView(withText("GUARDAR")).perform(click());
        // Click en la misma gasolinera para ver la vista detallada
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.listViewGasolineras)).atPosition(0).perform(click());
        // Comprobamos que el comentario se ha actualizado
        onView(withId(R.id.comentarioText)).check(matches(withText("Comentario:\nNuevo comentario")));
    }

}
