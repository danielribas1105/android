package com.apps.listadetarefas.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.listadetarefas.R;
import com.apps.listadetarefas.adapter.TaskAdapter;
import com.apps.listadetarefas.helper.DbHelper;
import com.apps.listadetarefas.helper.RecyclerItemClickListener;
import com.apps.listadetarefas.model.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TaskAdapter taskAdapter;
    private List<Task> listTask = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recycleView);

        //DbHelper db = new DbHelper(getApplicationContext());
        //ContentValues cv = new ContentValues();
        //cv.put("nameTask","Estudar Android");
        //db.getWritableDatabase().insert("tasks", null, cv);

        //Adicionar evento de clique
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(
                getApplicationContext(), recyclerView,
                new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.i("clique","Um clique");
            }

            @Override
            public void onLongItemClick(View view, int position) {
                Log.i("clique","Um clique longo");
            }

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        }
        ));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddTaskActivity.class);
                startActivity(intent);
            }
        });
    }

    public void loadTaskList (){
        //Listar Tarefas
        Task task1 = new Task();
        task1.setNameTask("Ir ao mercado");
        listTask.add(task1);

        Task task2 = new Task();
        task2.setNameTask("Estudar Angular");
        listTask.add(task2);

        //Exibir lista de tarefas no recycle view

        //Configurar adapter
        taskAdapter = new TaskAdapter(listTask);
        //Configurar recycle view
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(),
                LinearLayout.VERTICAL));
        recyclerView.setAdapter(taskAdapter);
    }

    @Override
    protected void onStart() {
        loadTaskList();
        super.onStart();
    }

}