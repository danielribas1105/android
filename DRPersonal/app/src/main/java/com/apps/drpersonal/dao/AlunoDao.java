package com.apps.drpersonal.dao;

import static com.apps.drpersonal.ui.activity.ConstantesActivities.CHAVE_DB_ACADEMIA_ALUNO;
import static com.apps.drpersonal.ui.activity.ConstantesActivities.CHAVE_DB_ALUNOS;
import static com.apps.drpersonal.ui.activity.ConstantesActivities.CHAVE_DB_IDPERSONAL;
import static com.apps.drpersonal.ui.activity.ConstantesActivities.CHAVE_DB_IMG_ALUNO;
import static com.apps.drpersonal.ui.activity.ConstantesActivities.CHAVE_DB_NIVER_ALUNO;
import static com.apps.drpersonal.ui.activity.ConstantesActivities.CHAVE_DB_NOME_ALUNO;
import static com.apps.drpersonal.ui.activity.ConstantesActivities.CHAVE_DB_SENHA_ALUNO;

import com.apps.drpersonal.config.ConfigFirebase;
import com.apps.drpersonal.helper.UsersFirebase;
import com.google.firebase.database.DatabaseReference;

public class AlunoDao {

    private DatabaseReference reference = ConfigFirebase.getFirebaseDatabase();
    private String idAluno = UsersFirebase.getIdUserAuth();

    public void salvarAluno(){
        DatabaseReference reference = ConfigFirebase.getFirebaseDatabase();
        reference.child(CHAVE_DB_ALUNOS).child(CHAVE_DB_IDPERSONAL).child(this.idAluno).setValue(this);
    }

    public void salvarPerfilAluno(String nome, String academia, String niver, String idImg){
        reference.child(CHAVE_DB_ALUNOS).child(CHAVE_DB_IDPERSONAL).child(idAluno)
                .child(CHAVE_DB_NOME_ALUNO).setValue(nome);
        reference.child(CHAVE_DB_ALUNOS).child(CHAVE_DB_IDPERSONAL).child(idAluno)
                .child(CHAVE_DB_ACADEMIA_ALUNO).setValue(academia);
        reference.child(CHAVE_DB_ALUNOS).child(CHAVE_DB_IDPERSONAL).child(idAluno)
                .child(CHAVE_DB_NIVER_ALUNO).setValue(niver);
        reference.child(CHAVE_DB_ALUNOS).child(CHAVE_DB_IDPERSONAL).child(idAluno)
                .child(CHAVE_DB_IMG_ALUNO).setValue(idImg);
    }

    public void salvarNovaSenhaAluno(String novaSenha){
        reference.child(CHAVE_DB_ALUNOS).child(CHAVE_DB_IDPERSONAL).child(idAluno)
                .child(CHAVE_DB_SENHA_ALUNO).setValue(novaSenha);
    }

}
