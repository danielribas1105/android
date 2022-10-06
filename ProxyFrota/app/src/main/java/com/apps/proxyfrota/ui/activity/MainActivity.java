package com.apps.proxyfrota.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.apps.proxyfrota.R;

public class MainActivity extends AppCompatActivity {

    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //btnRegister = findViewById(R.id.btnRegister);
    }

    public void goToRegister(View view){
        startActivity(new Intent(this, RegisterActivity.class));
    }

    public void goToLogin(View view){
        startActivity(new Intent(this, LoginActivity.class));
    }
}