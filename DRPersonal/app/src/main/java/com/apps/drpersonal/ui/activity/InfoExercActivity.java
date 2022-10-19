package com.apps.drpersonal.ui.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.apps.drpersonal.R;
import com.apps.drpersonal.config.ConfigFirebase;
import com.apps.drpersonal.model.Exercise;
import com.apps.drpersonal.model.InfoExercise;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class InfoExercActivity extends AppCompatActivity {

    private TextView campoNomeExerc, campoVideo, campoImg, campoDesc;
    private String infoIdExerc, keyExerc;
    private Exercise exerciseSelected;
    private DatabaseReference referenceInfoExerc = ConfigFirebase.getFirebaseDatabase();
    private DatabaseReference refInfoExerc;
    private ValueEventListener valueEventListenerInfoExerc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_exerc);

        campoNomeExerc = findViewById(R.id.infoExercNome);
        exerciseSelected = (Exercise) getIntent().getSerializableExtra("ExerciseSelect");
        campoNomeExerc.setText(exerciseSelected.getNomeExerc());
        campoVideo = findViewById(R.id.videoExercText);
        campoImg = findViewById(R.id.imgExercText);
        campoDesc = findViewById(R.id.descExercText);

        infoIdExerc = exerciseSelected.getIdExerc();
        loadInfoExerc(infoIdExerc);

    }

    public void loadInfoExerc(String idExerc) {
        refInfoExerc = referenceInfoExerc.child("exerciciosInfo").child(idExerc);
        valueEventListenerInfoExerc = refInfoExerc.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                InfoExercise infoExercise = snapshot.getValue(InfoExercise.class);
                campoVideo.setText(infoExercise.getVideoExerc());
                campoImg.setText(infoExercise.getImgExerc());
                campoDesc.setText(infoExercise.getDescExerc());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}