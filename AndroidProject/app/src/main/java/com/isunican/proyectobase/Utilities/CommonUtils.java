package com.isunican.proyectobase.Utilities;

import java.text.Collator;
import java.util.List;

public class CommonUtils {
    public static List<String> sortStringList(List<String> list){
        java.util.Collections.sort(list, Collator.getInstance());
        return list;
    }
}
