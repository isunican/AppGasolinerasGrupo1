package com.isunican.proyectobase.Views;

import android.widget.ListView;

import androidx.test.annotation.UiThreadTest;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.isunican.proyectobase.Database.AppDatabase;
import com.isunican.proyectobase.Model.Gasolinera;
import com.isunican.proyectobase.Model.GasolineraFavorita;
import com.isunican.proyectobase.Presenter.PresenterTarjetaDescuento;
import com.isunican.proyectobase.R;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.isunican.proyectobase.Presenter.PresenterGasolineras.SANTANDER;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.comparesEqualTo;

@RunWith(AndroidJUnit4.class)
public class AplicarTarjetaDeDescuentosEnFavoritosUITest {

    PresenterTarjetaDescuento presenterTarjetaDescuento = PresenterTarjetaDescuento.getInstance();

    @Rule
    public IntentsTestRule<FiltroFavoritosActivity> activityRule =
            new IntentsTestRule<>(FiltroFavoritosActivity.class);

    @BeforeClass
    public static void beforeClass() {
        ApplicationProvider.getApplicationContext().deleteDatabase("RoomDatabase");
    }

    @Before
    @UiThreadTest
    public void setUp(){
        presenterTarjetaDescuento.anhadirNuevaTarjeta("Test-CAMPSA", "Tarjeta para test por cts/Litro", "CAMPSA", "cts/Litro", "0.349");
        presenterTarjetaDescuento.anhadirNuevaTarjeta("Test-AVIA", "Tarjeta para test de manera porcentual", "AVIA", "Porcentual", "0.5");
        List<Gasolinera> gasolineras = new ArrayList<Gasolinera>();
        gasolineras.add(new Gasolinera(1053,SANTANDER,SANTANDER, "Plaza Matias Montero", 1.4,1.349,"CAMPSA"));
        gasolineras.add(new Gasolinera(1000,SANTANDER,SANTANDER, "Av Valdecilla", 1.2,1.4,"AVIA"));
        gasolineras = activityRule.getActivity().aplicarTarjetasDeDescuento(gasolineras);
        activityRule.getActivity().listaActual = gasolineras;
        activityRule.getActivity().adapterFavoritas.addAll(gasolineras);
        activityRule.getActivity().adapterFavoritas.notifyDataSetChanged();
    }

    @Test
    public void compruebaPrecios(){
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.listFavGasolineras)).atPosition(0).onChildView(ViewMatchers.withId(R.id.textViewGasoleoAFav)).check(matches(withText(" 1.051€")));
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.listFavGasolineras)).atPosition(0).onChildView(ViewMatchers.withId(R.id.textViewGasolina95Fav)).check(matches(withText(" 1.0€")));
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.listFavGasolineras)).atPosition(1).onChildView(ViewMatchers.withId(R.id.textViewGasoleoAFav)).check(matches(withText(" 0.6€")));
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.listFavGasolineras)).atPosition(1).onChildView(ViewMatchers.withId(R.id.textViewGasolina95Fav)).check(matches(withText(" 0.7€")));
    }
}
