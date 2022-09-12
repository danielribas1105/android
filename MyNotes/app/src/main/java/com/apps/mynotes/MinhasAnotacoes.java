package com.apps.mynotes;

import android.content.Context;
import android.content.SharedPreferences;

public class MinhasAnotacoes {

    //private TextInputEditText myNotes;
    private final String FILE_NOTES = "Minhas Anotações";
    private final String CHAVE_NOME = "nome";
    private Context context;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public MinhasAnotacoes(Context c) {
        this.context = c;
        preferences = context.getSharedPreferences(FILE_NOTES, 0);
        editor = preferences.edit();
    }

    public void salvarNotas(String nota){
        editor.putString(CHAVE_NOME,nota);
        editor.commit();
    }
    public String recuperarNotas(){
        return preferences.getString(CHAVE_NOME,"");
    }

}
