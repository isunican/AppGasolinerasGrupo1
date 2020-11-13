package com.isunican.proyectobase.Views;


import android.widget.ListView;

import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.isunican.proyectobase.Model.Gasolinera;
import com.isunican.proyectobase.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.anything;

@RunWith(AndroidJUnit4.class)
public class EliminarGasolineraFavoritaUITest {
    private Gasolinera g;

    @Rule
    public ActivityTestRule<MainActivity> activityRule=new ActivityTestRule<>(MainActivity.class);

    @Test
    public void eliminarGasolinera(){
        onData(anything()).inAdapterView(withId(R.id.listViewGasolineras)).atPosition(0).perform(click());
        ListView listView=activityRule.getActivity().findViewById(R.id.listViewGasolineras);
        g= (Gasolinera)listView.getAdapter().getItem(0);
        onView(withId(R.id.favButton)).perform(click());
        //onView(ViewMatchers.withId(R.id.))
    }
}
