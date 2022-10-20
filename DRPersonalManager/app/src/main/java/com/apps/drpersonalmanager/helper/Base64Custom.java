package com.apps.drpersonalmanager.helper;

import android.util.Base64;

import java.nio.charset.StandardCharsets;

public class Base64Custom {

    public static String codeToBase64 (String text){
        return Base64.encodeToString(text.getBytes(StandardCharsets.UTF_8),Base64.DEFAULT)
                .replaceAll("(\\n|\\r)","");
    }

    public static String decodeToBase64 (String textCode){
        return new String(Base64.decode(textCode, Base64.DEFAULT));
    }

}
