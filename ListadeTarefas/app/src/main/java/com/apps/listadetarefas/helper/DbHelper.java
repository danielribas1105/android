package com.apps.listadetarefas.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    private static int VERSION = 1;
    private static String DB_NAME = "DB_Tasks";
    private static String TAB_TASKS = "tasks";

    public DbHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS "+TAB_TASKS+
                " (id INTEGER PRIMARY KEY AUTOINCREMENT, nameTask TEXT NOT NULL);";

        try {
            db.execSQL(sql);
            Log.i("INFO DB","Sucesso ao criar tabela!");
        }catch (Exception e){
            Log.i("INFO DB","Erro ao criar tabela!");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
