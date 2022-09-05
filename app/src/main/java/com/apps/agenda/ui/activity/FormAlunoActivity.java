package com.apps.agenda.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.apps.agenda.R;
import com.apps.agenda.dao.alunoDao;
import com.apps.agenda.model.Aluno;

public class FormAlunoActivity extends AppCompatActivity {

    public static final String TITLE_APPBAR = "Novo Aluno";
    private EditText campoNome;
    private EditText campoTel;
    private EditText campoEmail;
    final alunoDao dao = new alunoDao();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_aluno);
        setTitle(TITLE_APPBAR);
        iniciaCampos();
        botaoSalvar();
    }

    private void botaoSalvar() {
        Button btnSalvar = findViewById(R.id.activity_form_aluno_btn_salvar);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Aluno alunoCriado = criaAluno();
                salva(alunoCriado);

                Toast.makeText(FormAlunoActivity.this, "Aluno Salvo!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void iniciaCampos() {
        campoNome = findViewById(R.id.activity_form_aluno_nome);
        campoTel = findViewById(R.id.activity_form_aluno_tel);
        campoEmail = findViewById(R.id.activity_form_aluno_email);
    }

    private void salva(Aluno alunoCriado) {
        dao.salvar(alunoCriado);
        finish();
    }

    @NonNull
    private Aluno criaAluno() {
        String nomeAluno = campoNome.getText().toString();
        String telAluno = campoTel.getText().toString();
        String emailAluno = campoEmail.getText().toString();
        Aluno alunoCriado = new Aluno(nomeAluno, telAluno, emailAluno);
        return alunoCriado;
    }
}