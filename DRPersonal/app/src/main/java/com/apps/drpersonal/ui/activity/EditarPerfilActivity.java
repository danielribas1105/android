package com.apps.drpersonal.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaFeature;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.apps.drpersonal.R;
import com.apps.drpersonal.config.ConfigFirebase;
import com.apps.drpersonal.helper.Base64Custom;
import com.apps.drpersonal.model.Aluno;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditarPerfilActivity extends AppCompatActivity {

    private ImageButton imgCamera, imgGallery;
    private CircleImageView campoFoto;
    private TextInputEditText campoNome, campoEmail, campoAcademia;
    private Aluno aluno;
    private String idAluno;
    private DatabaseReference dataProfile;
    private DatabaseReference reference = ConfigFirebase.getFirebaseDatabase();
    private FirebaseAuth authPerfil = ConfigFirebase.getFirebaseAutenticacao();
    private ValueEventListener valueEventListenerProfile;
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    private StorageReference fotoPerfil = storageReference.child("images").child("fotoAlunos");
    private static final int SELECT_CAMERA = 100;
    private static final int SELECT_GALLERY = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);

        getSupportActionBar().setTitle("Editar Perfil");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        campoFoto = findViewById(R.id.profile_image);
        imgCamera = findViewById(R.id.imgBtnCamera);
        imgGallery = findViewById(R.id.imgBtnGaleria);
        campoNome = findViewById(R.id.editTextNome);
        campoEmail = findViewById(R.id.editTextEmail);
        campoAcademia = findViewById(R.id.editTextAcademia);

        loadProfile();

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

    private void loadProfile() {
        idAluno = Base64Custom.codeToBase64(authPerfil.getCurrentUser().getEmail());
        dataProfile = reference.child("alunos").child(idAluno);
        StorageReference fotoRef = fotoPerfil.child(idAluno + ".jpg");
        Log.i("Id",fotoRef.toString());
        fotoRef.getDownloadUrl().addOnSuccessListener(EditarPerfilActivity.this, new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(EditarPerfilActivity.this).load(uri).into(campoFoto);
            }
        }).addOnFailureListener(EditarPerfilActivity.this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(EditarPerfilActivity.this, "Erro ao fazer download da imagem", Toast.LENGTH_LONG).show();
            }
        });
        valueEventListenerProfile = dataProfile.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Aluno alunoProfile = snapshot.getValue(Aluno.class);
                campoNome.setText(alunoProfile.getNomeAluno());
                campoEmail.setText(alunoProfile.getEmailAluno());
                campoAcademia.setText(alunoProfile.getAcademia());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void salvarPerfil() {
        aluno = new Aluno();
        aluno.setNomeAluno(campoNome.getText().toString());
        aluno.setEmailAluno(campoEmail.getText().toString());
        aluno.setAcademia(campoAcademia.getText().toString());
        aluno.setFotoAluno("Foto1");
        aluno.setSenhaAluno("1234567");
        aluno.salvarPerfilAluno();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save_info, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuSaveInfo:
                salvarPerfil();
                Toast.makeText(this,"Perfil atualizado!",Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}