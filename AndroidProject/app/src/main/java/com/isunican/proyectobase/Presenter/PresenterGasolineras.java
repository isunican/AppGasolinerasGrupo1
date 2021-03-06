package com.isunican.proyectobase.Presenter;

import android.util.Log;

import com.isunican.proyectobase.DAO.GasolineraDAO;
import com.isunican.proyectobase.Model.*;
import com.isunican.proyectobase.Utilities.ParserJSONGasolineras;
import com.isunican.proyectobase.Utilities.RemoteFetch;

import java.io.BufferedInputStream;
import java.util.ArrayList;
import java.util.List;

/*
------------------------------------------------------------------
    Clase presenter con la logica de gasolineras
    Mantiene un objeto ListaGasolineras que es el que mantendrá
    los datos de las gasolineras cargadas en nuestra aplicación
    Incluye métodos para gestionar la lista de gasolineras y
    cargar datos en ella.
------------------------------------------------------------------
*/
public class PresenterGasolineras {

    private List<Gasolinera> gasolineras;

    //URLs para obtener datos de las gasolineras
    //https://sedeaplicaciones.minetur.gob.es/ServiciosRESTCarburantes/PreciosCarburantes/help
    public static final String URL_GASOLINERAS_SPAIN="https://sedeaplicaciones.minetur.gob.es/ServiciosRESTCarburantes/PreciosCarburantes/EstacionesTerrestres/";
    public static final String URL_GASOLINERAS_CANTABRIA="https://sedeaplicaciones.minetur.gob.es/ServiciosRESTCarburantes/PreciosCarburantes/EstacionesTerrestres/FiltroCCAA/06";
    public static final String URL_GASOLINERAS_SANTANDER="https://sedeaplicaciones.minetur.gob.es/ServiciosRESTCarburantes/PreciosCarburantes/EstacionesTerrestres/FiltroMunicipio/5819";
    public static final String SANTANDER="Santander";

    /**
     * Constructor, getters y setters
     */
    public PresenterGasolineras(){
        gasolineras = new ArrayList<>();
    }

    public List<Gasolinera> getGasolineras(){
        return gasolineras;
    }


    public void setGasolineras(List<Gasolinera> l) {
        this.gasolineras = l;
    }

    public Gasolinera getGasolineraPorIdess(int idess, GasolineraDAO gasolineraDAO){
        List<Gasolinera> lista = gasolineraDAO.findByIdEESS(idess);
        if (lista.isEmpty())
            return null;
        return lista.get(0);
    }

    public int anhadeGasolinera(Gasolinera gasolinera, GasolineraDAO gasolineraDAO){
        long id = gasolineraDAO.insertOne(gasolinera);
        return gasolineraDAO.getIdFromRowId(id);
    }

    /**
     * cargaDatosGasolineras
     *
     * Carga los datos de las gasolineras en la lista de gasolineras de la clase.
     * Para ello llama a métodos de carga de datos internos de la clase ListaGasolineras.
     * En este caso realiza una carga de datos remotos dada una URL
     *
     * Habría que mejorar el método para que permita pasar un parámetro
     * con los datos a cargar (id de la ciudad, comunidad autónoma, etc.)
     *
     * @param
     * @return boolean Devuelve true si se han podido cargar los datos
     */
    public boolean cargaDatosGasolineras() {
        return cargaDatosRemotos(URL_GASOLINERAS_CANTABRIA);
    }

    /**
     * cargaDatosDummy
     *
     * Carga en la lista de gasolineras varias gasolineras definidas a "mano"
     * para hacer pruebas de funcionamiento
     *
     * @param
     * @return boolean
     */
    public boolean cargaDatosDummy(){
        this.gasolineras.add(new Gasolinera(1000,SANTANDER,SANTANDER, "Av Valdecilla", 1.299,1.359,"AVIA"));
        this.gasolineras.add(new Gasolinera(1053,SANTANDER,SANTANDER, "Plaza Matias Montero", 0,1.349,"CAMPSA"));
        this.gasolineras.add(new Gasolinera(420,SANTANDER,SANTANDER, "Area Arrabal Puerto de Raos", 0,1.279,"E.E.S.S. MAS, S.L."));
        this.gasolineras.add(new Gasolinera(9564,SANTANDER,SANTANDER, "Av Parayas", 1.189,0,"EASYGAS"));
        this.gasolineras.add(new Gasolinera(1025,SANTANDER,SANTANDER, "Calle el Empalme", 1.259,0,"CARREFOUR"));
        return true;
    }

