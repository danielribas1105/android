package com.apps.drpersonal.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.apps.drpersonal.R;
import com.apps.drpersonal.config.ConfigFirebase;
import com.apps.drpersonal.helper.Base64Custom;
import com.apps.drpersonal.helper.RecyclerItemClickListener;
import com.apps.drpersonal.model.Aluno;
import com.apps.drpersonal.model.Training;
import com.apps.drpersonal.ui.adapter.TreinosAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MeusTreinosActivity extends AppCompatActivity {

    private TextView campoHello;
    private FirebaseAuth auth = ConfigFirebase.getFirebaseAutenticacao();
    private DatabaseReference reference = ConfigFirebase.getFirebaseDatabase();
    private DatabaseReference alunoDB;
    private ValueEventListener valueEventListenerAluno;
    private TreinosAdapter treinosAdapter;
    private List<Training> trainings = new ArrayList<>();
    private RecyclerView recyclerTraining;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meus_treinos);

        campoHello = findViewById(R.id.textHello);
        recyclerTraining = findViewById(R.id.recyclerTreinos);
        getNomeAluno();

        //Configurar Adapter
        loadTraining();
        treinosAdapter = new TreinosAdapter(trainings, this);
        //Configurar RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerTraining.setLayoutManager(layoutManager);
        recyclerTraining.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        recyclerTraining.setHasFixedSize(true);
        recyclerTraining.setAdapter(treinosAdapter);

        //Configurar evento de click no Recycler View
        recyclerTraining.addOnItemTouchListener(new RecyclerItemClickListener(
                getApplicationContext(), recyclerTraining,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        int serie = position;
                        goToExercise(serie);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }

                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    }
                }
        ));

    }

    public void getNomeAluno() {
        String idAluno = Base64Custom.codeToBase64(auth.getCurrentUser().getEmail());
        alunoDB = reference.child("alunos").child(idAluno);
        valueEventListenerAluno = alunoDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Aluno aluno = snapshot.getValue(Aluno.class);
                campoHello.setText("Olá, " + aluno.getNomeAluno() + "!");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    public void loadTraining(){
        trainings.add(new Training(R.drawable.logo_circular,"Série de peito e tríceps"));
        trainings.add(new Training(R.drawable.logo_circular,"Série de costas e bíceps"));
        trainings.add(new Training(R.drawable.logo_circular,"Série de abdominais"));
        trainings.add(new Training(R.drawable.logo_circular,"Teste de quantidade"));
        trainings.add(new Training(R.drawable.logo_circular,"Teste de quantidade"));
        trainings.add(new Training(R.drawable.logo_circular,"Teste de quantidade"));
    }

    public void goToExercise(int serieEscolhida){
        Intent intent = new Intent(this, MeusExerciciosActivity.class);
        intent.putExtra("option",serieEscolhida);
        startActivity(intent);
    }

}