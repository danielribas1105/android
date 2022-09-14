package com.apps.listadetarefas.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.apps.listadetarefas.model.Task;

import java.util.ArrayList;
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
            write.insert(DbHelper.TAB_TASKS, null, cv);
            Log.i("INFO DB", "Tarefa salva com sucesso!");
        } catch (Exception e) {
            Log.i("INFO DB", "Erro ao salvar tarefa!" + e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean atualizar(Task task) {
        ContentValues cv = new ContentValues();
        cv.put("nameTask", task.getNameTask());
        try {
            String[] args = {task.getId().toString()};
            write.update(DbHelper.TAB_TASKS, cv, "id=?", args);
            Log.i("INFO DB", "Tarefa atualizada com sucesso!");
        } catch (Exception e) {
            Log.i("INFO DB", "Erro ao atualizar tarefa!" + e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean deletar (Task task) {

        try {
            String[] args = {task.getId().toString()};
            write.delete(DbHelper.TAB_TASKS,"id=?",args);
            Log.i("INFO DB", "Tarefa exluída com sucesso!");
        }catch (Exception e){
            Log.i("INFO DB", "Erro ao excluir tarefa!" + e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public List<Task> listar () {
        List<Task> taskList = new ArrayList<>();
        String sql = "SELECT * FROM " + DbHelper.TAB_TASKS + " ;";
        Cursor c = read.rawQuery(sql, null);

        while (c.moveToNext()) {
            Task task = new Task();
            Long id = c.getLong(c.getColumnIndexOrThrow("id"));
            String nameTask = c.getString(c.getColumnIndexOrThrow("nameTask"));
            task.setId(id);
            task.setNameTask(nameTask);
            taskList.add(task);
        }
        return taskList;
    }
}
