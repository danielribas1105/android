package com.apps.listadetarefas.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.apps.listadetarefas.model.Task;

import java.util.List;

public class TaskDAO implements ITaskDAO {

    private SQLiteDatabase write;
    private SQLiteDatabase read;

    public TaskDAO(Context context) {
        DbHelper db = new DbHelper(context);
        write = db.getWritableDatabase();
        read = db.getReadableDatabase();
    }

    @Override
    public boolean salvar(Task task) {
        ContentValues cv = new ContentValues();
        cv.put("nameTask", task.getNameTask());
        try {
            write.insert("tasks", null, cv);
            Log.i("INFO DB", "Tarefa salva com sucesso!");
        } catch (Exception e) {
            Log.i("INFO DB", "Erro ao salvar tarefa!" + e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean atualizar(Task task) {
        return false;
    }

    @Override
    public boolean deletar(Task task) {
        return false;
    }

    @Override
    public List<Task> listar() {
        return null;
    }
}
