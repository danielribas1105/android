package com.apps.drpersonal.helper;

import com.apps.drpersonal.config.ConfigFirebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;

public class UsersFirebase {

    private static FirebaseAuth auth = ConfigFirebase.getFirebaseAutenticacao();
    private DatabaseReference databaseReference = ConfigFirebase.getFirebaseDatabase();
    private StorageReference storageReference = ConfigFirebase.getStorageReference();

    public static String getIdUserAuth(){
        String idUser = Base64Custom.codeToBase64(auth.getCurrentUser().getEmail());
        return idUser;
    }

    public static FirebaseUser getUserActual(){ return auth.getCurrentUser(); }

    public static boolean changePassWord(String pass){
        FirebaseUser user = getUserActual();
        user.updatePassword(pass);
        return true;
    }

    public static boolean changeEmail(String email){
        FirebaseUser user = getUserActual();
        user.updateEmail(email);
        return true;
    }
}
