package com.isunican.proyectobase;


import android.app.AlertDialog;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.isunican.proyectobase.Views.FiltrosActivity;
import com.isunican.proyectobase.Views.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4.class)
public class FiltroTipoGasolinaUITest {

    private static final String SECOND_ITEM_TEXT = "Diesel";

    @Rule
    public IntentsTestRule<MainActivity> activityRule =
            new IntentsTestRule<>(MainActivity.class);

    @Test
    public void filtroTipoGasolinaTest(){
        onView(withId(R.id.button_test_filtroTipoGasolina)).perform(click());
        onView(withId(R.id.spinner_tipoGasolina)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.spinner_tipoGasolina)).atPosition(1).perform(click());
        //onData(allOf(is(instanceOf(String.class)))).atPosition(1).perform(click());
        onView(withText("OK")).perform(click());


    }
}
