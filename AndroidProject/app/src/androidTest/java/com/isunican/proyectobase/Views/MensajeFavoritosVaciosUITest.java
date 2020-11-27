package com.isunican.proyectobase.Views;

import android.view.Gravity;

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

@RunWith(AndroidJUnit4.class)
public class MensajeFavoritosVaciosUITest {
    @Rule
    public ActivityTestRule<MainActivity>activityRule=new ActivityTestRule<>(MainActivity.class);
    @Test
    public void testMensajeFavoritos(){
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getContext());
        onView(withText("Filtrar favoritos")).perform(click());
        onView(withId(android.R.id.button1)).check(matches(withText("ACEPTAR")));
        onView(withId(android.R.id.message)).check(matches(withText("La lista de gasolineras favoritas está vacía")));
        UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).click(0, 100);
        //Comprobamos que estamos en la vista original.
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getContext());
    }
}
