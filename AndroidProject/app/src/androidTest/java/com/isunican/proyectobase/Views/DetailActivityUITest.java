package com.isunican.proyectobase.Views;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.ListView;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.isunican.proyectobase.Model.Gasolinera;
import com.isunican.proyectobase.R;
import com.isunican.proyectobase.Views.DetailActivity;
import com.isunican.proyectobase.Views.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.appcompat.app.AppCompatActivity;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4.class)
public class DetailActivityUITest {

    private Gasolinera g;

    @Rule
    public ActivityTestRule<MainActivity> activityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void vistaDetalladaGasolinera(){
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.listViewGasolineras)).atPosition(0).perform(click());
        ListView listView = activityRule.getActivity().findViewById(R.id.listViewGasolineras);
        g = (Gasolinera) listView.getAdapter().getItem(0);
        onView(withId(R.id.nombreGasolineraText)).check(matches(withText(g.getRotulo())));
        onView(withId(R.id.direccionText)).check(matches(withText("Dirección:\n"+g.getDireccion())));
        onView(withId(R.id.precioGasoleoAText)).check(matches(withText("Gasoleo A: "+ g.getGasoleoA()+"€")));
        onView(withId(R.id.precioGasoleo95Text)).check(matches(withText("Gasoleo 95: "+g.getGasolina95()+"€")));
    }
}
