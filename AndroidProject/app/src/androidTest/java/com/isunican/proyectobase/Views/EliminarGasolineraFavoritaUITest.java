package com.isunican.proyectobase.Views;


import android.opengl.EGLExt;
import android.widget.Button;
import android.widget.ListView;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.ViewAssertion;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.isunican.proyectobase.Model.Gasolinera;
import com.isunican.proyectobase.R;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withTagValue;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class EliminarGasolineraFavoritaUITest {
    private Gasolinera g;

    @Rule
    public ActivityTestRule<MainActivity> activityRule=new ActivityTestRule<>(MainActivity.class);

    @BeforeClass
    public static void cleanUp(){
        ApplicationProvider.getApplicationContext().deleteDatabase("RoomDatabase");
    }

    @Test
    public void testEliminarGasolineraFavoritaDesdeVistaDetallada(){
        //Set up
        onData(anything()).inAdapterView(withId(R.id.listViewGasolineras)).atPosition(0).perform(click());
        ListView listView=activityRule.getActivity().findViewById(R.id.listViewGasolineras);
        g = (Gasolinera)listView.getAdapter().getItem(0);
        onView(withId(R.id.favButton)).perform(click());
        onView(withId(android.R.id.button1)).perform(click());
        //UIT.1.a
        onView(withId(R.id.favButton)).perform(click());
        onView(withId(R.id.txt_confirmacion)).check(matches(withText("¿Quiere eliminar la gasolinera "+g.getRotulo()+" de su lista de favoritos?")));
        onView(withId(android.R.id.button1)).check(matches(withText("APLICAR")));
        onView(withId(android.R.id.button2)).check(matches(withText("CANCELAR")));
        //UIT.1.b
        onView(withId(android.R.id.button2)).perform(click());
        onView(withId(R.id.favButton)).check(matches(withTagValue(Matchers.<Object>equalTo(R.drawable.favorito_activado))));
        onView(withId(R.id.comentarioText)).check(matches(withText("Comentario:\n")));
        //UIT.1.c
        onView(withId(R.id.favButton)).perform(click());
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.favButton)).check(matches(withTagValue(Matchers.<Object>equalTo(R.drawable.favorito_desactivado))));
        onView(withId(R.id.comentarioText)).check(matches(withText("")));
    }

    /*
    @Test
    public void testEliminarGasolineraFavoritaDesdeListaFavoritos(){
        // Falta por implementar
    }
    */

}
