package com.isunican.proyectobase.Views;

import android.view.Gravity;

import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.GeneralLocation;
import androidx.test.espresso.action.GeneralSwipeAction;
import androidx.test.espresso.action.Press;
import androidx.test.espresso.action.Swipe;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.DrawerActions;
import androidx.test.espresso.contrib.NavigationViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import androidx.test.uiautomator.UiDevice;

import com.isunican.proyectobase.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

/**
 * Test de interfaz de la historia de usuario Añadir aviso de lista de favoritos vacía, #404010
 *
 * @author Jaime López-Agudo Higuera
 */
@RunWith(AndroidJUnit4.class)
public class MensajeFavoritosVaciosUITest {
    @Rule
    public ActivityTestRule<MainActivity>activityRule=new ActivityTestRule<>(MainActivity.class);
    @Test
    public void testMensajeFavoritos(){
        onView(withId(R.id.activity_precio_gasolina_drawer)).perform(swipeRight());
        onView(withText("Filtrar favoritos")).perform(click());
        //404010UIT.1.a Comprobación de botones y texto de la interfaz.
        onView(withId(android.R.id.button1)).check(matches(withText("ACEPTAR")));
        onView(withId(android.R.id.message)).check(matches(withText("La lista de gasolineras favoritas está vacía")));
        //404010UIT.1.b (pulsar botón aceptar para cerrar)
        onView(withId(android.R.id.button1)).perform(click());
        //404010UIT.1.c (pulsar fuera de la ventana para cerrar).
        onView(withId(R.id.activity_precio_gasolina_drawer)).perform(swipeRight());
        onView(withText("Filtrar favoritos")).perform(click());
        UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).click(0, 100);
        //Comprobamos que estamos en la vista original.
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getContext());
    }
    public static ViewAction swipeRight() {
        return new GeneralSwipeAction(Swipe.FAST, GeneralLocation.CENTER_LEFT,
                GeneralLocation.CENTER_RIGHT, Press.FINGER);
    }
}
