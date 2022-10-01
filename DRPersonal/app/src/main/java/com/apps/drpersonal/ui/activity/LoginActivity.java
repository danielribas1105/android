package com.apps.drpersonal.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.apps.drpersonal.R;
import com.apps.drpersonal.config.ConfigFirebase;
import com.apps.drpersonal.model.Aluno;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class LoginActivity extends AppCompatActivity {

    private EditText campoEmail, campoSenha;
    private Button btnLogin;
    private FirebaseAuth auth;
    private Aluno aluno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        campoEmail = findViewById(R.id.textEmailLog);
        campoSenha = findViewById(R.id.textSenhaLog);
        btnLogin = findViewById(R.id.btnLog);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textoEmail = campoEmail.getText().toString();
                String textoSenha = campoSenha.getText().toString();

                if(!textoEmail.isEmpty()){
                    if(!textoSenha.isEmpty()){
                        aluno = new Aluno();
                        aluno.setEmailAluno(textoEmail);
                        aluno.setSenhaAluno(textoSenha);
                        validateLogin();
                    }else {
                        Toast.makeText(LoginActivity.this, "Digite uma senha!", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(LoginActivity.this, "Digite um e-mail!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void validateLogin(){
        auth = ConfigFirebase.getFirebaseAutenticacao();
        auth.signInWithEmailAndPassword(aluno.getEmailAluno(), aluno.getSenhaAluno())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            goToHome();
                        }else {
                            String erroDB;
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthInvalidCredentialsException e) {
                                erroDB = "Cadastro inválido. Verifique o e-mail e a senha!";
                            } catch (FirebaseAuthInvalidUserException e) {
                                erroDB = "Usuário não cadastrado!";
                            } catch (Exception e) {
                                erroDB = "Erro ao fazer login: " + e.getMessage();
                                e.printStackTrace();
                            }
                            Toast.makeText(LoginActivity.this, erroDB, Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public void goToHome(){
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }

}