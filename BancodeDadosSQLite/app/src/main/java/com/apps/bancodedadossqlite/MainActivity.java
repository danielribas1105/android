package com.apps.bancodedadossqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            //Criar Banco de Dados
            SQLiteDatabase bancoDados = openOrCreateDatabase("app",MODE_PRIVATE,null);
            //Criar Tabelas
            bancoDados.execSQL("CREATE TABLE IF NOT EXISTS pessoas (id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " nome VARCHAR,idade INT(3))");
            //Excluir Tabelas
            //bancoDados.execSQL("DROP TABLE pessoas");
            //Inserir Dados em uma Tabela
            //bancoDados.execSQL("INSERT INTO pessoas (nome, idade) VALUES ('Daniel',47)");
            //bancoDados.execSQL("INSERT INTO pessoas (nome, idade) VALUES ('Vitor',12)");
            //bancoDados.execSQL("INSERT INTO pessoas (nome, idade) VALUES ('Leticia',10)");
            //Recuperar Dados
            Cursor cursor = bancoDados.rawQuery("SELECT * FROM pessoas",null);
            //Recuperando o indice da coluna
            int indexId = cursor.getColumnIndex("id");
            int indexNome = cursor.getColumnIndex("nome");
            int indexIdade = cursor.getColumnIndex("idade");
            cursor.moveToFirst();
            while (cursor != null) {
                String id = cursor.getString(indexId);
                String nome = cursor.getString(indexNome);
                String idade = cursor.getString(indexIdade);
                Log.i("Resultado - Id: ",id+" / Nome: "+nome+" / Idade: "+idade);
                cursor.moveToNext();
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}