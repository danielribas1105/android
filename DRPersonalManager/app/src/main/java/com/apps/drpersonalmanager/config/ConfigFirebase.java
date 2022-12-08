package com.apps.drpersonalmanager.config;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ConfigFirebase {

    private static FirebaseAuth auth;
    private static DatabaseReference reference;
    private static StorageReference storage;

    //Retorna a instância do Firebase Storage
    public static StorageReference getStorage(){
        if(storage == null){
            storage = FirebaseStorage.getInstance().getReference();
        }
        return storage;
    }

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
