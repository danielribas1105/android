package com.apps.drpersonalmanager.ui.activity;

import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.CHAVE_EXERCICIO_EDIT;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.apps.drpersonalmanager.R;
import com.apps.drpersonalmanager.model.Exercise;

public class EditTrainigActivity extends AppCompatActivity {

    private TextView editNomeExerc;
    private EditText editQuantExerc, editPesoExerc, editObsExerc;
    private Button btnEditExerc;
    private Exercise exercSelect;

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
        if(exercSelect != null){
            editNomeExerc.setText(exercSelect.getNomeExerc());
        }

        btnEditExerc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(EditTrainigActivity.this, "Exercício salvo com sucesso!",
                        Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}