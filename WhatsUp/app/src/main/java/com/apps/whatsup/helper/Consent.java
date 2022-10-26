package com.apps.whatsup.helper;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class Consent {

    public static boolean validateConsent(String[] consents, Activity activity){

        if(Build.VERSION.SDK_INT >= 23){
            List<String> listConsent = new ArrayList<>();

            /*Percorrer as permissões necessárias,
            * verificando uma a uma se já estão liberadas*/
            for(String consent: consents){
                Boolean checkConsent = ContextCompat.checkSelfPermission(activity,consent) == PackageManager.PERMISSION_GRANTED;
                if(!checkConsent) listConsent.add(consent);
            }

        }

        return true;
    }
}
