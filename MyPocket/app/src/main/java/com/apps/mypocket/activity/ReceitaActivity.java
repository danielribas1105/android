package com.apps.mypocket.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.apps.mypocket.R;
import com.apps.mypocket.helper.DateCustom;
import com.google.android.material.textfield.TextInputEditText;

public class ReceitaActivity extends AppCompatActivity {

    private TextInputEditText campoData, campoCat, campoDesc;
    private EditText campoValor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receita);

        campoValor = findViewById(R.id.valorReceita);
        campoData = findViewById(R.id.dataReceita);
        campoCat = findViewById(R.id.catReceita);
        campoDesc = findViewById(R.id.descReceita);

        campoData.setText(DateCustom.dataAtual());
    }
}