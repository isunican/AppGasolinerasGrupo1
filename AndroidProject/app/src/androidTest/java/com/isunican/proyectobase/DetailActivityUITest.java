package com.isunican.proyectobase;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.isunican.proyectobase.Model.Gasolinera;
import com.isunican.proyectobase.Views.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4.class)
public class DetailActivityUITest {

    private Gasolinera g;

    @Rule
    public ActivityTestRule<MainActivity> activityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Before
    public void init(){
        g = new Gasolinera(0,"", "CANTABRIA", "CR N-629 79,7",
                1.129, 1.215, "REPSOL");
    }

    @Test
    public void vistaDetalladaGasolinera(){
        //onData(allOf(is(instanceOf(Gasolinera.class)), is(g))).perform(click());
        onData(is(g)).inAdapterView(withId(R.id.listViewGasolineras)).perform(click());
        onView(withId(R.id.nombreGasolineraText)).check(matches(withText("REPSOL")));
        onView(withId(R.id.direccionText)).check(matches(withText("Direccion:\nCR N-629 79,7")));
        onView(withId(R.id.precioGasoleoAText)).check(matches(withText("Gasoleo A: 1.129€")));
        onView(withId(R.id.precioGasoleo95Text)).check(matches(withText("Gasoleo 95: 1.215€")));
    }
}
