package com.isunican.proyectobase;


import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.isunican.proyectobase.Views.FiltrosActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4.class)
public class FiltroTipoGasolinaUITest {

    @Rule
    public IntentsTestRule<FiltrosActivity> activityRule =
            new IntentsTestRule<>(FiltrosActivity.class);

    @Test
    public void filtroTipoGasolinaTest(){
        onView(withId(R.id.spinner_tipoGasolina)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Diesel"))).perform(click());
        onView(withId(R.id.aplicar_button)).perform(click());
    }
}
