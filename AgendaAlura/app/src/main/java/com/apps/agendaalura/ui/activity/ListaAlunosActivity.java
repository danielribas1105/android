package com.apps.agendaalura.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.apps.agendaalura.R;
import com.apps.agendaalura.dao.AlunoDao;
import com.apps.agendaalura.model.Aluno;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ListaAlunosActivity extends AppCompatActivity {

    public static final String TITLE_APPBAR = "Lista de Alunos";
    private final AlunoDao dao = new AlunoDao();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);
        setTitle(TITLE_APPBAR);
        configFabAddAluno();
        dao.salvar(new Aluno("Daniel","123456","daniel@gmail"));
        dao.salvar(new Aluno("Vitor","123456","vitor@gmail"));
    }

    private void configFabAddAluno() {
        FloatingActionButton fabAdd = findViewById(R.id.fabAddAlunos);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToFormNewAluno();
            }
        });
    }

    private void goToFormNewAluno() {
        startActivity(new Intent(this, FormularioAlunoActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        configListAlunos();
    }

    private void configListAlunos() {
        ListView listAlunos = findViewById(R.id.listViewAlunos);
        final List<Aluno> todosAlunos = dao.allAlunos();
        listAlunos.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, todosAlunos));
        listAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Aluno selectAluno = todosAlunos.get(position);
                Intent goToForm = new Intent(ListaAlunosActivity.this, FormularioAlunoActivity.class);
                goToForm.putExtra("aluno",selectAluno);
                startActivity(goToForm);
            }
        });

    }
}
