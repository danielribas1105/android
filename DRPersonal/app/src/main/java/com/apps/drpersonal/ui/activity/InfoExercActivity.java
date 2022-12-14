package com.apps.drpersonal.ui.activity;

import static com.apps.drpersonal.ui.activity.ConstantesActivities.CHAVE_DB_EXERCICIOS;
import static com.apps.drpersonal.ui.activity.ConstantesActivities.CHAVE_EXERCISE;
import static com.apps.drpersonal.ui.activity.ConstantesActivities.CHAVE_ST_EXERCISES;
import static com.apps.drpersonal.ui.activity.ConstantesActivities.CHAVE_ST_IMAGES;
import static com.apps.drpersonal.ui.activity.ConstantesActivities.CHAVE_ST_VIDEOS;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.apps.drpersonal.R;
import com.apps.drpersonal.config.ConfigFirebase;
import com.apps.drpersonal.dao.InfoExercDao;
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

import java.util.ArrayList;
import java.util.List;

public class InfoExercActivity extends AppCompatActivity {

    private TextView campoNomeExerc, campoDesc;
    private ImageView campoImagem, campoVideo;
    private String idExerc, nomeExerc, catExerc;
    private List<ExerciseAluno> exerciseAlunoList = new ArrayList<>();
    private ExerciseAluno exerciseSelected;
    private DatabaseReference reference = ConfigFirebase.getFirebaseDatabase();
    private DatabaseReference refInfoExerc;
    private ValueEventListener valueEventListenerInfoExerc;
    private InfoExercDao infoExercDao = new InfoExercDao();
    private StorageReference storageReference = ConfigFirebase.getStorageReference();
    private StorageReference imagens = storageReference.child(CHAVE_ST_IMAGES).child(CHAVE_ST_EXERCISES);
    private StorageReference videos = storageReference.child(CHAVE_ST_VIDEOS).child(CHAVE_ST_EXERCISES);

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
            catExerc = exerciseSelected.getCatExerc();
        }
        //exerciseAlunoList.add(exerciseSelected);
        //String teste = exerciseSelected;
        //Log.i("key", "id "+teste);

        campoNomeExerc.setText(nomeExerc);

        loadImageExerc(idExerc+"_full.jpg");
        loadVideoExerc(idExerc+".gif");
        loadDescExerc();

    }

    private void loadImageExerc(String infoIdExerc) {
        StorageReference imgRef = imagens.child(infoIdExerc);
        imgRef.getDownloadUrl().addOnSuccessListener(InfoExercActivity.this, new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(InfoExercActivity.this).load(uri).into(campoImagem);
            }
        }).addOnFailureListener(InfoExercActivity.this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });
    }

    private void loadVideoExerc(String infoIdExerc) {
        StorageReference videoRef = videos.child(infoIdExerc);
        videoRef.getDownloadUrl().addOnSuccessListener(InfoExercActivity.this, new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(InfoExercActivity.this).load(uri).into(campoVideo);
            }
        }).addOnFailureListener(InfoExercActivity.this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });
    }

    private void loadDescExerc() {
        refInfoExerc = reference.child(CHAVE_DB_EXERCICIOS).child(catExerc)
                .child(idExerc);
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

    }


}