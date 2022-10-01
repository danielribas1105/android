package com.apps.drpersonal.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.apps.drpersonal.R;

public class LoginActivity extends AppCompatActivity {

    private EditText campoEmail, campoSenha;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        campoEmail = findViewById(R.id.textEmailLog);
        campoSenha = findViewById(R.id.textSenhaLog);
        //btnLogin = findViewById(R.id.btnLog);

    }

    public void realizarLogin(View view){

        startActivity(new Intent(this, HomeActivity.class));
    }
}