package com.apps.drpersonalmanager.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.apps.drpersonalmanager.R;
import com.apps.drpersonalmanager.config.ConfigFirebase;
import com.apps.drpersonalmanager.model.Personal;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class MainActivity extends AppCompatActivity {

    private EditText campoEmail, campoSenha;
    private Button btnLogin;
    private Personal personal;
    private FirebaseAuth auth = ConfigFirebase.getFirebaseAutenticacao();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        campoEmail = findViewById(R.id.editTextEmail);
        campoSenha = findViewById(R.id.editTextSenha);
        btnLogin = findViewById(R.id.btnEntrar);

        validateUser();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textoEmail = campoEmail.getText().toString();
                String textoSenha = campoSenha.getText().toString();
                if(!textoEmail.isEmpty()){
                    if(!textoSenha.isEmpty()){
                        personal = new Personal();
                        personal.setEmailPersonal(textoEmail);
                        personal.setSenhaPersonal(textoSenha);
                        checkLogin();
                    }else{
                        Toast.makeText(MainActivity.this,"Informe sua senha!",Toast.LENGTH_SHORT);
                    }
                }else{
                    Toast.makeText(MainActivity.this,"Informe seu e-mail!", Toast.LENGTH_SHORT);
                }
            }
        });

    }

    private void validateUser() {
        if(auth.getCurrentUser() != null){
            goToHome();
        }
    }

    public void checkLogin(){
        auth.signInWithEmailAndPassword(personal.getEmailPersonal(),personal.getSenhaPersonal())
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
                                erroDB = "Cadastro inválido. Verifique e-mail e senha!";
                            } catch (FirebaseAuthInvalidUserException e) {
                                erroDB = "Usuário não cadastrado!";
                            } catch (Exception e) {
                                erroDB = "Erro ao fazer login: " + e.getMessage();
                                e.printStackTrace();
                            }
                            Toast.makeText(MainActivity.this, erroDB, Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }

    public void goToHome(){
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }
}