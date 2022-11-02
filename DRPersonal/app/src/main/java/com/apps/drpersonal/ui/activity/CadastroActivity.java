package com.apps.drpersonal.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.apps.drpersonal.R;
import com.apps.drpersonal.config.ConfigFirebase;
import com.apps.drpersonal.helper.Base64Custom;
import com.apps.drpersonal.model.Aluno;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.database.DatabaseReference;

public class CadastroActivity extends AppCompatActivity {

    private EditText campoNome, campoEmail, campoSenha, campoAcademia;
    private Button btnCadastro;
    private FirebaseAuth auth;
    private Aluno aluno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        campoNome = findViewById(R.id.textNomeCad);
        campoEmail = findViewById(R.id.textEmailCad);
        campoAcademia = findViewById(R.id.textAcademiaCad);
        campoSenha = findViewById(R.id.textSenhaCad);
        btnCadastro = findViewById(R.id.btnCad);

        btnCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textoNome = campoNome.getText().toString();
                String textoEmail = campoEmail.getText().toString();
                String textoAcademia = campoAcademia.getText().toString();
                String textoSenha = campoSenha.getText().toString();

                if(!textoNome.isEmpty()){
                    if(!textoEmail.isEmpty()){
                        if(!textoSenha.isEmpty()){
                            aluno = new Aluno();
                            aluno.setFotoAluno("Foto");
                            aluno.setNomeAluno(textoNome);
                            aluno.setEmailAluno(textoEmail);
                            aluno.setAcademia(textoAcademia);
                            aluno.setSenhaAluno(textoSenha);
                            createAluno();
                        }else {
                            Toast.makeText(CadastroActivity.this, "Digite uma senha!", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(CadastroActivity.this, "Digite um e-mail!", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(CadastroActivity.this, "Digite o nome!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void createAluno(){
        auth = ConfigFirebase.getFirebaseAutenticacao();
        auth.createUserWithEmailAndPassword(aluno.getEmailAluno(), aluno.getSenhaAluno())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            String idAluno = Base64Custom.codeToBase64(aluno.getEmailAluno());
                            aluno.setIdAluno(idAluno);
                            aluno.salvarAluno();
                            finish();
                            goToHome();
                        }else {
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
                            Toast.makeText(CadastroActivity.this, erroDB,
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public void goToHome(){
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }

}