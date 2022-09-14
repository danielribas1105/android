package com.apps.listadetarefas.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.listadetarefas.R;
import com.apps.listadetarefas.adapter.TaskAdapter;
import com.apps.listadetarefas.helper.RecyclerItemClickListener;
import com.apps.listadetarefas.helper.TaskDAO;
import com.apps.listadetarefas.model.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TaskAdapter taskAdapter;
    private List<Task> listTask = new ArrayList<>();
    private Task taskSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recycleView);

        //Adicionar evento de clique
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(
                getApplicationContext(), recyclerView,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        //Recuperar tarefa selecionada
                        Task taskSelected = listTask.get(position);
                        //Enviar tarefa selecionada para a próxima activity
                        Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
                        intent.putExtra("taskSelected", taskSelected);
                        startActivity(intent);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        taskSelected = listTask.get(position);
                        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                        dialog.setTitle("Confirmar exclusão");
                        dialog.setMessage("Deseja excluir a tarefa " +
                                taskSelected.getNameTask() + " ?");
                        dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                TaskDAO taskDAO = new TaskDAO(getApplicationContext());
                                if(taskDAO.deletar(taskSelected)){
                                    loadTaskList();
                                    Toast.makeText(MainActivity.this,
                                            "Tarefa excluída!", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(MainActivity.this,
                                            "Erro ao excluir tarefa!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        dialog.setNegativeButton("Não", null);

                        //Exibir dialog
                        dialog.create();
                        dialog.show();
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

    public void loadTaskList() {
        //Listar Tarefas
        TaskDAO taskDAO = new TaskDAO(getApplicationContext());
        listTask = taskDAO.listar();

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