package com.apps.drpersonal.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.apps.drpersonal.R;
import com.apps.drpersonal.config.ConfigFirebase;
import com.apps.drpersonal.helper.Base64Custom;
import com.apps.drpersonal.model.Exercise;
import com.apps.drpersonal.model.InfoExercise;
import com.apps.drpersonal.ui.adapter.InfoExercAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class InfoExercActivity extends AppCompatActivity {

    private TextView campoNomeExerc;
    private String infoIdExerc, idAluno;
    private Exercise exerciseSelected;
    private RecyclerView recyclerInfoExerc;
    private InfoExercAdapter adapterInfoExerc;
    private List<InfoExercise> infoExercises = new ArrayList<>();
    private FirebaseAuth auth = ConfigFirebase.getFirebaseAutenticacao();
    private DatabaseReference referenceInfoExerc = ConfigFirebase.getFirebaseDatabase();
    private DatabaseReference refInfoExerc;
    private ValueEventListener valueEventListenerInfoExerc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_exerc);

        campoNomeExerc = findViewById(R.id.infoExercNome);
        exerciseSelected = (Exercise) getIntent().getSerializableExtra("nomeExercicio");
        campoNomeExerc.setText(exerciseSelected.getNomeExerc());

        recyclerInfoExerc = findViewById(R.id.recyclerExercInfo);

        infoIdExerc = "idRefExerc1";
        loadInfoExerc(infoIdExerc);

        //Configurar Adapter
        adapterInfoExerc = new InfoExercAdapter(infoExercises, this);
        //Configurar RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerInfoExerc.setLayoutManager(layoutManager);
        recyclerInfoExerc.setAdapter(adapterInfoExerc);

    }

    public void loadInfoExerc(String idExerc){
        refInfoExerc = referenceInfoExerc.child("exerciciosInfo").child(idExerc);
        valueEventListenerInfoExerc = refInfoExerc.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                infoExercises.clear();
                for (DataSnapshot dataInfo: snapshot.getChildren()){
                    Log.i("dados",dataInfo.toString());
                    InfoExercise infoExercise = dataInfo.getValue(InfoExercise.class);
                    infoExercises.add(infoExercise);
                }
                adapterInfoExerc.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //infoExercises.add(new InfoExercise("Vídeo1", "Imagem1","Descrição1"));
    }
}