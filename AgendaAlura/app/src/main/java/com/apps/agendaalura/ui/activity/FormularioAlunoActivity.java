package com.apps.agendaalura.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.apps.agendaalura.R;
import com.apps.agendaalura.dao.AlunoDao;
import com.apps.agendaalura.model.Aluno;

public class FormularioAlunoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_aluno);

        final AlunoDao dao = new AlunoDao();

        setTitle("Adicionar Aluno");
        final EditText campoNome, campoTelefone, campoEmail;
        Button btnSalvar;

        campoNome = findViewById(R.id.editTextNome);
        campoTelefone = findViewById(R.id.editTextTelefone);
        campoEmail = findViewById(R.id.editTextEmail);
        btnSalvar = findViewById(R.id.btnSalvarAluno);

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nome = campoNome.getText().toString();
                String telefone = campoTelefone.getText().toString();
                String email = campoEmail.getText().toString();
                Aluno aluno = new Aluno(nome, telefone, email);
                dao.salvar(aluno);
                startActivity(new Intent(FormularioAlunoActivity.this,
                        ListaAlunosActivity.class));
            }
        });

    }
}