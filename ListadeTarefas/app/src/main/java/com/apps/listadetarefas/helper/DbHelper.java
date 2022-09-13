package com.apps.listadetarefas.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {

    private static final int VERSION = 2;
    private static final String DB_NAME = "DB_TASKS";
    private static final String TAB_TASKS = "tasks";

    public DbHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TAB_TASKS +
                " (id INTEGER PRIMARY KEY AUTOINCREMENT, nameTask TEXT NOT NULL);";
        try {
            db.execSQL(sql);
            Log.i("INFO DB", "Sucesso ao criar tabela!");
        } catch (Exception e) {
            Log.i("INFO DB", "Erro ao criar tabela!"+e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String sql = "DROP TABLE IF EXISTS " + TAB_TASKS + ";";
        try {
            db.execSQL(sql);
            onCreate(db);
            Log.i("INFO DB", "Sucesso ao atualizar o app!");
        } catch (Exception e) {
            Log.i("INFO DB", "Erro ao atualizar o app!"+e.getMessage());
        }
    }
}
