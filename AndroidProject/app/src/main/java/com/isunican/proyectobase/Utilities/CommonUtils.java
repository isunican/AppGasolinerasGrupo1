package com.isunican.proyectobase.Utilities;

import java.text.Collator;
import java.util.List;

/**
 * -----------------------------------------------------
 * Clase dedica a almacenar metodos comunes que pueden
 * ser usados de forma comun en cualquier lugar de la
 * app.
 * -----------------------------------------------------
 *
 * @author Adrian Celis
 * @version 0.0.1
 */
public class CommonUtils {

    private CommonUtils(){}
    /**
     * Reorganiza una lista de Strings por orden alfabetico en orden ascendente
     * @return lista de String ordenada
     */
    public static List<String> sortStringList(List<String> list){
        java.util.Collections.sort(list, Collator.getInstance());
        return list;
    }
}
