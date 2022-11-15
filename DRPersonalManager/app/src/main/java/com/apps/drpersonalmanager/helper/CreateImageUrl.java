package com.apps.drpersonalmanager.helper;

import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.CHAVE_ST_IMAGES;
import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.CHAVE_ST_IMAGES_EXERCISES;
import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.IMAGE_NOT_FOUND;

import android.net.Uri;

import androidx.annotation.NonNull;

import com.apps.drpersonalmanager.config.ConfigFirebase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;

import java.util.concurrent.Executor;

public class CreateImageUrl {

    private StorageReference storageReference = ConfigFirebase.getStorageReference();
    private StorageReference imgExercicios = storageReference.child(CHAVE_ST_IMAGES)
            .child(CHAVE_ST_IMAGES_EXERCISES);
    private String idImgStorage;

    public String setImageUrl(String id){
        StorageReference imgRef = imgExercicios.child(id);
        imgRef.getDownloadUrl().addOnSuccessListener((Executor) this, new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                idImgStorage = uri.toString();
            }
        }).addOnFailureListener((Executor) this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                idImgStorage = IMAGE_NOT_FOUND;
            }
        });
        return idImgStorage;
    }

    public String setTeste(){
        return "NDA";
    }

}
