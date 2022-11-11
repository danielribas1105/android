package com.apps.drpersonalmanager.ui.activity;

import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.CHAVE_ALUNO_SELECT;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.apps.drpersonalmanager.R;
import com.apps.drpersonalmanager.dao.TrainingDao;
import com.apps.drpersonalmanager.helper.Base64Custom;
import com.apps.drpersonalmanager.model.Aluno;
import com.apps.drpersonalmanager.model.Training;

public class CreateTrainingActivity extends AppCompatActivity {

    private EditText campoNomeSerie, campoData, campoDescSerie;
    private Button btnCriarSerie;
    private Training training;
    private TrainingDao trainingDao = new TrainingDao();
    private Aluno aluno;
    private String idAluno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_training);
        setTitle("Montar Treino");
        campoNomeSerie = findViewById(R.id.editTextNomeSerie);
        campoData = findViewById(R.id.editTextData);
        campoDescSerie = findViewById(R.id.editTextDescSerie);

        aluno = (Aluno) getIntent().getSerializableExtra(CHAVE_ALUNO_SELECT);
        if(aluno != null){
            idAluno = Base64Custom.codeToBase64(aluno.getEmailAluno());
        }

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