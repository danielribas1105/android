package com.apps.drpersonalmanager.dao;

import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.CHAVE_DB_ALUNOS;
import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.CHAVE_DB_IDPERSONAL;

import com.apps.drpersonalmanager.config.ConfigFirebase;
import com.apps.drpersonalmanager.helper.Base64Custom;
import com.apps.drpersonalmanager.model.Aluno;
import com.google.firebase.database.DatabaseReference;

public class AlunoDao {

    private DatabaseReference reference = ConfigFirebase.getFirebaseDatabase();

    public void salvarNovoAluno(Aluno aluno, String emailAluno){
        reference.child(CHAVE_DB_ALUNOS).child(CHAVE_DB_IDPERSONAL)
                .child(Base64Custom.codeToBase64(emailAluno)).setValue(aluno);
    }
}
