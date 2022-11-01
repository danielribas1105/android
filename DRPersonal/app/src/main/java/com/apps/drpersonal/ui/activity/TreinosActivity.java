package com.apps.drpersonal.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import java.util.ArrayList;
import java.util.List;

public class TreinosActivity extends AppCompatActivity {

    private TextView campoHello;
    private String idAluno, idTreino = "102022";
    private FirebaseAuth auth = ConfigFirebase.getFirebaseAutenticacao();
    private DatabaseReference reference = ConfigFirebase.getFirebaseDatabase();
    private DatabaseReference alunoDB, treinoAluno;
    private ValueEventListener valueEventListenerAluno;
    private ValueEventListener valueEventListenerTreino;
    private TreinosAdapter treinosAdapter;
    private List<Training> trainings = new ArrayList<>();
    private RecyclerView recyclerTraining;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treinos);

        getSupportActionBar().setTitle("Meus Treinos");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
                        //Recuperar treino selecionado
                        Training trainingSelected = trainings.get(position);
                        //Enviar treino selecionado para a próxima tela
                        Intent intent = new Intent(TreinosActivity.this,
                                ExerciciosActivity.class);
                        intent.putExtra("keyTraining", trainingSelected);
                        startActivity(intent);
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
        idAluno = Base64Custom.codeToBase64(auth.getCurrentUser().getEmail());
        alunoDB = reference.child("alunos").child(idAluno);
        valueEventListenerAluno = alunoDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Aluno aluno = snapshot.getValue(Aluno.class);
                campoHello.setText("Olá, " + aluno.getNomeAluno() + "!");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void loadTraining() {
        idAluno = Base64Custom.codeToBase64(auth.getCurrentUser().getEmail());
        treinoAluno = reference.child("treinos").child(idAluno).child(idTreino);
        valueEventListenerTreino = treinoAluno.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                trainings.clear();
                for (DataSnapshot infoTreinos : snapshot.getChildren()) {
                    Training training = infoTreinos.getValue(Training.class);
                    trainings.add(training);
                }
                treinosAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        getNomeAluno();
        loadTraining();
    }

    @Override
    protected void onStop() {
        super.onStop();
        alunoDB.removeEventListener(valueEventListenerAluno);
        treinoAluno.removeEventListener(valueEventListenerTreino);
    }
}