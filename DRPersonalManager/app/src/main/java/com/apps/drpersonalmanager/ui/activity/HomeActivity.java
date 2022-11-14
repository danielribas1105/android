package com.apps.drpersonalmanager.ui.activity;

import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.CHAVE_ALUNO_SELECT;
import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.CHAVE_DB_ALUNOS;
import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.CHAVE_DB_IDPERSONAL;
import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.CHAVE_DB_PERSONAL;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.apps.drpersonalmanager.R;
import com.apps.drpersonalmanager.config.ConfigFirebase;
import com.apps.drpersonalmanager.helper.Base64Custom;
import com.apps.drpersonalmanager.helper.RecyclerItemClickListener;
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
    private FirebaseAuth auth = ConfigFirebase.getFirebaseAutenticacao();
    private DatabaseReference reference = ConfigFirebase.getFirebaseDatabase();
    private DatabaseReference refDbPersonal;
    private DatabaseReference findAlunos;
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
        //Configurar evento de click no RecyclerView
        recyclerViewAlunos.addOnItemTouchListener(new RecyclerItemClickListener(
                getApplicationContext(), recyclerViewAlunos,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        //Recuperar aluno selecionado
                        Aluno alunoSelected = alunos.get(position);
                        //Enviar aluno para a próxima tela
                        Intent i = new Intent(HomeActivity.this, ManageAlunoActivity.class);
                        i.putExtra(CHAVE_ALUNO_SELECT, alunoSelected);
                        startActivity(i);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }

                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    }
                }));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btn_menu_meu_perfil:
                startActivity(new Intent(this, MyProfileActivity.class));
                break;
            case R.id.btn_menu_novo_aluno:
                startActivity(new Intent(this, NewAlunoActivity.class));
                break;
            case R.id.btn_menu_novo_exercicio:
                startActivity(new Intent(this, NewExerciseActivity.class));
                break;
            case R.id.btn_menu_sair:
                auth.signOut();
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
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

    public void loadAlunos() {
        findAlunos = reference.child(CHAVE_DB_ALUNOS).child(CHAVE_DB_IDPERSONAL);
        valueEventListenerAlunos = findAlunos.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                alunos.clear();
                for (DataSnapshot dadosAlunos : snapshot.getChildren()) {
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
        refDbPersonal = reference.child(CHAVE_DB_PERSONAL).child(CHAVE_DB_IDPERSONAL);
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