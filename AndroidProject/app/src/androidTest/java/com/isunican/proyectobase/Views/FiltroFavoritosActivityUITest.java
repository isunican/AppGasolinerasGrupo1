package com.isunican.proyectobase.Views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

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
        //Cogemos la marca
        String marca =activityRule.getActivity().textMarcaFavDialog.getText().toString();
        //Aplicamos el filtro
        onView(withText("OK")).perform(click());
        ListView newlistaFav = activityRule.getActivity().findViewById(R.id.listFavGasolineras);
        //Volvemos a contar los favoritos de la lista
        int newFavoritos = newlistaFav.getAdapter().getCount();
        //Comprobamos que el numero ha cambiado
        Assert.assertTrue(favoritos >= newFavoritos);
        //Comprobamos que la marca de la gasolinera se corresponde con el filtro
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.listFavGasolineras)).atPosition(0).perform(click());
        //Comprobamos que la marca de la gasolinera coincide con la seleccionada
        onView(withId(R.id.nombreGasolineraText)).check(matches(withText(marca)));

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
        //Cogemos la localidad seleccionada
        String localidad = activityRule.getActivity().textLocalidadFavDialog.getText().toString();
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
        //Comprobamos que la localidad de la gasolinera coincide con la seleccionada en el filtro
        Assert.assertEquals(g.getLocalidad(), localidad);

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

    @Test
    public void filtroFavoritosDosFiltrosTest(){

        //Caso 1: aplicamos los 2 filtros a la vez
        ListView listaFav = activityRule.getActivity().findViewById(R.id.listFavGasolineras);
        //Contamos la cantidad de favoritos antes de filtrar
        int favoritos = listaFav.getAdapter().getCount();
        //Vamos a la vista de filtrado
        onView(withId(R.id.action_favorite)).perform(click());
        //Cogemos la primera marca
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.listViewMarcasFavDialog)).atPosition(0).perform(click());
        //Cogemos la primera localidad
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.listViewLocalidadesFavDialog)).atPosition(0).perform(click());
        //Cogemos la localidad seleccionada
        String localidad = activityRule.getActivity().textLocalidadFavDialog.getText().toString();
        //Cogemos la marca
        String marca =activityRule.getActivity().textMarcaFavDialog.getText().toString();
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
        //Comprobamos que la localidad de la gasolinera coincide con la seleccionada en el filtro
        Assert.assertEquals(g.getLocalidad(), localidad);
        //Comprobamos que la marca de la gasolinera coincide con la seleccionada
        onView(withId(R.id.nombreGasolineraText)).check(matches(withText(marca)));

        Espresso.pressBack();

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
