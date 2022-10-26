package com.apps.whatsup.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.apps.whatsup.R;
import com.apps.whatsup.config.ConfigFirebase;
import com.apps.whatsup.helper.Base64Custom;
import com.apps.whatsup.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.database.DatabaseReference;

public class RegisterActivity extends AppCompatActivity {

    private TextInputEditText campoNome, campoEmail, campoSenha;
    private Button btnCadastro;
    private User user;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        campoNome = findViewById(R.id.editCadNome);
        campoEmail = findViewById(R.id.editCadEmail);
        campoSenha = findViewById(R.id.editCadSenha);
        btnCadastro = findViewById(R.id.btnCadastrar);

        btnCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nome = campoNome.getText().toString();
                String email = campoEmail.getText().toString();
                String senha = campoSenha.getText().toString();
                if(!nome.isEmpty()){
                    if(!email.isEmpty()){
                        if(!senha.isEmpty()){
                            user = new User();
                            user.setNome(nome);
                            user.setEmail(email);
                            user.setSenha(senha);
                            cadastrarUser();
                        }else{
                            Toast.makeText(RegisterActivity.this,"Digite uma senha",Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(RegisterActivity.this,"Digite um e-mail",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(RegisterActivity.this,"Digite um nome!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void cadastrarUser() {
        auth = ConfigFirebase.getFirebaseAutenticacao();
        auth.createUserWithEmailAndPassword(user.getEmail(), user.getSenha())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            String idUser = Base64Custom.codeToBase64(user.getEmail());
                            user.setIdUser(idUser);
                            user.salvarUser();
                            Toast.makeText(RegisterActivity.this,
                                    "Usuário cadastrado com sucesso!",Toast.LENGTH_SHORT).show();
                            finish();
                            goToHome();
                        }else{
                            String erroDB;
                            try {
                                throw task.getException();
                            }catch (FirebaseAuthWeakPasswordException e){
                                erroDB = "A senha deve conter no mínimo 6 caracteres!";
                            }catch (FirebaseAuthInvalidCredentialsException e){
                                erroDB = "O e-mail cadastrado é inválido!";
                            }catch (FirebaseAuthUserCollisionException e){
                                erroDB = "O e-mail digitado já está cadastrado!";
                            }catch (Exception e){
                                erroDB = "Erro ao cadastrar usuário: "+e.getMessage();
                                e.printStackTrace();
                            }
                            Toast.makeText(RegisterActivity.this, erroDB,
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void goToHome() {
        startActivity(new Intent(RegisterActivity.this,HomeActivity.class));
        finish();
    }

}