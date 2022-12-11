package com.apps.drpersonalmanager.ui.activity;

import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.CHAVE_ALUNO_SELECT;
import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.CHAVE_DB_EXERCICIOS_ALUNOS;
import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.CHAVE_DB_IDPERSONAL;
import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.CHAVE_DB_TREINOS;
import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.CHAVE_TREINO_SELECT;
import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.STR_SERIE;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.drpersonalmanager.R;
import com.apps.drpersonalmanager.config.ConfigFirebase;
import com.apps.drpersonalmanager.dao.ExerciseDao;
import com.apps.drpersonalmanager.dao.TrainingDao;
import com.apps.drpersonalmanager.helper.Base64Custom;
import com.apps.drpersonalmanager.helper.RecyclerItemClickListener;
import com.apps.drpersonalmanager.model.Aluno;
import com.apps.drpersonalmanager.model.ExerciseAluno;
import com.apps.drpersonalmanager.model.Training;
import com.apps.drpersonalmanager.ui.adapter.TreinoSelectAdapter;
import com.apps.drpersonalmanager.ui.adapter.TreinosAlunoAdapter;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ManageAlunoActivity extends AppCompatActivity {

    private TextView campoNome, campoAcademia;
    private CircleImageView fotoAluno;
    private Aluno alunoSelect;
    private String idAluno, idFotoAluno, nomeAluno, emailAluno, academiaAluno;
    private List<Training> trainings = new ArrayList<>();
    private List<ExerciseAluno> exerciseAlunos = new ArrayList<>();
    private TreinosAlunoAdapter treinosAlunoAdapter;
    private RecyclerView recyclerTreinos;
    private DatabaseReference reference = ConfigFirebase.getFirebaseDatabase();
    private DatabaseReference treinoAluno;
    private ValueEventListener valueEventListenerTreino;
    private TrainingDao trainingDao = new TrainingDao();
    private ExerciseDao exerciseDao = new ExerciseDao();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_aluno);
        getSupportActionBar().setTitle("Aluno");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        fotoAluno = findViewById(R.id.fotoPerfilAluno);
        campoNome = findViewById(R.id.textAlunoSelect);
        campoAcademia = findViewById(R.id.textAcademiaSelect);
        recyclerTreinos = findViewById(R.id.recyclerTreinosAluno);

        alunoSelect = (Aluno) getIntent().getSerializableExtra(CHAVE_ALUNO_SELECT);
        if (alunoSelect != null) {
            idFotoAluno = alunoSelect.getIdImgAluno();
            nomeAluno = alunoSelect.getNomeAluno();
            emailAluno = alunoSelect.getEmailAluno();
            academiaAluno = alunoSelect.getAcademia();
        }
        Glide.with(ManageAlunoActivity.this).load(Uri.parse(idFotoAluno)).into(fotoAluno);
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
                Training trainingSelect = trainings.get(position);
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(ManageAlunoActivity.this);
                alertDialog.setTitle("EXCLUIR - SÉRIE " + trainingSelect.getNomeSerie());
                alertDialog.setMessage("A série selecionada será excluída definitivamente. Confirmar?");
                alertDialog.setCancelable(false);
                alertDialog.setPositiveButton("SIM", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        trainingDao.excluirTreino(Base64Custom.codeToBase64(emailAluno),
                                STR_SERIE+trainingSelect.getNomeSerie());
                        exerciseDao.excluirSerieExercAluno(Base64Custom.codeToBase64(emailAluno),
                                STR_SERIE+trainingSelect.getNomeSerie());
                        Toast.makeText(ManageAlunoActivity.this, "Série "
                                + trainingSelect.getNomeSerie()
                                + " excluída com sucesso!", Toast.LENGTH_SHORT).show();
                    }
                }).setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                alertDialog.create();
                alertDialog.show();
            }

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        }
        ));

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

}