package com.apps.whatsup.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.apps.whatsup.R;
import com.apps.whatsup.config.ConfigFirebase;
import com.apps.whatsup.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText textLogEmail, textLogSenha;
    private Button btnLogin;
    private User user;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textLogEmail = findViewById(R.id.editLogEmail);
        textLogSenha = findViewById(R.id.editLogSenha);
        btnLogin = findViewById(R.id.btnLogar);

        validateUser();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = textLogEmail.getText().toString();
                String senha = textLogSenha.getText().toString();
                if(!email.isEmpty()){
                    if(!senha.isEmpty()){
                        user = new User();
                        user.setEmail(email);
                        user.setSenha(senha);
                        validarUser();
                    }else {
                        Toast.makeText(LoginActivity.this,"Digite sua senha!",
                                Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(LoginActivity.this, "Digite seu e-mail!",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void validarUser() {
        auth = ConfigFirebase.getFirebaseAutenticacao();
        auth.signInWithEmailAndPassword(user.getEmail(),user.getSenha())
                .addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            goToHome();
                        }else{
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

    public void validateUser(){
        auth = ConfigFirebase.getFirebaseAutenticacao();
        if(auth.getCurrentUser() != null){
            startActivity(new Intent(this, HomeActivity.class));
        }
    }

    public void goToRegister(View view){
        startActivity(new Intent(this, RegisterActivity.class));
    }

    private void goToHome() {
        startActivity(new Intent(LoginActivity.this,HomeActivity.class));
        finish();
    }
}