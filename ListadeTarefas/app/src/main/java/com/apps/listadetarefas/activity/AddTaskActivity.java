package com.apps.listadetarefas.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.apps.listadetarefas.R;
import com.apps.listadetarefas.helper.TaskDAO;
import com.apps.listadetarefas.model.Task;
import com.google.android.material.textfield.TextInputEditText;

public class AddTaskActivity extends AppCompatActivity {

    private TextInputEditText editText;
    private Task taskActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        editText = findViewById(R.id.editTask);
        //Recuperar tarefa, caso seja edição
        taskActual = (Task) getIntent().getSerializableExtra("taskSelected");
        //Configurar tarefa na caixa de texto
        if (taskActual != null) {
            editText.setText(taskActual.getNameTask());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_salvar:
                TaskDAO taskDAO = new TaskDAO(getApplicationContext());
                if (taskActual != null) { //editar
                    String nameTask = editText.getText().toString();
                    if (!nameTask.isEmpty()) {
                        Task task = new Task();
                        task.setNameTask(nameTask);
                        task.setId(taskActual.getId());
                        //atualizar o banco de dados
                        if (taskDAO.atualizar(task)) {
                            finish();
                            Toast.makeText(getApplicationContext(), "Tarefa atualizada!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Erro ao atualizar tarefa!", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else { //salvar
                    String nameTask = editText.getText().toString();
                    if (!nameTask.isEmpty()) {
                        Task task = new Task();
                        task.setNameTask(nameTask);
                        if (taskDAO.salvar(task)) {
                            finish();
                            Toast.makeText(getApplicationContext(), "Tarefa incluída!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Erro ao incluir tarefa!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Preencher a tarefa!", Toast.LENGTH_SHORT).show();
                    }
                }

                break;
        }
        return super.onOptionsItemSelected(item);
    }
}