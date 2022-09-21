package com.apps.mypocket.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.apps.mypocket.R;
import com.apps.mypocket.helper.DateCustom;
import com.google.android.material.textfield.TextInputEditText;

public class DespesasActivity extends AppCompatActivity {

    private TextInputEditText campoData, campoCat, campoDesc;
    private EditText campoValor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_despesas);

        campoValor = findViewById(R.id.valorDespesa);
        campoData = findViewById(R.id.dataDespesa);
        campoCat = findViewById(R.id.catDespesa);
        campoDesc = findViewById(R.id.descDespesa);

        campoData.setText(DateCustom.dataAtual());
    }
}