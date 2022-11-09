package com.apps.agendaalura.ui.activity;

import static com.apps.agendaalura.ui.activity.ConstantesActivities.CHAVE_ALUNO;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.apps.agendaalura.R;
import com.apps.agendaalura.dao.AlunoDao;
import com.apps.agendaalura.model.Aluno;

public class FormularioAlunoActivity extends AppCompatActivity {

    private static final String TITLE_APPBAR_NEW_ALUNO = "Novo Aluno";
    private static final String TITLE_APPBAR_EDIT_ALUNO = "Editar Aluno";
    private EditText campoNome, campoTelefone, campoEmail;
    private final AlunoDao dao = new AlunoDao();
    private Aluno aluno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_aluno);
        inicializarCampos();
        configBtnSalvar();
        loadInfoAluno();
    }

    private void loadInfoAluno() {
        Intent dados = getIntent();
        if (dados.hasExtra(CHAVE_ALUNO)) {
            setTitle(TITLE_APPBAR_EDIT_ALUNO);
            aluno = (Aluno) dados.getSerializableExtra(CHAVE_ALUNO);
            preencheCampos();
        } else {
            setTitle(TITLE_APPBAR_NEW_ALUNO);
            aluno = new Aluno();
        }
    }

    private void preencheCampos() {
        campoNome.setText(aluno.getNome());
        campoTelefone.setText(aluno.getTelefone());
        campoEmail.setText(aluno.getEmail());
    }

    private void configBtnSalvar() {
        Button btnSalvar = findViewById(R.id.btnSalvarAluno);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finalizarForm();
            }
        });
    }

    private void finalizarForm() {
        preencheAluno();
        if (aluno.validateId()) {
            dao.editar(aluno);
        } else {
            dao.salvar(aluno);
        }
        finish();
    }

    private void inicializarCampos() {
        campoNome = findViewById(R.id.editTextNome);
        campoTelefone = findViewById(R.id.editTextTelefone);
        campoEmail = findViewById(R.id.editTextEmail);
    }

    private void preencheAluno() {
        String nome = campoNome.getText().toString();
        String telefone = campoTelefone.getText().toString();
        String email = campoEmail.getText().toString();
        aluno.setNome(nome);
        aluno.setTelefone(telefone);
        aluno.setEmail(email);
    }
}