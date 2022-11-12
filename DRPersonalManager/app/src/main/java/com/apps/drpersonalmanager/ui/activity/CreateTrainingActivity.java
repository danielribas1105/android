package com.apps.drpersonalmanager.ui.activity;

import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.CAT_ABDOMINAIS;
import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.CAT_AEROBICO;
import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.CAT_MUSC_INFER;
import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.CAT_MUSC_SUPER;
import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.CHAVE_ALUNO_SELECT;
import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.CHAVE_DB_EXERCICIOS;
import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.CHAVE_DB_IDPERSONAL;
import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.CHAVE_DB_TREINOS;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.apps.drpersonalmanager.R;
import com.apps.drpersonalmanager.config.ConfigFirebase;
import com.apps.drpersonalmanager.dao.ExerciseDao;
import com.apps.drpersonalmanager.dao.TrainingDao;
import com.apps.drpersonalmanager.helper.Base64Custom;
import com.apps.drpersonalmanager.model.Aluno;
import com.apps.drpersonalmanager.model.Exercise;
import com.apps.drpersonalmanager.model.Training;
import com.apps.drpersonalmanager.ui.adapter.FindExercisesAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CreateTrainingActivity extends AppCompatActivity {

    private EditText campoNomeSerie, campoData, campoDescSerie;
    private RadioGroup catSelect;
    private Button btnFindExercise;
    private Training training;
    private TrainingDao trainingDao = new TrainingDao();
    private Aluno aluno;
    private String idAluno, categoria = "";
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
        campoNomeSerie = findViewById(R.id.editTextNomeSerie);
        campoData = findViewById(R.id.editTextData);
        campoDescSerie = findViewById(R.id.editTextDescSerie);
        btnFindExercise = findViewById(R.id.btnBuscarExercicios);
        recyclerFindExercises = findViewById(R.id.recyclerBuscarExercicios);

        aluno = (Aluno) getIntent().getSerializableExtra(CHAVE_ALUNO_SELECT);
        if(aluno != null){
            idAluno = Base64Custom.codeToBase64(aluno.getEmailAluno());
        }
        selectedCategory();

        findExercisesAdapter = new FindExercisesAdapter(exercises,this);
        //Configurar RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerFindExercises.setLayoutManager(layoutManager);
        recyclerFindExercises.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        recyclerFindExercises.setHasFixedSize(true);
        recyclerFindExercises.setAdapter(findExercisesAdapter);

        btnFindExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFindExercises();
            }
        });

    }

    private void selectedCategory() {
        catSelect.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int catId) {
                if(catId == R.id.rbAerobico){
                    categoria = CAT_AEROBICO;
                }else if(catId == R.id.rbAbdominais){
                    categoria = CAT_ABDOMINAIS;
                }else if(catId == R.id.rbMuscSuper){
                    categoria = CAT_MUSC_SUPER;
                }else if(catId == R.id.rbMuscInfer){
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
        getMenuInflater().inflate(R.menu.menu_create_training,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.btn_salvar_treino){
            String serie = campoNomeSerie.getText().toString();
            String descricao = campoDescSerie.getText().toString();
            String idSerie = "serie"+serie;
            training = new Training();
            training.setNomeSerie(serie);
            training.setDescSerie(descricao);
            trainingDao.salvar(training,idAluno,idSerie);
        }
        return super.onOptionsItemSelected(item);
    }
}