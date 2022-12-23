package com.apps.drpersonalmanager.ui.activity;

import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.CHAVE_DB_IDPERSONAL;
import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.CHAVE_DB_PERSONAL;
import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.CHAVE_ST_IMAGES;
import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.CHAVE_ST_PROFILE_PERSONAL;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.exifinterface.media.ExifInterface;

import com.apps.drpersonalmanager.R;
import com.apps.drpersonalmanager.config.ConfigFirebase;
import com.apps.drpersonalmanager.dao.PersonalDao;
import com.apps.drpersonalmanager.helper.Consent;
import com.apps.drpersonalmanager.helper.UsersFirebase;
import com.apps.drpersonalmanager.model.Personal;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyProfileActivity extends AppCompatActivity {

    private EditText editNome;
    private TextView textEmail;
    private ImageButton btnCamera, btnGaleria;
    private Button btnAtualizaDados;
    private CircleImageView imgProfilePersonal;
    private DatabaseReference reference = ConfigFirebase.getFirebaseDatabase();
    private DatabaseReference refPersonal;
    private StorageReference storageReference = ConfigFirebase.getStorage();
    private StorageReference storagePersonal;
    private ValueEventListener valueEventListenerPersonal;

    private String[] consent = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        setTitle("Editar Perfil");

        //Validar permissões
        Consent.validateConsent(consent, this, 1);

        //Recuperar Usuário Atual
        FirebaseUser user = UsersFirebase.getUserActual();


        imgProfilePersonal = findViewById(R.id.imgPerfilPersonal);
        editNome = findViewById(R.id.editNome);
        textEmail = findViewById(R.id.textViewEmail);
        btnCamera = findViewById(R.id.imgBtnCamera);
        btnGaleria = findViewById(R.id.imgBtnGaleria);
        btnAtualizaDados = findViewById(R.id.btnAtualizarDados);

        loadPersonalProfile();

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                arlCamera.launch(i);
            }
        });

        btnGaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                arlPicture.launch(i);
            }
        });

        btnAtualizaDados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nomePersonal = editNome.getText().toString();
                if(!nomePersonal.isEmpty()){
                    PersonalDao personalDao = new PersonalDao();
                    personalDao.salvarPerfilPersonal(nomePersonal);
                    Toast.makeText(MyProfileActivity.this, "Perfil atualizado com sucesso!", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(MyProfileActivity.this, "Campo nome não pode estar vazio!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    ActivityResultLauncher<Intent> arlCamera = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if(result.getResultCode() == Activity.RESULT_OK){
                    Intent imgInfo = result.getData();
                    Uri localImgSelected = imgInfo.getData();
                    Log.i("uri", String.valueOf(localImgSelected));
                    Bitmap imagem = null;
                    try{
                        imagem = (Bitmap) imgInfo.getExtras().get("data");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    //Rotacionar a imagem recebida
                    //Uri uriImgSelected = imgInfo.getData();
                    //String pathImgSelected = getImagePath(uriImgSelected);
                    //int anguloImg = loadAnguloImg(pathImgSelected);
                    //Bitmap imgRotate = rotateImg(imagem,anguloImg);

                    //Configurar a imagem recebida
                    if(imagem != null){
                        imgProfilePersonal.setImageBitmap(imagem);
                        //Recuperar dados da imagem para o Firebase
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        imagem.compress(Bitmap.CompressFormat.JPEG,70,baos);
                        byte[] dadosImagem = baos.toByteArray();

                        //Salvar imagem no Firebase
                        storagePersonal = storageReference.child(CHAVE_ST_IMAGES)
                                .child(CHAVE_ST_PROFILE_PERSONAL)
                                .child(CHAVE_DB_IDPERSONAL + ".jpg");

                        UploadTask uploadTask = storagePersonal.putBytes(dadosImagem); //upload da imagem
                        uploadTask.addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MyProfileActivity.this,
                                        "Falha ao salvar a imagem!", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Toast.makeText(MyProfileActivity.this,
                                        "Imagem salva com sucesso!", Toast.LENGTH_SHORT).show();
                                storagePersonal.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {

                                    }
                                });
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
                    Log.i("uri", String.valueOf(localImgSelected));
                    Bitmap imagem = null;
                    try{
                        imagem = MediaStore.Images.Media
                                .getBitmap(getContentResolver(),localImgSelected);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    //Rotacionar a imagem recebida
                    String pathImgSelected = getImagePath(localImgSelected);
                    int anguloImg = loadAnguloImg(pathImgSelected);
                    Bitmap imgRotate = rotateImg(imagem,anguloImg);

                    //Configurar a imagem recebida
                    if(imgRotate != null){
                        imgProfilePersonal.setImageBitmap(imgRotate);
                    }else{
                        Toast.makeText(this,"Imagem vazia", Toast.LENGTH_SHORT).show();
                    }

                }
            });

    private Bitmap rotateImg(Bitmap imagem, int anguloImg) {
        Matrix matrix= new Matrix();
        matrix.setRotate(anguloImg);
        Bitmap btmp = Bitmap.createBitmap(imagem,0,0,imagem.getWidth(),imagem.getHeight(),
                matrix,true);
        return btmp;
    }

    private int loadAnguloImg(String pathImgSelected) {
        ExifInterface exif = null;
        try {
            //data carrega imagem, mas está retornando zero sempre! Mas não dá erro.
            exif = new ExifInterface(pathImgSelected);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String orientacao = exif.getAttribute(ExifInterface.TAG_ORIENTATION);

        int codigoOrientacao = Integer.parseInt(orientacao);
        int i=0;
        switch (codigoOrientacao) {
            case ExifInterface.ORIENTATION_NORMAL:
                i = 0;
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                i = 90;
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                i = 180;
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                i = 270;
                break;
        }
        return i;
    }

    private String getImagePath(Uri uriImgSelected) {
        String[] campos = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uriImgSelected,campos,null,null,null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
        cursor.close();
        return path;
    }

    private void loadPersonalProfile() {
        refPersonal = reference.child(CHAVE_DB_PERSONAL).child(CHAVE_DB_IDPERSONAL);
        storagePersonal = storageReference.child(CHAVE_ST_IMAGES).child(CHAVE_ST_PROFILE_PERSONAL)
                .child(CHAVE_DB_IDPERSONAL + ".jpg");
        storagePersonal.getDownloadUrl().addOnSuccessListener(MyProfileActivity.this,
                new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(MyProfileActivity.this).load(uri).into(imgProfilePersonal);
            }
        }).addOnFailureListener(MyProfileActivity.this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("foto", "Erro ao carregar a imagem de perfil");
            }
        });
        valueEventListenerPersonal = refPersonal.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Personal personal = snapshot.getValue(Personal.class);
                editNome.setText(personal.getNomePersonal());
                textEmail.setText(personal.getEmailPersonal());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}