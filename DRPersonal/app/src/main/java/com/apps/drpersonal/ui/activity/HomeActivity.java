package com.apps.drpersonal.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.apps.drpersonal.R;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void goToMeusTreinos(View view){
        startActivity(new Intent(this, MeusTreinosActivity.class));
    }

    public void goToHistorico(View view){
        startActivity(new Intent(this, HistoricoActivity.class));
    }

    public void goToAvaliacao(View view){
        startActivity(new Intent(this, AvaliacoesActivity.class));
    }
}