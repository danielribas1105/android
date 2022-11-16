package com.apps.drpersonalmanager.ui.activity;

import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.CAT_ABDOMINAIS;
import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.CAT_AEROBICO;
import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.CAT_MUSC_INFER;
import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.CAT_MUSC_SUPER;
import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.CHAVE_ALUNO_SELECT;
import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.CHAVE_CATEGORIA;
import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.CHAVE_DB_EXERCICIOS;
import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.CHAVE_EXERCICIO_EDIT;
import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.CHAVE_ID_SERIE;
import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.STR_SERIE;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.drpersonalmanager.R;
import com.apps.drpersonalmanager.config.ConfigFirebase;
import com.apps.drpersonalmanager.dao.ExerciseDao;
import com.apps.drpersonalmanager.dao.TrainingDao;
import com.apps.drpersonalmanager.helper.Base64Custom;
import com.apps.drpersonalmanager.helper.DataCustom;
import com.apps.drpersonalmanager.helper.RecyclerItemClickListener;
import com.apps.drpersonalmanager.model.Aluno;
import com.apps.drpersonalmanager.model.Exercise;
import com.apps.drpersonalmanager.model.ExerciseAluno;
import com.apps.drpersonalmanager.model.Training;
import com.apps.drpersonalmanager.ui.adapter.FindExercisesAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CreateTrainingActivity extends AppCompatActivity {

    private EditText campoNomeAluno, campoNomeSerie, campoData, campoDescSerie;
    private RadioGroup catSelect;
    private Button btnFindExercise;
    private Training training;
    private TrainingDao trainingDao = new TrainingDao();
    private ExerciseAluno exerciseAluno;
    private Aluno aluno;
    private String serie, idSerie, objetivo;
    private String idAluno, nomeAluno, dataTreino, categoria = "";
    private List<Exercise> exercises = new ArrayList<>();
    private RecyclerView recyclerFindExercises;
    private FindExercisesAdapter findExercisesAdapter;
    private ExerciseDao exerciseDao = new ExerciseDao();
    private DatabaseReference reference = ConfigFirebase.getFirebaseDatabase();
    private DatabaseReference findExercises;
    private ValueEventListener valueEventListenerFindExerc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_training);
        setTitle("Montar Treino");
        campoNomeAluno = findViewById(R.id.editTextNomeAluno);
        campoNomeSerie = findViewById(R.id.editTextNomeSerie);
        campoData = findViewById(R.id.editTextData);
        campoDescSerie = findViewById(R.id.editTextDescSerie);
        btnFindExercise = findViewById(R.id.btnBuscarExercicios);
        catSelect = findViewById(R.id.rgSelecaoCat);
        recyclerFindExercises = findViewById(R.id.recyclerBuscarExercicios);

        aluno = (Aluno) getIntent().getSerializableExtra(CHAVE_ALUNO_SELECT);
        if (aluno != null) {
            idAluno = Base64Custom.codeToBase64(aluno.getEmailAluno());
            nomeAluno = aluno.getNomeAluno();
        }
        campoNomeAluno.setText(nomeAluno);
        dataTreino = DataCustom.dataAtual();
        campoData.setText(dataTreino);
        selectedCategory();

        findExercisesAdapter = new FindExercisesAdapter(exercises, this);
        //Configurar RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerFindExercises.setLayoutManager(layoutManager);
        recyclerFindExercises.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        recyclerFindExercises.setHasFixedSize(true);
        recyclerFindExercises.setAdapter(findExercisesAdapter);

        //Configurar evento de click no RecyclerView Exercícios
        recyclerFindExercises.addOnItemTouchListener(new RecyclerItemClickListener(
                getApplicationContext(), recyclerFindExercises,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        //Recuperar exercício selecionado
                        Exercise exerciseSelected = exercises.get(position);
                        loadInfoTreino();
                        //Passar informações para a próxima activity
                        Intent i = new Intent(CreateTrainingActivity.this, EditTrainigActivity.class);
                        i.putExtra(CHAVE_EXERCICIO_EDIT, exerciseSelected)
                                .putExtra(CHAVE_ALUNO_SELECT, idAluno)
                                .putExtra(CHAVE_ID_SERIE, idSerie)
                                .putExtra(CHAVE_CATEGORIA, categoria);
                        startActivity(i);

                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }

                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    }
                }));

        btnFindExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFindExercises();
            }
        });

    }

    private void loadInfoTreino() {
        serie = campoNomeSerie.getText().toString();
        idSerie = STR_SERIE + serie;
        objetivo = campoDescSerie.getText().toString();
    }

    private void selectedCategory() {
        catSelect.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int catId) {
                if (catId == R.id.rbCatAero) {
                    categoria = CAT_AEROBICO;
                } else if (catId == R.id.rbCatAbdo) {
                    categoria = CAT_ABDOMINAIS;
                } else if (catId == R.id.rbCatMuscSuper) {
                    categoria = CAT_MUSC_SUPER;
                } else if (catId == R.id.rbCatMuscInfer) {
                    categoria = CAT_MUSC_INFER;
                }
            }
        });
    }

    private void loadFindExercises() {
        findExercises = reference.child(CHAVE_DB_EXERCICIOS).child(categoria);
        valueEventListenerFindExerc = findExercises.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                exercises.clear();
                for (DataSnapshot infoTreinos : snapshot.getChildren()) {
                    Exercise exercise = infoTreinos.getValue(Exercise.class);
                    exercises.add(exercise);
                }
                findExercisesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_create_training, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.btn_salvar_treino) {
            loadInfoTreino();
            if (!objetivo.isEmpty()) {
                training = new Training();
                training.setNomeSerie(serie);
                training.setDescSerie(objetivo);
                trainingDao.salvarTreino(idAluno, idSerie, training);
                Toast.makeText(this, "Treino salvo com sucesso!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Preencher campo objetivo da série!", Toast.LENGTH_SHORT).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}