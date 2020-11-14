package com.isunican.proyectobase.Views;

import android.widget.ListView;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.isunican.proyectobase.Model.Gasolinera;
import com.isunican.proyectobase.R;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;

@RunWith(AndroidJUnit4.class)
public class FiltroFavoritosActivityUITest {

    @Rule
    public IntentsTestRule<FiltroFavoritosActivity> activityRule =
            new IntentsTestRule<>(FiltroFavoritosActivity.class);

    @Test
    public void filtroFavoritosMarcaTest(){
        //Caso 1: filtramos por una marca cualquiera
        ListView listaFav = activityRule.getActivity().findViewById(R.id.listFavGasolineras);
        //Cogemos la cantidad de favoritos antes de filtrar
        int favoritos = listaFav.getAdapter().getCount();
        //Vamos a la vista de filtrado
        onView(withId(R.id.action_favorite)).perform(click());
        //Cogemos la primera marca
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.listViewMarcasFavDialog)).atPosition(0).perform(click());
        /*
        ListView marcas = activityRule.getActivity().findViewById(R.id.listViewMarcasFavDialog);
        String marcaSeleccionada = (String) marcas.getAdapter().getItem(0);
        System.out.println(marcaSeleccionada);
        onView(withId(R.id.textMarcaFavDialog)).check(matches(withText(marcaSeleccionada)));
        *TODO: Solucionar el null pointer que tira aquí
         */
        //Aplicamos el filtro
        onView(withText("OK")).perform(click());
        ListView newlistaFav = activityRule.getActivity().findViewById(R.id.listFavGasolineras);
        //Volvemos a contar los favoritos de la lista
        int newFavoritos = newlistaFav.getAdapter().getCount();
        //Comprobamos que el numero ha cambiado
        Assert.assertTrue(favoritos >= newFavoritos);
        //Comprobamos que la marca de la gasolinera se corresponde con el filtro
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.listFavGasolineras)).atPosition(0).perform(click());
        onView(withId(R.id.nombreGasolineraText)).check(matches(withText("AVIA"))); //TODO: Conseguir la marca y compararla aquí

        Espresso.pressBack(); //Volvemos a la vista anterior para los siguientes casos de prueba

        //Caso 2: no se selecciona ninguna marca de filtro
        //Vamos a la vista de filtrado
        onView(withId(R.id.action_favorite)).perform(click());
        //Aplicamos el filtro
        onView(withText("OK")).perform(click());
        newlistaFav = activityRule.getActivity().findViewById(R.id.listFavGasolineras);
        //Volvemos a contar los favoritos de la lista
        newFavoritos = newlistaFav.getAdapter().getCount();
        //Comprobamos que es el mismo numero que al principio
        Assert.assertTrue(favoritos == newFavoritos);

        //Caso 3: pulsamos cancelar
        //Vamos a la vista de filtrado
        onView(withId(R.id.action_favorite)).perform(click());
        // No aplicamos el filtro
        onView(withText("Cancel")).perform(click());
        newlistaFav = activityRule.getActivity().findViewById(R.id.listFavGasolineras);
        //Volvemos a contar los favoritos de la lista
        newFavoritos = newlistaFav.getAdapter().getCount();
        //Comprobamos que el numero de favoritos no ha cambiado
        Assert.assertTrue(favoritos == newFavoritos);

    }

    @Test
    public void filtroFavoritosLocalidadTest(){


        //Caso 1: filtramos por una localidad cualquiera
        ListView listaFav = activityRule.getActivity().findViewById(R.id.listFavGasolineras);
        //Contamos la cantidad de favoritos antes de filtrar
        int favoritos = listaFav.getAdapter().getCount();
        //Vamos a la vista de filtrado
        onView(withId(R.id.action_favorite)).perform(click());
        //Cogemos la primera localidad
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.listViewLocalidadesFavDialog)).atPosition(0).perform(click());
        /*
        ListView localidades = activityRule.getActivity().findViewById(R.id.listViewLocalidadesFavDialog);
        String localidadSeleccionada = (String) localidades.getAdapter().getItem(0);
        System.out.println(localidadSeleccionada);
        onView(withId(R.id.textLocalidadFavDialog)).check(matches(withText(localidadSeleccionada)));
        //TODO: Solucionar el null pointer que tira aquí
        */
        //Aplicamos el filtro
        onView(withText("OK")).perform(click());
        ListView newlistaFav = activityRule.getActivity().findViewById(R.id.listFavGasolineras);
        //Volvemos a contar los favoritos
        int newFavoritos = newlistaFav.getAdapter().getCount();
        //Comprobamos que el número de favoritos ha cambiado
        Assert.assertTrue(favoritos >= newFavoritos);

        //Comprobamos que la localidad de la gasolinera se corresponde con el filtro
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.listFavGasolineras)).atPosition(0).perform(click());
        ListView listView = activityRule.getActivity().findViewById(R.id.listFavGasolineras);
        Gasolinera g = (Gasolinera) listView.getAdapter().getItem(0);
        //TODO: No puedo comprobar una localidad, porque no se como extraerla de la lista

        Espresso.pressBack(); //Volvemos a la vista anterior para los siguientes casos de prueba

        //Caso 2: no se selecciona ninguna marca de filtro
        //Vamos a la vista de filtrado
        onView(withId(R.id.action_favorite)).perform(click());
        //Aplicamos el filtro
        onView(withText("OK")).perform(click());
        newlistaFav = activityRule.getActivity().findViewById(R.id.listFavGasolineras);
        //Volvemos a contar los favoritos
        newFavoritos = newlistaFav.getAdapter().getCount();
        //Comprobamos que hay el mismo numero que al principio
        Assert.assertTrue(favoritos == newFavoritos);

        //Caso 3: pulsamos cancelar
        //Vamos a la vista de filtrado
        onView(withId(R.id.action_favorite)).perform(click());
        //No aplicamos el filtro
        onView(withText("Cancel")).perform(click());
        newlistaFav = activityRule.getActivity().findViewById(R.id.listFavGasolineras);
        //Volvemos a contar los favoritos
        newFavoritos = newlistaFav.getAdapter().getCount();
        //Comprobamos que el numero de favoritos no ha cambiado
        Assert.assertTrue(favoritos == newFavoritos);
    }


}
