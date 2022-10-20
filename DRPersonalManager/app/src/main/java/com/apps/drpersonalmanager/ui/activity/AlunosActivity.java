package com.apps.drpersonalmanager.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.apps.drpersonalmanager.R;
import com.apps.drpersonalmanager.adapter.AlunosAdapter;
import com.apps.drpersonalmanager.config.ConfigFirebase;
import com.apps.drpersonalmanager.model.Aluno;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AlunosActivity extends AppCompatActivity {

    private AlunosAdapter alunosAdapter;
    private List<Aluno> alunos = new ArrayList<>();
    private RecyclerView recyclerViewAlunos;
    private DatabaseReference reference = ConfigFirebase.getFirebaseDatabase();
    private DatabaseReference findAlunos;
    private ValueEventListener valueEventListenerAlunos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alunos);

        recyclerViewAlunos = findViewById(R.id.recyclerAlunos);

        //Configurar Adapter
        loadAlunos();
        alunosAdapter = new AlunosAdapter(alunos, this);
        //Configurar RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewAlunos.setLayoutManager(layoutManager);
        recyclerViewAlunos.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        recyclerViewAlunos.setHasFixedSize(true);
        recyclerViewAlunos.setAdapter(alunosAdapter);

    }

    public void loadAlunos(){
        findAlunos = reference.child("alunos");
        valueEventListenerAlunos = findAlunos.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                alunos.clear();
                for(DataSnapshot dadosAlunos: snapshot.getChildren()){
                    Aluno aluno = dadosAlunos.getValue(Aluno.class);
                    alunos.add(aluno);
                }
                alunosAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}