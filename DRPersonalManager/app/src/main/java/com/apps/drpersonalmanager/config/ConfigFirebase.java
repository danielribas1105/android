package com.apps.drpersonalmanager.config;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ConfigFirebase {

    private static FirebaseAuth auth;
    private static DatabaseReference reference;

    //Retorna a instância do Firebase Database
    public static DatabaseReference getFirebaseDatabase(){
        if(reference == null){
            reference = FirebaseDatabase.getInstance().getReference();
        }
        return reference;
    }

    //Retorna a instância do Firebase Auth
    public static FirebaseAuth getFirebaseAutenticacao(){
        if(auth == null){
            auth = FirebaseAuth.getInstance();
        }
        return auth;
    }

}
