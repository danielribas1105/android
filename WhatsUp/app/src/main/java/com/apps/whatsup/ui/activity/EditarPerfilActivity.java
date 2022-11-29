package com.apps.whatsup.ui.activity;

import static com.apps.whatsup.ui.activity.ConstantesActivities.CHAVE_ST_USERS;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.apps.whatsup.R;
import com.apps.whatsup.config.ConfigFirebase;
import com.apps.whatsup.helper.Consent;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditarPerfilActivity extends AppCompatActivity {

    private ImageButton imgCamera, imgGallery;
    private CircleImageView circleImageView;
    private String idUser;
    private static final int SELECT_CAMERA = 100;
    private static final int SELECT_GALLERY = 200;
    private StorageReference storageReference = ConfigFirebase.getStorageReference();
    private StorageReference storageImg;

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

        idUser = (String) getIntent().getSerializableExtra(CHAVE_ST_USERS);

        imgCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                arlCamera.launch(i);
                /*if(i.resolveActivity(getPackageManager()) != null){
                    //startActivityForResult(i,SELECT_CAMERA);
                } */

            }
        });

        imgGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                arlPicture.launch(i);
                //if(i.resolveActivity(getPackageManager()) != null){
               //     startActivityForResult(i,SELECT_GALLERY);
               // }

            }
        });

    }

    ActivityResultLauncher<Intent> arlCamera = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if(result.getResultCode() == Activity.RESULT_OK){
                    Intent imgInfo = result.getData();
                    Bitmap imagem = null;
                    try{
                        imagem = (Bitmap) imgInfo.getExtras().get("data");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //Configurar a imagem recebida
                    if(imagem != null){
                        circleImageView.setImageBitmap(imagem);
                        //Recuperar dados da imagem para o Firebase
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        imagem.compress(Bitmap.CompressFormat.JPEG,70,baos);
                        byte[] dadosImagem = baos.toByteArray();

                        //Salvar imagem no Firebase
                        storageImg = storageReference.child("images")
                                .child("profiles")
                                .child(idUser + ".jpg");

                        UploadTask uploadTask = storageImg.putBytes(dadosImagem);
                        uploadTask.addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(EditarPerfilActivity.this, "Falha ao salvar a imagem!", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Toast.makeText(EditarPerfilActivity.this, "Imagem salva com sucesso!", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }else{
                        Toast.makeText(this,"Imagem vazia", Toast.LENGTH_SHORT).show();
                    }

                }
            });

    ActivityResultLauncher<Intent> arlPicture = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if(result.getResultCode() == Activity.RESULT_OK){
                    Intent imgInfo = result.getData();
                    Uri localImgSelected = imgInfo.getData();
                    Bitmap imagem = null;
                    try{
                        imagem = MediaStore.Images.Media
                                .getBitmap(getContentResolver(),localImgSelected);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //Configurar a imagem recebida
                    if(imagem != null){
                        circleImageView.setImageBitmap(imagem);
                    }else{
                        Toast.makeText(this,"Imagem vazia", Toast.LENGTH_SHORT).show();
                    }

                }
            });


    /*
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

     */

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