package com.apps.agendaalura.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.apps.agendaalura.R;
import com.apps.agendaalura.dao.AlunoDao;
import com.apps.agendaalura.model.Aluno;

public class FormularioAlunoActivity extends AppCompatActivity {

    public static final String TITLE_APPBAR = "Novo Aluno";
    private EditText campoNome, campoTelefone, campoEmail;
    private final AlunoDao dao = new AlunoDao();
    private Aluno aluno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_aluno);
        setTitle(TITLE_APPBAR);
        inicializarCampos();
        configBtnSalvar();

        Intent dados = getIntent();
        if(dados.hasExtra("aluno")){
            aluno = (Aluno) dados.getSerializableExtra("aluno");
            campoNome.setText(aluno.getNome());
            campoTelefone.setText(aluno.getTelefone());
            campoEmail.setText(aluno.getEmail());
        }else {
            aluno = new Aluno();
        }
    }

    private void configBtnSalvar() {
        Button btnSalvar = findViewById(R.id.btnSalvarAluno);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preencheAluno();
                if(aluno.validateId()){
                    dao.editar(aluno);
                }else {
                    dao.salvar(aluno);
                }
                finish();
            }
        });
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