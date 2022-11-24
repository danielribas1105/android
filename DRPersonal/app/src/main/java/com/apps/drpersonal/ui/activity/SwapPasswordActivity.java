package com.apps.drpersonal.ui.activity;

import static com.apps.drpersonal.ui.activity.ConstantesActivities.CHAVE_DB_ALUNOS;
import static com.apps.drpersonal.ui.activity.ConstantesActivities.CHAVE_DB_IDPERSONAL;
import static com.apps.drpersonal.ui.activity.ConstantesActivities.STR_EMAIL;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.apps.drpersonal.R;
import com.apps.drpersonal.config.ConfigFirebase;
import com.apps.drpersonal.helper.Base64Custom;
import com.apps.drpersonal.model.Aluno;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class SwapPasswordActivity extends AppCompatActivity {

    private EditText campoSenhaAtual, campoNovaSenha;
    private Button btnSwapPass;
    private String idAluno, emailAluno, senhaAluno;
    private FirebaseAuth auth = ConfigFirebase.getFirebaseAutenticacao();
    private DatabaseReference reference = ConfigFirebase.getFirebaseDatabase();
    private DatabaseReference alunoRef;
    private ValueEventListener valueEventListenerOldPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swap_password);

        campoSenhaAtual = findViewById(R.id.editTextSenhaAtual);
        campoNovaSenha = findViewById(R.id.editTextNovaSenha);
        btnSwapPass = findViewById(R.id.btnAlterarSenha);

        if(auth.getCurrentUser() != null){
            idAluno = auth.getUid();
            emailAluno = auth.getCurrentUser().getEmail();
        }

        Log.i("dados",idAluno);
        Log.i("dados",emailAluno);
        loadSenhaAtual(emailAluno);

        btnSwapPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //salvarNovaSenha(emailAluno);
            }
        });
    }

    private void salvarNovaSenha(String email) {
        if(auth.getCurrentUser() != null) {
            alunoRef = reference.child(CHAVE_DB_ALUNOS).child(CHAVE_DB_IDPERSONAL)
                    .child(Base64Custom.codeToBase64(email));
            Aluno aluno = new Aluno();
            aluno.setSenhaAluno(campoNovaSenha.getText().toString());
            aluno.salvarPerfilAluno();
        }
    }

    private void loadSenhaAtual(String email) {
        if(auth.getCurrentUser() != null){
            alunoRef = reference.child(CHAVE_DB_ALUNOS).child(CHAVE_DB_IDPERSONAL)
                    .child(Base64Custom.codeToBase64(email));
            valueEventListenerOldPass = alunoRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Aluno aluno = snapshot.getValue(Aluno.class);
                    campoSenhaAtual.setText(aluno.getSenhaAluno());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
}