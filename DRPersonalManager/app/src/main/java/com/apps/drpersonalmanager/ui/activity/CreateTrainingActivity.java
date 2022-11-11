package com.apps.drpersonalmanager.ui.activity;

import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.CHAVE_ALUNO_SELECT;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.apps.drpersonalmanager.R;
import com.apps.drpersonalmanager.dao.TrainingDao;
import com.apps.drpersonalmanager.helper.Base64Custom;
import com.apps.drpersonalmanager.model.Aluno;
import com.apps.drpersonalmanager.model.Training;

public class CreateTrainingActivity extends AppCompatActivity {

    private EditText campoNomeSerie, campoDescSerie;
    private Button btnCriarSerie;
    private Training training;
    private TrainingDao trainingDao = new TrainingDao();
    private Aluno aluno;
    private String idAluno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_training);
        setTitle("Treinos");
        campoNomeSerie = findViewById(R.id.editTextNomeSerie);
        campoDescSerie = findViewById(R.id.editTextDescSerie);
        btnCriarSerie = findViewById(R.id.btnIncluirSerie);

        aluno = (Aluno) getIntent().getSerializableExtra(CHAVE_ALUNO_SELECT);
        if(aluno != null){
            idAluno = Base64Custom.codeToBase64(aluno.getEmailAluno());
        }

        btnCriarSerie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String serie = campoNomeSerie.getText().toString();
                String descricao = campoDescSerie.getText().toString();
                String idSerie = "serie"+serie;
                training = new Training();
                training.setNomeSerie(serie);
                training.setDescSerie(descricao);
                //Log.i("dados",idAluno);
                //Log.i("dados",training.getNomeSerie());
                //Log.i("dados",training.getDescSerie());
                trainingDao.salvar(training,idAluno,idSerie);

            }
        });

    }
}