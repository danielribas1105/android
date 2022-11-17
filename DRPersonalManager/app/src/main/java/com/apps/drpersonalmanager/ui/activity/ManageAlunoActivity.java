package com.apps.drpersonalmanager.ui.activity;

import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.CHAVE_ALUNO_SELECT;
import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.CHAVE_DB_EXERCICIOS_ALUNOS;
import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.CHAVE_DB_IDPERSONAL;
import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.CHAVE_DB_TREINOS;
import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.CHAVE_TREINO_SELECT;
import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.STR_SERIE;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.drpersonalmanager.R;
import com.apps.drpersonalmanager.config.ConfigFirebase;
import com.apps.drpersonalmanager.helper.Base64Custom;
import com.apps.drpersonalmanager.helper.RecyclerItemClickListener;
import com.apps.drpersonalmanager.model.Aluno;
import com.apps.drpersonalmanager.model.ExerciseAluno;
import com.apps.drpersonalmanager.model.Training;
import com.apps.drpersonalmanager.ui.adapter.TreinoSelectAdapter;
import com.apps.drpersonalmanager.ui.adapter.TreinosAlunoAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ManageAlunoActivity extends AppCompatActivity {

    private TextView campoNome, campoAcademia;
    private Aluno alunoSelect;
    private String idAluno, nomeAluno, emailAluno, academiaAluno;
    private List<Training> trainings = new ArrayList<>();
    private List<ExerciseAluno> exerciseAlunos = new ArrayList<>();
    private TreinosAlunoAdapter treinosAlunoAdapter;
    private RecyclerView recyclerTreinos;
    private DatabaseReference reference = ConfigFirebase.getFirebaseDatabase();
    private DatabaseReference treinoAluno;
    private ValueEventListener valueEventListenerTreino;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_aluno);
        getSupportActionBar().setTitle("Aluno");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        campoNome = findViewById(R.id.textAlunoSelect);
        campoAcademia = findViewById(R.id.textAcademiaSelect);
        recyclerTreinos = findViewById(R.id.recyclerTreinosAluno);

        alunoSelect = (Aluno) getIntent().getSerializableExtra(CHAVE_ALUNO_SELECT);
        if (alunoSelect != null) {
            nomeAluno = alunoSelect.getNomeAluno();
            emailAluno = alunoSelect.getEmailAluno();
            academiaAluno = alunoSelect.getAcademia();
        }
        campoNome.setText(nomeAluno);
        campoAcademia.setText(academiaAluno);

        //Configurar Adapter
        loadTraining();
        treinosAlunoAdapter = new TreinosAlunoAdapter(trainings, this);
        //Configurar RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerTreinos.setLayoutManager(layoutManager);
        recyclerTreinos.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        recyclerTreinos.setHasFixedSize(true);
        recyclerTreinos.setAdapter(treinosAlunoAdapter);

        recyclerTreinos.addOnItemTouchListener(new RecyclerItemClickListener(
                getApplicationContext(), recyclerTreinos,
                new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //Recuperar serie selecionada
                Training trainingSelect = trainings.get(position);
                Intent i = new Intent(ManageAlunoActivity.this, EditTrainingSelectActivity.class);
                i.putExtra(CHAVE_TREINO_SELECT,trainingSelect)
                        .putExtra(CHAVE_ALUNO_SELECT,emailAluno);
                startActivity(i);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_manage_aluno, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.btn_add_treino) {
            Intent i = new Intent(ManageAlunoActivity.this, CreateTrainingActivity.class);
            i.putExtra(CHAVE_ALUNO_SELECT, alunoSelect);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadTraining() {
        idAluno = Base64Custom.codeToBase64(emailAluno);
        treinoAluno = reference.child(CHAVE_DB_TREINOS).child(CHAVE_DB_IDPERSONAL).child(idAluno);
        valueEventListenerTreino = treinoAluno.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                trainings.clear();
                for (DataSnapshot infoTreinos : snapshot.getChildren()) {
                    Training training = infoTreinos.getValue(Training.class);
                    trainings.add(training);
                }
                treinosAlunoAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}