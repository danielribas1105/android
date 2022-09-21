package com.apps.drpersonal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.apps.drpersonal.ui.activity.CadastroActivity;
import com.apps.drpersonal.ui.activity.LoginActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void openRegister(View view){
        startActivity(new Intent(this, CadastroActivity.class));
    }
    public void openLogin(View view){
        startActivity(new Intent(this, LoginActivity.class));
    }
}