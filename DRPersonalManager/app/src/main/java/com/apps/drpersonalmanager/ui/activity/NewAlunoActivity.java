package com.apps.drpersonalmanager.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.apps.drpersonalmanager.R;
import com.apps.drpersonalmanager.config.ConfigFirebase;
import com.apps.drpersonalmanager.dao.AlunoDao;
import com.apps.drpersonalmanager.model.Aluno;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class NewAlunoActivity extends AppCompatActivity {

    private EditText nomeNewAluno, emailNewAluno, gymNewAluno;
    private Button btnSaveNewAluno;
    private Aluno alunoNew;
    private AlunoDao alunoDao = new AlunoDao();
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_aluno);

        nomeNewAluno = findViewById(R.id.editNewNomeAluno);
        emailNewAluno = findViewById(R.id.editNewEmailAluno);
        gymNewAluno = findViewById(R.id.editNewGymAluno);
        btnSaveNewAluno = findViewById(R.id.btnSalvarNovoAluno);

        btnSaveNewAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textoNome = nomeNewAluno.getText().toString();
                String textoEmail = emailNewAluno.getText().toString();
                String textoAcademia = gymNewAluno.getText().toString();
                if(!textoNome.isEmpty()){
                    if(!textoEmail.isEmpty()){
                        if(!textoAcademia.isEmpty()){
                            createAluno();
                            alunoNew = new Aluno();
                            alunoNew.setNomeAluno(nomeNewAluno.getText().toString());
                            alunoNew.setEmailAluno(emailNewAluno.getText().toString());
                            alunoNew.setAcademia(gymNewAluno.getText().toString());
                            alunoNew.setSenhaAluno("123456");
                            alunoDao.salvarNovoAluno(alunoNew,emailNewAluno.getText().toString());
                            Toast.makeText(NewAlunoActivity.this,
                                    "Aluno(a) "+ nomeNewAluno.getText().toString() + " salvo(a) com sucesso!",
                                    Toast.LENGTH_SHORT).show();
                            finish();
                        }else {
                            Toast.makeText(NewAlunoActivity.this, "Informe a academia!", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(NewAlunoActivity.this, "Digite um e-mail!", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(NewAlunoActivity.this, "Digite o nome!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void createAluno(){
        auth = ConfigFirebase.getFirebaseAutenticacao();
        auth.createUserWithEmailAndPassword(emailNewAluno.getText().toString(), "123456")
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(NewAlunoActivity.this, "Novo usuário autenticado!",
                                    Toast.LENGTH_LONG).show();
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
                            Toast.makeText(NewAlunoActivity.this, erroDB,
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

}