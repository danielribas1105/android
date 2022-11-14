package com.apps.drpersonal.ui.activity;

import static com.apps.drpersonal.ui.activity.ConstantesActivities.CHAVE_DB_EXERCICIOS;
import static com.apps.drpersonal.ui.activity.ConstantesActivities.CHAVE_EXERCISE;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.apps.drpersonal.R;
import com.apps.drpersonal.config.ConfigFirebase;
import com.apps.drpersonal.model.Exercise;
import com.apps.drpersonal.model.ExerciseAluno;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class InfoExercActivity extends AppCompatActivity {

    private TextView campoNomeExerc, campoDesc;
    private ImageView campoImagem, campoVideo;
    private String idExerc, nomeExerc;
    private ExerciseAluno exerciseSelected;
    private DatabaseReference reference = ConfigFirebase.getFirebaseDatabase();
    private DatabaseReference refInfoExerc;
    private ValueEventListener valueEventListenerInfoExerc;
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    private StorageReference imagens = storageReference.child("images").child("exercises");
    private StorageReference videos = storageReference.child("videos").child("exercises");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_exerc);

        getSupportActionBar().setTitle("Informações");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        campoNomeExerc = findViewById(R.id.infoExercNome);
        campoImagem = findViewById(R.id.imgImageExerc);
        campoVideo = findViewById(R.id.imgVideoExerc);
        campoDesc = findViewById(R.id.descExercText);

        exerciseSelected = (ExerciseAluno) getIntent().getSerializableExtra(CHAVE_EXERCISE);
        if(exerciseSelected != null){
            idExerc = exerciseSelected.getIdExerc();
            nomeExerc = exerciseSelected.getNomeExerc();
        }

        campoNomeExerc.setText(nomeExerc);

        loadImageExerc(idExerc+".jpg");
        loadVideoExerc(idExerc+".gif");
        loadDescExerc();


    }

    private void loadImageExerc(String infoIdExerc) {
        StorageReference imgRef = imagens.child(infoIdExerc);
        //Log.i("Id",imgRef.toString());
        imgRef.getDownloadUrl().addOnSuccessListener(InfoExercActivity.this, new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(InfoExercActivity.this).load(uri).into(campoImagem);
                //Toast.makeText(InfoExercActivity.this, "Sucesso ao fazer download da imagem", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(InfoExercActivity.this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //Toast.makeText(InfoExercActivity.this, "Erro ao fazer download da imagem", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void loadVideoExerc(String infoIdExerc) {
        StorageReference videoRef = videos.child(infoIdExerc);
        //Log.i("Id",videoRef.toString());
        videoRef.getDownloadUrl().addOnSuccessListener(InfoExercActivity.this, new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(InfoExercActivity.this).load(uri).into(campoVideo);
                //Toast.makeText(InfoExercActivity.this, "Sucesso ao fazer download do vídeo", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(InfoExercActivity.this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //Toast.makeText(InfoExercActivity.this, "Erro ao fazer download do vídeo", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void loadDescExerc() {
        refInfoExerc = reference.child(CHAVE_DB_EXERCICIOS).child("aero")
                .child("1668453335932_exercicio_de_teste");
        valueEventListenerInfoExerc = refInfoExerc.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Exercise exercise = snapshot.getValue(Exercise.class);
                campoDesc.setText(exercise.getDescExerc());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //problema no id do exercicio para ler a descrição !!!!!
    }


}