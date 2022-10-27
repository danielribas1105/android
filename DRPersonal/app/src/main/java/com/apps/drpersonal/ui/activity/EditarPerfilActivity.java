package com.apps.drpersonal.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaFeature;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;

import com.apps.drpersonal.R;

public class EditarPerfilActivity extends AppCompatActivity {

    private ImageButton imgCamera, imgGallery;
    private static final int SELECT_CAMERA = 100;
    private static final int SELECT_GALLERY = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);

        getSupportActionBar().setTitle("Editar Perfil");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imgCamera = findViewById(R.id.imgBtnCamera);
        imgGallery = findViewById(R.id.imgBtnGaleria);

        imgCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(i.resolveActivity(getPackageManager()) != null){
                    startActivityForResult(i,SELECT_CAMERA);
                }
            }
        });

        imgGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                if(i.resolveActivity(getPackageManager()) != null){
                    startActivityForResult(i,SELECT_GALLERY);
                }
            }
        });
    }
}