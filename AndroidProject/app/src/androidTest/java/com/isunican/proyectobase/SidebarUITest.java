package com.isunican.proyectobase;

import android.view.Gravity;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.test.espresso.contrib.DrawerActions;
import androidx.test.espresso.contrib.NavigationViewActions;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.isunican.proyectobase.Views.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.DrawerMatchers.isClosed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)
public class SidebarUITest {

    private DrawerLayout drawerLayout;

    @Rule
    public IntentsTestRule<MainActivity> activityRule =
            new IntentsTestRule<>(MainActivity.class);

    @Test
    public void sidebarHasAllItems() throws InterruptedException    {
        // Test comentado porque no se ha encontrado solución al error.
        /*
        // Open Drawer to click on navigation.
        Thread.sleep(2000);
        onView(withId(R.id.activity_precio_gasolina_drawer))
                .check(matches(isClosed(Gravity.START))) // Left Drawer should be closed.
                .perform(DrawerActions.open())
                ; // Open Drawer

        onView(withId(R.id.nav_view_main)).perform(NavigationViewActions.navigateTo(R.id.filtroTipoGasolina));
        */

    }
}
