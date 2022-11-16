package com.apps.drpersonalmanager.helper;

import java.text.Normalizer;

public class StringCustom {

    public static String removerAcentos(String str) {
        return Normalizer.normalize(str, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "");
    }
}
