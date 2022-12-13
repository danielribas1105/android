package com.apps.drpersonalmanager.ui.activity;

import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.CHAVE_ALUNO_SELECT;
import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.CHAVE_DB_EXERCICIOS_ALUNOS;
import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.CHAVE_ID_SERIE;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.apps.drpersonalmanager.R;
import com.apps.drpersonalmanager.dao.ExerciseDao;
import com.apps.drpersonalmanager.model.ExerciseAluno;

public class EditExerciseSelectActivity extends AppCompatActivity {

    private TextView nomeExerc;
    private EditText quantExerc, pesoExerc, obsExerc;
    private Button saveEdicaoExerc;
    private String emailAluno, idSerie, key;
    private ExerciseAluno exerciseAluno;
    private ExerciseDao exerciseDao = new ExerciseDao();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_exercise_select);
        setTitle("Editar Repetições e Pesos");
        nomeExerc = findViewById(R.id.textExercSelecao);
        quantExerc = findViewById(R.id.editTextQuantExerc);
        pesoExerc = findViewById(R.id.editTextPesoExerc);
        obsExerc = findViewById(R.id.editTextObsExerc);
        saveEdicaoExerc = findViewById(R.id.btnSalvarEdicao);
        exerciseAluno = (ExerciseAluno) getIntent().getSerializableExtra(CHAVE_DB_EXERCICIOS_ALUNOS);
        if(exerciseAluno != null){
            nomeExerc.setText(exerciseAluno.getNomeExerc());
            quantExerc.setText(exerciseAluno.getQuantExerc());
            pesoExerc.setText(exerciseAluno.getPesoExerc());
            obsExerc.setText(exerciseAluno.getObsExerc());
            key = exerciseAluno.getKey();
        }

        emailAluno = (String) getIntent().getSerializableExtra(CHAVE_ALUNO_SELECT);
        idSerie = (String) getIntent().getSerializableExtra(CHAVE_ID_SERIE);

        saveEdicaoExerc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String repeticoes = quantExerc.getText().toString();
                String peso = pesoExerc.getText().toString();
                String observacoes = obsExerc.getText().toString();
                ExerciseAluno exerciseAluno = new ExerciseAluno();
                exerciseAluno.setQuantExerc(repeticoes);
                exerciseAluno.setPesoExerc(peso);
                exerciseAluno.setObsExerc(observacoes);
                exerciseDao.editarExercAluno(emailAluno, idSerie, key,exerciseAluno);
                Toast.makeText(EditExerciseSelectActivity.this,
                        "Exercício atualizado com sucesso!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }


}