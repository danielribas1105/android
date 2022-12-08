package com.apps.drpersonalmanager.ui.activity;

import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.CHAVE_DB_PERSONAL;

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
import com.apps.drpersonalmanager.helper.Base64Custom;
import com.apps.drpersonalmanager.model.Personal;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.database.DatabaseReference;

public class RegistrationActivity extends AppCompatActivity {

    private EditText fieldName, fieldEmail, fieldPass;
    private Button btnNewRegister;
    private Personal personal;
    private DatabaseReference database = ConfigFirebase.getFirebaseDatabase();
    private DatabaseReference personalRef;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        setTitle("Cadastrar Personal");

        fieldName = findViewById(R.id.editTextNewPersonal);
        fieldEmail = findViewById(R.id.editTextNewEmailPersonal);
        fieldPass = findViewById(R.id.editTextNewPassPersonal);
        btnNewRegister = findViewById(R.id.btnRegisterNewPersonal);

        btnNewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nome = fieldName.getText().toString();
                String email = fieldEmail.getText().toString();
                String senha = fieldPass.getText().toString();
                if(!nome.isEmpty()){
                    if(!email.isEmpty()){
                        if(!senha.isEmpty() && senha.length()>=6){
                            personal = new Personal();
                            personal.setNomePersonal(nome);
                            personal.setEmailPersonal(email);
                            personal.setSenhaPersonal(senha);
                            registerNewPersonal();
                        }else{
                            Toast.makeText(RegistrationActivity.this, "Informe uma senha válida!", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(RegistrationActivity.this, "Informe um e-mail!", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(RegistrationActivity.this, "Informe um nome de usuário!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void registerNewPersonal() {
        auth = ConfigFirebase.getFirebaseAutenticacao();
        auth.createUserWithEmailAndPassword(personal.getEmailPersonal(), personal.getSenhaPersonal())
                .addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String idUsuario = Base64Custom.codeToBase64(personal.getEmailPersonal());
                            personal.setIdPersonal(idUsuario);
                            personal.salvarNewPersonal();
                            finish();
                            goToHome();
                        } else {
                            String excecao;
                            try {
                                throw task.getException();
                            }catch (FirebaseAuthWeakPasswordException e){
                                excecao = "A senha deve conter no mínimo 6 caracteres!";
                            }catch (FirebaseAuthInvalidCredentialsException e){
                                excecao = "O e-mail cadastrado é inválido!";
                            }catch (FirebaseAuthUserCollisionException e){
                                excecao = "O e-mail digitado já está cadastrado!";
                            }catch (Exception e){
                                excecao = "Erro ao cadastrar usuário: "+e.getMessage();
                                e.printStackTrace();
                            }
                            Toast.makeText(RegistrationActivity.this, excecao,
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void goToHome() {
        startActivity(new Intent(RegistrationActivity.this, HomeActivity.class));
    }

}