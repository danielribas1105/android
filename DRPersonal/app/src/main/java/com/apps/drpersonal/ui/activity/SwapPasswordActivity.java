package com.apps.drpersonal.ui.activity;

import static com.apps.drpersonal.ui.activity.ConstantesActivities.CHAVE_DB_ALUNOS;
import static com.apps.drpersonal.ui.activity.ConstantesActivities.CHAVE_DB_IDPERSONAL;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.apps.drpersonal.R;
import com.apps.drpersonal.config.ConfigFirebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class SwapPasswordActivity extends AppCompatActivity {

    private EditText campoSenhaAtual, campoNovaSenha;
    private Button btnSwapPass;
    private FirebaseAuth auth;
    private DatabaseReference reference = ConfigFirebase.getFirebaseDatabase();
    private DatabaseReference alunoRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swap_password);

        campoSenhaAtual = findViewById(R.id.editTextSenhaAtual);
        campoNovaSenha = findViewById(R.id.editTextNovaSenha);
        btnSwapPass = findViewById(R.id.btnAlterarSenha);

        loadSenhaAtual();

        btnSwapPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void loadSenhaAtual() {
        auth = ConfigFirebase.getFirebaseAutenticacao();
        if(auth.getCurrentUser() != null){
            //alunoRef = reference.child(CHAVE_DB_ALUNOS).child(CHAVE_DB_IDPERSONAL).child("idAluno");
        }
    }
}