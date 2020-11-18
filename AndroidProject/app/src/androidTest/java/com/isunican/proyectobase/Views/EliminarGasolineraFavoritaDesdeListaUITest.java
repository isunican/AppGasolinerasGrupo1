package com.isunican.proyectobase.Views;

import android.widget.ListView;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.isunican.proyectobase.Model.Gasolinera;
import com.isunican.proyectobase.R;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;

/**
 * Clase test que realiza las pruebas de las interfaces de eliminar la gasolinera desde la lista de gasolineras favoritas
 * Pruebas con identificador UIT.2.X
 *
 * @author Jaime López-Agudo Higuera
 */
@RunWith(AndroidJUnit4.class)
public class EliminarGasolineraFavoritaDesdeListaUITest {

    Gasolinera g;
    @Rule
    public ActivityTestRule<FiltroFavoritosActivity> activityRule=new ActivityTestRule<>(FiltroFavoritosActivity.class);

    @BeforeClass
    public static void beforeClass() {
        ApplicationProvider.getApplicationContext().deleteDatabase("RoomDatabase");
    }

    @Test
    public void eliminarGasolineraFavoritaDesdeListaTest(){
        //Set up, añadimos la gasolinera para realizar las pruebas
        ListView listView=activityRule.getActivity().findViewById(R.id.listFavGasolineras);
        g = (Gasolinera)listView.getAdapter().getItem(0);
        onData(anything()).inAdapterView(withId(R.id.listFavGasolineras)).atPosition(0).perform(click());
        onView(withId(R.id.favButton)).perform(click());
        onView(withId(android.R.id.button1)).perform(click());
        Espresso.pressBack();

        //UIT.2.a
        onData(anything()).inAdapterView(withId(R.id.listFavGasolineras)).atPosition(0).onChildView(withId(R.id.buttonElimina)).perform(click());
        onView(withId(R.id.txt_confirmacion)).check(matches(withText("¿Quiere eliminar la gasolinera "+g.getRotulo()+" de su lista de favoritos?")));
        onView(withId(android.R.id.button1)).check(matches(withText("APLICAR")));
        onView(withId(android.R.id.button2)).check(matches(withText("CANCELAR")));
        //Comprobamos que al pulsar boton de eliminar aparece ventana de dialogo con todos los campos

        //UIT.2.b
        onView(withId(android.R.id.button2)).perform(click());
        Assert.assertEquals(listView.getAdapter().getItem(0),g);
        //Comprobamos que al clickar en el boton de cancelar, la gasolinera no ha sido eliminada (existe en la lista todavia)

        //UIT2.c
        onData(anything()).inAdapterView(withId(R.id.listFavGasolineras)).atPosition(0).onChildView(withId(R.id.buttonElimina)).perform(click());
        onView(withId(android.R.id.button1)).perform(click());
        Assert.assertNotEquals(listView.getAdapter().getItem(0),g);
        //Comprobamos que al clickar en el botón de aplicar, la gasolinera ha sido eliminada de la lista de favoritos (no existe en la lista ya)
    }

}

