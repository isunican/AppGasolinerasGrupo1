package com.isunican.proyectobase.Views;

import androidx.test.annotation.UiThreadTest;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.isunican.proyectobase.Model.Gasolinera;
import com.isunican.proyectobase.R;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.hasTextColor;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;

import static com.isunican.proyectobase.Presenter.PresenterGasolineras.SANTANDER;

/*
 * @author: Adrián Celis
 * Clase de prueba de interfaz de modificar una gasolinera favorita
 */
@RunWith(AndroidJUnit4.class)
public class ModificaGasolineraFavoritaUITest {

    private static final String COMENTARIOFUERALIMITE = "iiiiiiiiiiiiiiiiiiii" +
            "iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii" +
            "iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii" +
            "iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii" +
            "iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii";

    @Rule
    public IntentsTestRule<FiltroFavoritosActivity> activityRule =
            new IntentsTestRule<>(FiltroFavoritosActivity.class);

    @Before
    @UiThreadTest
    public void setUp(){
        ArrayList<Gasolinera> listaOriginal = new ArrayList<Gasolinera>();
        listaOriginal.add(new Gasolinera(1000,SANTANDER,SANTANDER, "Av Valdecilla", 1.299,1.359,"AVIA"));
        listaOriginal.add(new Gasolinera(1053,SANTANDER,SANTANDER, "Plaza Matias Montero", 0,1.349,"CAMPSA"));
        listaOriginal.add(new Gasolinera(420,SANTANDER,SANTANDER, "Area Arrabal Puerto de Raos", 0,1.279,"E.E.S.S. MAS, S.L."));
        listaOriginal.add(new Gasolinera(9564,SANTANDER,SANTANDER, "Av Parayas", 1.189,0,"EASYGAS"));
        listaOriginal.add(new Gasolinera(1025,SANTANDER,SANTANDER, "Calle el Empalme", 1.259,0,"CARREFOUR"));
        activityRule.getActivity().listaActual =listaOriginal;
        activityRule.getActivity().adapterFavoritas.addAll(listaOriginal);
        activityRule.getActivity().adapterFavoritas.notifyDataSetChanged();
    }

