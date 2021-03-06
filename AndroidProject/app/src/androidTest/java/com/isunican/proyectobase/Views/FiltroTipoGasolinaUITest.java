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
import static androidx.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertTrue;

/*
 * Author: Miguel Carbayo y Jaime López-Agudo
 */
@RunWith(AndroidJUnit4.class)
public class FiltroTipoGasolinaUITest {

    private static final String FIRST_ITEM_TEXT = "Gasolina95";
    private static final String SECOND_ITEM_TEXT = "Diesel";
    private static final String OK = "ACEPTAR";
    private static final String CANCEL = "CANCELAR";

    @Rule
    public IntentsTestRule<MainActivity> activityRule =
            new IntentsTestRule<>(MainActivity.class);

    @Test
    public void filtroTipoGasolinaTest(){
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getContext());
        onView(withText("Filtrar por tipo de gasolina")).perform(click());
        onView(withId(R.id.spinner_tipoGasolina)).perform(click());
        onData(allOf(is(instanceOf(String.class)),is(FIRST_ITEM_TEXT))).inRoot(isPlatformPopup()).perform(click());
        onView(withId(R.id.spinner_tipoGasolina)).perform(click());
        onData(allOf(is(instanceOf(String.class)),is(SECOND_ITEM_TEXT))).inRoot(isPlatformPopup()).perform(click());
        onView(withText(OK)).perform(click());
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getContext());
        onView(withText("Filtrar por tipo de gasolina")).perform(click());
        onView(withText(CANCEL)).perform(click());
        assertTrue(true);
    }
}
