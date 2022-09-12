package com.apps.preferenciasusuario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText nome;
    private TextView textSalvo;
    private Button btnSalvar;
    private static final String ARQUIVO_PREFERENCIA = "Preferências do Usuário";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nome = findViewById(R.id.editNome);
        textSalvo = findViewById(R.id.textSalvo);
        btnSalvar = findViewById(R.id.btnSalvar);

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences preferences = getSharedPreferences(ARQUIVO_PREFERENCIA,0);
                SharedPreferences.Editor editor = preferences.edit();

                if(nome.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"Preencha o campo nome",
                            Toast.LENGTH_LONG).show();
                }else{
                    String campoNome = nome.getText().toString();
                    editor.putString("Nome: ",campoNome);
                    editor.commit();
                    textSalvo.setText("Olá, "+ campoNome + "!");
                }
            }
        });
        //Recuperar dados salvos
        SharedPreferences preferences = getSharedPreferences(ARQUIVO_PREFERENCIA,0);
        if(preferences.contains("Nome: ")){
            String nome = preferences.getString("Nome: ","Usuário não definido!!!");
            textSalvo.setText("Olá, "+nome+"!");
        }else {
            textSalvo.setText("Usuário não definido!!!");
        }
    }

}