package com.apps.drpersonalmanager.dao;

import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.CHAVE_DB_ALUNOS;
import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.CHAVE_DB_IDPERSONAL;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.apps.drpersonalmanager.config.ConfigFirebase;
import com.apps.drpersonalmanager.helper.Base64Custom;
import com.apps.drpersonalmanager.model.Aluno;
import com.apps.drpersonalmanager.ui.activity.NewAlunoActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.database.DatabaseReference;

import java.util.concurrent.Executor;

public class AlunoDao {

    private DatabaseReference reference = ConfigFirebase.getFirebaseDatabase();

    public void salvarNovoAluno(Aluno aluno, String emailAluno){
        reference.child(CHAVE_DB_ALUNOS).child(CHAVE_DB_IDPERSONAL)
                .child(Base64Custom.codeToBase64(emailAluno)).setValue(aluno);
    }
}
