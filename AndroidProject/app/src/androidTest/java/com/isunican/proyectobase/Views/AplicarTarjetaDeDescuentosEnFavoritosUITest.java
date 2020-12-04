package com.isunican.proyectobase.Views;

import android.view.Gravity;
import android.widget.ListView;

import androidx.annotation.MainThread;
import androidx.core.view.GravityCompat;
import androidx.test.annotation.UiThreadTest;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.GeneralLocation;
import androidx.test.espresso.action.GeneralSwipeAction;
import androidx.test.espresso.action.Press;
import androidx.test.espresso.action.Swipe;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.DrawerActions;
import androidx.test.espresso.contrib.NavigationViewActions;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.isunican.proyectobase.DAO.GasolineraDAO;
import com.isunican.proyectobase.Database.AppDatabase;
import com.isunican.proyectobase.Model.Gasolinera;
import com.isunican.proyectobase.Presenter.PresenterGasolineras;
import com.isunican.proyectobase.Presenter.PresenterTarjetaDescuento;
import com.isunican.proyectobase.R;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.swipeRight;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.DrawerMatchers.isClosed;
import static androidx.test.espresso.contrib.DrawerMatchers.isOpen;
import static androidx.test.espresso.contrib.ViewPagerActions.scrollRight;
import static androidx.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.isunican.proyectobase.Presenter.PresenterGasolineras.SANTANDER;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;


@RunWith(AndroidJUnit4.class)
public class AplicarTarjetaDeDescuentosEnFavoritosUITest {
    @Rule
    public IntentsTestRule<MainActivity> activityRule =
            new IntentsTestRule<>(MainActivity.class);

    PresenterTarjetaDescuento presenterTarjetaDescuento = PresenterTarjetaDescuento.getInstance();

    @BeforeClass
    public static void beforeClass() {
        ApplicationProvider.getApplicationContext().deleteDatabase("RoomDatabase");
    }

    @Before
    @MainThread
    public void setUp(){
        presenterTarjetaDescuento.anhadirNuevaTarjeta("Test-CAMPSA", "Tarjeta para test por cts/Litro", "CAMPSA", "cts/Litro", "0.349");
        presenterTarjetaDescuento.anhadirNuevaTarjeta("Test-AVIA", "Tarjeta para test de manera porcentual", "AVIA", "Porcentual", "0.5");
        Gasolinera g1 = new Gasolinera(1053,SANTANDER,SANTANDER, "Plaza Matias Montero", 1.4,1.349,"CAMPSA");
        Gasolinera g2 =  new Gasolinera(1000,SANTANDER,SANTANDER, "Av Valdecilla", 1.2,1.4,"AVIA");
    }

    @Test
    public void compruebaPrecios() {
        //UT404006-1.a
        
        // Hacemos clic en la primera gasolinera de la lista
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.listViewGasolineras)).atPosition(0).perform(click());
        // Hacemos clic en el botón de añadir favorito
        onView(withId(R.id.favButton)).perform(click());
        // Escribimos un comentario
        onView(withId(R.id.textBox_anhadeComentario)).perform(typeText("Comentario de texto"), closeSoftKeyboard());
        // Pulsamos guardar
        onView(withId(android.R.id.button1)).perform(click());
        //Volvemos a la main activity
        Espresso.pressBack();
        //Guardamos la gasolinera agregada a favoritos
        ListView listView = activityRule.getActivity().findViewById(R.id.listViewGasolineras);
        Gasolinera g1 = (Gasolinera) listView.getAdapter().getItem(0);
        Gasolinera g = new Gasolinera(g1.getIdeess(), g1.getLocalidad(), g1.getProvincia(), g1.getDireccion(), g1.getGasoleoA(), g1.getGasolina95(), g1.getRotulo());
        //Abrimos panel lateral y seleccionamos agrega nueva tarjeta de descuento
        onView(withId(R.id.activity_precio_gasolina_drawer)).perform(swipeRight());
        onView(withText(R.string.slider_menu_text_nueva_tarjeta_descuento)).perform(click());
        //Guardamos la nueva tarjeta de descuento
        onView(withId(R.id.nombreTarjeta)).perform(typeText("Tarjeta "+g1.getRotulo()), closeSoftKeyboard());
        onView(withId(R.id.spnMarcas)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is(g1.getRotulo()))).inRoot(isPlatformPopup()).perform(click());
        onView(withId(R.id.spnTipoDescuento)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("cts/Litro"))).inRoot(isPlatformPopup()).perform(click());
        onView(withId(R.id.descuento)).perform(typeText("0.189"),closeSoftKeyboard());
        onView(withId(R.id.comentarios)).perform(typeText("Tarjeta de descuento "+g1.getRotulo()+" con 0.189 CTS de descuento."),closeSoftKeyboard());
        onView(withText("GUARDAR")).perform(click());
        //Abrimos panel lateral y saleccionamos filtrar por favoritos
        onView(withId(R.id.activity_precio_gasolina_drawer)).perform(swipeRight());
        onView(withText(R.string.filtrar_favoritos)).perform(click());
        //Realizamos los calculos correctos sobre los datos obtenidos para comprobar el resultado
        List<Gasolinera> lista = new ArrayList<>();
        lista.add(g);
        lista =presenterTarjetaDescuento.actualizarListaDePrecios(lista);
        //comprobamos que la tarjeta de descuento ha sido aplicada
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.listFavGasolineras)).atPosition(0).onChildView(ViewMatchers.withId(R.id.textViewGasoleoAFav)).check(matches(withText(" "+lista.get(0).getGasoleoA()+"€")));
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.listFavGasolineras)).atPosition(0).onChildView(ViewMatchers.withId(R.id.textViewGasolina95Fav)).check(matches(withText(" "+lista.get(0).getGasolina95()+"€")));
    }

    public static ViewAction swipeRight() {
        return new GeneralSwipeAction(Swipe.FAST, GeneralLocation.CENTER_LEFT,
                GeneralLocation.CENTER_RIGHT, Press.FINGER);
    }
}
