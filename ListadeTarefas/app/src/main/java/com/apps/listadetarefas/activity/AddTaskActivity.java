package com.apps.listadetarefas.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.apps.listadetarefas.R;
import com.apps.listadetarefas.helper.TaskDAO;
import com.apps.listadetarefas.model.Task;
import com.google.android.material.textfield.TextInputEditText;

public class AddTaskActivity extends AppCompatActivity {

    private TextInputEditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        editText = findViewById(R.id.editTask);
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
                Task task = new Task();
                task.setNameTask("Ir ao mercado");
                taskDAO.salvar(task);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}