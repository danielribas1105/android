package com.apps.drpersonalmanager.ui.activity;

import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.CHAVE_DB_ALUNOS;
import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.CHAVE_DB_PERSONAL;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.apps.drpersonalmanager.R;
import com.apps.drpersonalmanager.config.ConfigFirebase;
import com.apps.drpersonalmanager.helper.Base64Custom;
import com.apps.drpersonalmanager.model.Aluno;
import com.apps.drpersonalmanager.model.Personal;
import com.apps.drpersonalmanager.ui.adapter.AlunosAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private TextView campoHello;
    private AlunosAdapter alunosAdapter;
    private RecyclerView recyclerViewAlunos;
    private List<Aluno> alunos = new ArrayList<>();
    private FirebaseAuth auth;
    private DatabaseReference reference = ConfigFirebase.getFirebaseDatabase();
    private DatabaseReference refDbPersonal;
    private DatabaseReference findAlunos;
    private String idPersonal;
    private ValueEventListener valueEventListenerPersonal;
    private ValueEventListener valueEventListenerAlunos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        campoHello = findViewById(R.id.textHomeOla);
        recyclerViewAlunos = findViewById(R.id.recyclerMeusAlunos);
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

    @Override
    protected void onStart() {
        super.onStart();
        getPersonalLog();
    }

    @Override
    protected void onStop() {
        super.onStop();
        refDbPersonal.removeEventListener(valueEventListenerPersonal);
    }

    public void loadAlunos(){
        findAlunos = reference.child(CHAVE_DB_ALUNOS);
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

    private void getPersonalLog() {
        auth = ConfigFirebase.getFirebaseAutenticacao();
        idPersonal = Base64Custom.codeToBase64(auth.getCurrentUser().getEmail());
        refDbPersonal = reference.child(CHAVE_DB_PERSONAL).child(idPersonal);
        valueEventListenerPersonal = refDbPersonal.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Personal personal = snapshot.getValue(Personal.class);
                campoHello.setText("Olá, " + personal.getNomePersonal() + "!");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}