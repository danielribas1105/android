package com.apps.agendaalura.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.apps.agendaalura.R;
import com.apps.agendaalura.dao.AlunoDao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListaAlunosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);

        AlunoDao dao = new AlunoDao();
        setTitle("Lista de Alunos");
        //List<String> alunos = new ArrayList<>(Arrays.asList("Daniel","Vitor","Leticia"));
        ListView listAlunos = findViewById(R.id.listViewAlunos);

        listAlunos.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,dao.allAlunos()));
    }

    public void goToFormAluno(View view){
        startActivity(new Intent(this, FormularioAlunoActivity.class));
    }
}
