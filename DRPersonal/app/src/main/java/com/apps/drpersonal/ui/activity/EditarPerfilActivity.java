package com.apps.drpersonal.ui.activity;

import static com.apps.drpersonal.ui.activity.ConstantesActivities.CHAVE_DB_ALUNOS;
import static com.apps.drpersonal.ui.activity.ConstantesActivities.CHAVE_DB_IDPERSONAL;
import static com.apps.drpersonal.ui.activity.ConstantesActivities.CHAVE_ST_IMAGES;
import static com.apps.drpersonal.ui.activity.ConstantesActivities.CHAVE_ST_PROFILE_ALUNOS;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.apps.drpersonal.R;
import com.apps.drpersonal.config.ConfigFirebase;
import com.apps.drpersonal.helper.Base64Custom;
import com.apps.drpersonal.helper.Consent;
import com.apps.drpersonal.helper.UsersFirebase;
import com.apps.drpersonal.model.Aluno;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditarPerfilActivity extends AppCompatActivity {

    private ImageButton imgCamera, imgGallery;
    private CircleImageView campoFoto;
    private EditText campoNome, campoAcademia, campoNiver;
    private TextView campoEmail;
    private Aluno alunoNew, imgPerfilAluno;
    private String idAluno, idImgPerfil;
    private FirebaseAuth auth = ConfigFirebase.getFirebaseAutenticacao();
    private DatabaseReference dataProfile;
    private DatabaseReference reference = ConfigFirebase.getFirebaseDatabase();
    private ValueEventListener valueEventListenerProfile;
    private StorageReference storageReference = ConfigFirebase.getStorageReference();
    private StorageReference fotoPerfil;
    //private static final int SELECT_CAMERA = 100;
    //private static final int SELECT_GALLERY = 200;

    private String[] consent = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);
        getSupportActionBar().setTitle("Editar Perfil");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Validar permissões
        Consent.validateConsent(consent, this, 1);

        campoFoto = findViewById(R.id.profile_image);
        imgCamera = findViewById(R.id.imgBtnCamera);
        imgGallery = findViewById(R.id.imgBtnGaleria);
        campoNome = findViewById(R.id.editTextNome);
        campoAcademia = findViewById(R.id.editTextAcademia);
        campoNiver = findViewById(R.id.editTextNiver);
        campoEmail = findViewById(R.id.textViewEmail);
        idAluno = UsersFirebase.getIdUserAuth();
        loadProfile();

        imgCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                arlCamera.launch(i);
            }
        });

        imgGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                arlPicture.launch(i);
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
                        campoFoto.setImageBitmap(imagem);
                        //Recuperar dados da imagem para o Firebase
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        imagem.compress(Bitmap.CompressFormat.JPEG,70,baos);
                        byte[] dadosImagem = baos.toByteArray();

                        //Salvar imagem no Firebase
                        fotoPerfil = storageReference.child(CHAVE_ST_IMAGES)
                                .child(CHAVE_ST_PROFILE_ALUNOS)
                                .child(idAluno + ".jpg");

                        UploadTask uploadTask = fotoPerfil.putBytes(dadosImagem);
                        uploadTask.addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(EditarPerfilActivity.this, "Falha ao salvar a imagem!", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                StorageReference imgRef = storageReference.child(CHAVE_ST_IMAGES)
                                        .child(CHAVE_ST_PROFILE_ALUNOS).child(idAluno + ".jpg");
                                imgRef.getDownloadUrl().addOnSuccessListener(
                                        EditarPerfilActivity.this, new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                idImgPerfil = uri.toString();
                                            }
                                        });
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
                        campoFoto.setImageBitmap(imagem);
                    }else{
                        Toast.makeText(this,"Imagem vazia", Toast.LENGTH_SHORT).show();
                    }

                }
            });

    private void loadProfile() {
        dataProfile = reference.child(CHAVE_DB_ALUNOS).child(CHAVE_DB_IDPERSONAL).child(idAluno);
        StorageReference fotoRef = storageReference.child(CHAVE_ST_IMAGES)
                .child(CHAVE_ST_PROFILE_ALUNOS).child(idAluno + ".jpg");
        fotoRef.getDownloadUrl().addOnSuccessListener(EditarPerfilActivity.this,
                new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(EditarPerfilActivity.this).load(uri).into(campoFoto);
            }
        }).addOnFailureListener(EditarPerfilActivity.this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("foto", "Erro ao carregar a imagem de perfil");
            }
        });
        valueEventListenerProfile = dataProfile.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Aluno alunoProfile = snapshot.getValue(Aluno.class);
                campoNome.setText(alunoProfile.getNomeAluno());
                campoAcademia.setText(alunoProfile.getAcademia());
                campoNiver.setText(alunoProfile.getDataNiver());
                campoEmail.setText(alunoProfile.getEmailAluno());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void salvarPerfil() {
        alunoNew = new Aluno();
        alunoNew.salvarPerfilAluno(campoNome.getText().toString(),
                campoAcademia.getText().toString(), campoNiver.getText().toString(), idImgPerfil);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save_info, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuSaveInfo){
            salvarPerfil();
            Toast.makeText(this,"Perfil atualizado com sucesso!",Toast.LENGTH_SHORT).show();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}