    /**
     * cargaDatosLocales
     *
     * A partir de la dirección de un fichero JSON pasado como parámetro:
     * Parsea la información para obtener una lista de gasolineras.
     * Finalmente, dicha lista queda almacenada en la clase.
     *
     * @param String Nombre del fichero
     * @return boolean Devuelve true si se han podido cargar los datos
     */
    public boolean cargaDatosLocales(String fichero){
        return(fichero != null);
    }

    /**
     * cargaDatosRemotos
     *
     * A partir de la dirección pasada como parámetro:
     * Utiliza RemoteFetch para cargar el fichero JSON ubicado en dicha URL
     * en un stream de datos.
     * Luego utiliza ParserJSONGasolineras para parsear dicho stream
     * y extraer una lista de gasolineras.
     * Finalmente, dicha lista queda almacenada en la clase.
     *
     * @param String Dirección URL del JSON con los datos
     * @return boolean Devuelve true si se han podido cargar los datos
     */
    public boolean cargaDatosRemotos(String direccion) {
        try {
            BufferedInputStream buffer = RemoteFetch.cargaBufferDesdeURL(direccion);
            gasolineras = ParserJSONGasolineras.parseaArrayGasolineras(buffer);
            Log.d("ENTRA", "Obten gasolineras:" + gasolineras.size());
            return true;
        } catch (Exception e) {
            Log.e("ERROR", "Error en la obtención de gasolineras: " + e.getMessage());
            return false;
        }
    }


    /**
     *
     * Filtro de gasolineras por tipo de combustible, filtra las gasolineras mostradas en funcion de un tipo de gasolina (tipo).
     * Hace uso de hasTipoGasolina() para determinar los tipos de gasolina de la gasolinera
     *
     * @param tipo (String tipo de gasolina buscada)
     * @param lista (List<Gasolinera>lista de gasolineras a filtrar)
     * @return lista de gasolineras filtradas
     * Autor: Jaime López-Agudo Higuera
     */
    public List<Gasolinera> filtraGasolinerasTipoCombustible(String tipo,List<Gasolinera>lista){
        if(lista==null){
            throw new NullPointerException();
        }
        List<Gasolinera>gasolinerasFiltradas=new ArrayList<>();
        for(Gasolinera g : lista) {

            if (g.tiposGasolina().contains(tipo)) {
                gasolinerasFiltradas.add(g);
            }
        }
        return gasolinerasFiltradas;
    }

    /**
     * Método encargado de aplicar un filtro por precioMaximo a una lista (lista) y mostrar solo las gasolineras cuyo tipo
     * de combustible (tipo) tenga un precio menor que un precio determinado (precioMax).
     * @param tipo (String tipo de gasolina)
     * @param lista (List<Gasolinera> lista a filtrar)
     * @param precioMax (double precio máximo por el que filtrar)
     * @return Lista de gasolineras filtradas
     * Autor: Jaime López-Agudo Higuera
     */
    public List<Gasolinera> filtrarGasolineraPorPrecioMaximo(String tipo, List<Gasolinera>lista, double precioMax){
        if(lista==null) {
            throw new NullPointerException();
        }
        ArrayList<Gasolinera>gasolinerasFiltradas=new ArrayList<>();

            if(tipo.equals("Gasolina95")) {
                for (Gasolinera g : lista) {
                    if (g.tiposGasolina().contains("Gasolina95") && g.getGasolina95() <= precioMax) {
                        gasolinerasFiltradas.add(g);
                    }
                }
            }
            if(tipo.equals("Diesel")) {
                for (Gasolinera g : lista) {
                    if (g.tiposGasolina().contains("Diesel") && g.getGasoleoA() <= precioMax) {
                        gasolinerasFiltradas.add(g);
                    }

                }
            }

        return gasolinerasFiltradas;
    }



}