    /**
     * Esto es para anhadir gasolineras al principio
     */
    private void anhadeGasolineras(){
        // Guardamos una gasolinera en favoritos
        // Hacemos clic en la primera gasolinera de la lista
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.listFavGasolineras)).atPosition(0).perform(click());
        // Hacemos clic en el botón de añadir favorito
        onView(withId(R.id.favButton)).perform(click());
        // Escribimos un comentario
        onView(withId(R.id.textBox_anhadeComentario)).perform(typeText("Comentario de texto"), closeSoftKeyboard());
        // Pulsamos guardar
        onView(withId(android.R.id.button1)).perform(click());

        Espresso.pressBack();

        // Guardamos otra gasolinera en favoritos
        // Hacemos clic en la primera gasolinera de la lista
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.listFavGasolineras)).atPosition(1).perform(click());
        // Hacemos clic en el botón de añadir favorito
        onView(withId(R.id.favButton)).perform(click());
        // Escribimos un comentario
        onView(withId(R.id.textBox_anhadeComentario)).perform(typeText("Comentario de texto"), closeSoftKeyboard());
        // Pulsamos guardar
        onView(withId(android.R.id.button1)).perform(click());

        Espresso.pressBack();
    }
    @Test
    public void modificaFavorito() {
        anhadeGasolineras(); // añade dos gasolineras favoritas a la base de datos, para modificar su comentario posteriormente

        // ID1: Cancelamos al modificar una gasolinera favorita
        // Hacemos clic en la primera gasolinera de la lista en el boton de modificar
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.listFavGasolineras)).atPosition(0).
                onChildView(withId(R.id.buttonModifica)).perform(click());
        // Comprobamos que en la caja de texto aparece el comentario antiguo
        onView(withId(R.id.textBox_modificaComentario)).check(matches(withText("Comentario de texto")));
        // Pulsamos cancelar
        onView(withId(android.R.id.button2)).perform(click());
        // Comprobamos que el comentario no ha cambiado
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.listFavGasolineras)).atPosition(0).perform(click());
        // Comprobamos que el comentario sale correctamente en la gasolinera
        onView(withId(R.id.comentarioText)).check(matches(withText("Comentario:\nComentario de texto")));

        Espresso.pressBack();

        // ID2: Guardamos al modificar una gasolinera favorita con comentario fuera de 240 caracteres
        // Hacemos clic en la primera gasolinera de la lista en el boton de modificar
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.listFavGasolineras)).atPosition(0).
                onChildView(withId(R.id.buttonModifica)).perform(click());
        // Comprobamos que en la caja de texto aparece el comentario antiguo
        onView(withId(R.id.textBox_modificaComentario)).check(matches(withText("Comentario de texto")));
        // Escribimos un comentario
        onView(withId(R.id.textBox_modificaComentario)).perform(clearText(), typeText(COMENTARIOFUERALIMITE), closeSoftKeyboard());
        // Comprobamos que el contador de caracteres es de 241
        onView(withId(R.id.textNumCaracteresActual)).check(matches(withText("241")));
        // Comprobamos que el texto a cambiado a color rojo
        onView(withId(R.id.textNumCaracteresActual)).check(matches(hasTextColor(R.color.rojo)));
        // Pulsamos guardar
        onView(withId(android.R.id.button1)).perform(click());
        // Obtenemos mensaje de error
        onView(withId(R.id.textBox_modificaComentario)).check(matches(hasErrorText("El comentario debe ser menor de 240 carácteres")));

        Espresso.pressBack();

        // ID3: Guardamos al modificar una gasolinera favorita con comentario vacio
        // Hacemos clic en la primera gasolinera de la lista en el boton de modificar
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.listFavGasolineras)).atPosition(0).
                onChildView(withId(R.id.buttonModifica)).perform(click());
        // Comprobamos que en la caja de texto aparece el comentario antiguo
        onView(withId(R.id.textBox_modificaComentario)).check(matches(withText("Comentario de texto")));
        // Escribimos un comentario
        onView(withId(R.id.textBox_modificaComentario)).perform(clearText(), closeSoftKeyboard());
        // Pulsamos guardar
        onView(withId(android.R.id.button1)).perform(click());
        // Comprobamos que el comentario no ha cambiado
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.listFavGasolineras)).atPosition(0).perform(click());
        // Comprobamos que el comentario sale correctamente en la gasolinera
        onView(withId(R.id.comentarioText)).check(matches(withText("Comentario:\n")));

        Espresso.pressBack();

        // ID4: Guardamos al modificar una gasolinera favorita con comentario menor de 240 caracteres
        // Hacemos clic en la primera gasolinera de la lista en el boton de modificar
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.listFavGasolineras)).atPosition(1).
                onChildView(withId(R.id.buttonModifica)).perform(click());
        // Comprobamos que en la caja de texto aparece el comentario antiguo
        onView(withId(R.id.textBox_modificaComentario)).check(matches(withText("Comentario de texto")));
        // Comprobamos que el contador de caracteres es de 19
        onView(withId(R.id.textNumCaracteresActual)).check(matches(withText("19")));
        // Escribimos un comentario
        onView(withId(R.id.textBox_modificaComentario)).perform(clearText(), typeText("Nuevo comentario de texto"), closeSoftKeyboard());
        // Pulsamos guardar
        onView(withId(android.R.id.button1)).perform(click());
        // Comprobamos que el comentario no ha cambiado
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.listFavGasolineras)).atPosition(1).perform(click());
        // Comprobamos que el comentario sale correctamente en la gasolinera
        onView(withId(R.id.comentarioText)).check(matches(withText("Comentario:\nNuevo comentario de texto")));

        Espresso.pressBack();

    }

}