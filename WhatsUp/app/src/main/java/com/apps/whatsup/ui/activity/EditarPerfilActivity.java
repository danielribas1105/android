package com.apps.whatsup.ui.activity;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.apps.whatsup.R;
import com.apps.whatsup.helper.Consent;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditarPerfilActivity extends AppCompatActivity {

    private ImageButton imgCamera, imgGallery;
    private CircleImageView circleImageView;
    private static final int SELECT_CAMERA = 100;
    private static final int SELECT_GALLERY = 200;

    private String[] consent = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);

        //Validar permissões
        Consent.validateConsent(consent, this, 1);

        Toolbar toolbar = findViewById(R.id.toolbarHome);
        toolbar.setTitle("Editar");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        circleImageView = findViewById(R.id.imgCirclePerfil);
        imgCamera = findViewById(R.id.imgBtnCamera);
        imgGallery = findViewById(R.id.imgBtnGaleria);

        /*
        ActivityResultLauncher<String> register =
                registerForActivityResult(new ActivityResultContracts.GetContent(),
                        new ActivityResultCallback<Uri>() {
                            @Override
                            public void onActivityResult(Uri result) {

                            }
                        });

         */

        imgCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(i.resolveActivity(getPackageManager()) != null){
                    //startActivityForResult(i,SELECT_CAMERA);
                    //register.launch(String.valueOf(i));
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            Bitmap imagem = null;

            try{
                switch (requestCode){
                    case SELECT_CAMERA:
                        imagem = (Bitmap) data.getExtras().get("dados");
                        break;
                    case SELECT_GALLERY:
                        Uri localImgSelected = data.getData();
                        imagem = MediaStore.Images.Media
                                .getBitmap(getContentResolver(),localImgSelected);
                        break;
                }
                //Configurar a imagem recebida
                if(imagem != null){
                    circleImageView.setImageBitmap(imagem);
                }else{
                    Toast.makeText(this,"Imagem vazia", Toast.LENGTH_SHORT).show();
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }else{
            //mensagem de erro
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        for(int consentResult: grantResults){
            if(consentResult == PackageManager.PERMISSION_DENIED){
                alertValidateConsent();
            }
        }
    }

    public void alertValidateConsent(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permissões Negadas");
        builder.setMessage("Para utilizar o App é necessário aceitar as permissões!");
        builder.setCancelable(false);
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}