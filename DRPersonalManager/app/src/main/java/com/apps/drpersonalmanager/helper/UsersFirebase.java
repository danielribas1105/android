package com.apps.drpersonalmanager.helper;

import com.apps.drpersonalmanager.config.ConfigFirebase;
import com.google.firebase.auth.FirebaseAuth;
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
}
