package com.apps.drpersonalmanager.ui.activity;

import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.CHAVE_ALUNO_SELECT;
import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.CHAVE_CATEGORIA;
import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.CHAVE_EXERCICIO_EDIT;
import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.CHAVE_ID_SERIE;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.apps.drpersonalmanager.R;
import com.apps.drpersonalmanager.dao.ExerciseDao;
import com.apps.drpersonalmanager.model.Exercise;
import com.apps.drpersonalmanager.model.ExerciseAluno;

public class EditTrainingActivity extends AppCompatActivity {

    private TextView editNomeExerc;
    private EditText editQuantExerc, editPesoExerc, editObsExerc;
    private Button btnEditExerc;
    private Exercise exercSelect;
    private ExerciseAluno exerciseAluno;
    private ExerciseDao exerciseDao = new ExerciseDao();
    private String idAluno, idSerie, catExerc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_trainig);
        setTitle("Editar Exercício");
        editNomeExerc = findViewById(R.id.textEditNomeExerc);
        editQuantExerc = findViewById(R.id.editQuantExerc);
        editPesoExerc = findViewById(R.id.editPesoExerc);
        editObsExerc = findViewById(R.id.editObsExerc);
        btnEditExerc = findViewById(R.id.btnSaveEditExerc);

        exercSelect = (Exercise) getIntent().getSerializableExtra(CHAVE_EXERCICIO_EDIT);
        if (exercSelect != null) {
            editNomeExerc.setText(exercSelect.getNomeExerc());
        }
        idAluno = (String) getIntent().getSerializableExtra(CHAVE_ALUNO_SELECT);
        idSerie = (String) getIntent().getSerializableExtra(CHAVE_ID_SERIE);
        catExerc = (String) getIntent().getSerializableExtra(CHAVE_CATEGORIA);

        btnEditExerc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exerciseAluno = new ExerciseAluno();
                exerciseAluno.setNomeExerc(exercSelect.getNomeExerc());
                exerciseAluno.setIdExerc(exercSelect.getIdExerc());
                exerciseAluno.setIdImg(exercSelect.getIdImgExerc());
                exerciseAluno.setCatExerc(catExerc);
                exerciseAluno.setQuantExerc(editQuantExerc.getText().toString());
                exerciseAluno.setPesoExerc(editPesoExerc.getText().toString());
                exerciseAluno.setObsExerc(editObsExerc.getText().toString());
                exerciseDao.salvarExercAluno(idAluno, idSerie, exerciseAluno);

                Toast.makeText(EditTrainingActivity.this, "Exercício " +
                        exercSelect.getNomeExerc() + " salvo com sucesso!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}