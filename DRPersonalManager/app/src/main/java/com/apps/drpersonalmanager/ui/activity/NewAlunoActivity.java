package com.apps.drpersonalmanager.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.apps.drpersonalmanager.R;
import com.apps.drpersonalmanager.dao.AlunoDao;
import com.apps.drpersonalmanager.model.Aluno;

public class NewAlunoActivity extends AppCompatActivity {

    private EditText nomeNewAluno, emailNewAluno, gymNewAluno;
    private Button btnSaveNewAluno;
    private Aluno alunoNew;
    private AlunoDao alunoDao = new AlunoDao();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_aluno);

        nomeNewAluno = findViewById(R.id.editNewNomeAluno);
        emailNewAluno = findViewById(R.id.editNewEmailAluno);
        gymNewAluno = findViewById(R.id.editNewGymAluno);
        btnSaveNewAluno = findViewById(R.id.btnSalvarNovoAluno);

        btnSaveNewAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alunoNew = new Aluno();
                alunoNew.setNomeAluno(nomeNewAluno.getText().toString());
                alunoNew.setEmailAluno(emailNewAluno.getText().toString());
                alunoNew.setAcademia(gymNewAluno.getText().toString());
                alunoNew.setSenhaAluno("123456");
                alunoDao.salvarNovoAluno(alunoNew,emailNewAluno.getText().toString());
                Toast.makeText(NewAlunoActivity.this,
                        "Aluno(a) "+ nomeNewAluno.getText().toString() + " salvo(a) com sucesso!",
                        Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}