package com.apps.drpersonal.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.apps.drpersonal.R;

public class CadastroActivity extends AppCompatActivity {

    private EditText campoNome, campoEmail, campoSenha;
    private Button btnCadastro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        campoNome = findViewById(R.id.textNomeCad);
        campoEmail = findViewById(R.id.textEmailCad);
        campoSenha = findViewById(R.id.textSenhaCad);
        //btnCadastro = findViewById(R.id.btnCad);
    }

    public void realizarCadastro(View view){
        startActivity(new Intent(this, HomeActivity.class));
    }
}