package com.apps.drpersonal.model;

import static com.apps.drpersonal.ui.activity.ConstantesActivities.CHAVE_DB_ALUNOS;
import static com.apps.drpersonal.ui.activity.ConstantesActivities.CHAVE_DB_IDPERSONAL;

import com.apps.drpersonal.config.ConfigFirebase;
import com.apps.drpersonal.helper.Base64Custom;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

public class Aluno {

    private String idAluno;
    private String nomeAluno;
    private String emailAluno;
    private String senhaAluno;
    private String dataInicio;
    private String academia;
    private FirebaseAuth auth = ConfigFirebase.getFirebaseAutenticacao();

    public Aluno() {}

    public void salvarAluno(){
        DatabaseReference reference = ConfigFirebase.getFirebaseDatabase();
        reference.child(CHAVE_DB_ALUNOS).child(CHAVE_DB_IDPERSONAL).child(this.idAluno).setValue(this);
    }

    public void salvarPerfilAluno(){
        idAluno = Base64Custom.codeToBase64(auth.getCurrentUser().getEmail());
        DatabaseReference referencePerfil = ConfigFirebase.getFirebaseDatabase();
        referencePerfil.child(CHAVE_DB_ALUNOS).child(CHAVE_DB_IDPERSONAL).child(idAluno).setValue(this);
    }

    @Exclude
    public String getIdAluno() {
        return idAluno;
    }

    public void setIdAluno(String idAluno) {
        this.idAluno = idAluno;
    }

    public String getNomeAluno() { return nomeAluno;}

    public void setNomeAluno(String nomeAluno) {
        this.nomeAluno = nomeAluno;
    }

    public String getEmailAluno() {
        return emailAluno;
    }

    public void setEmailAluno(String emailAluno) {
        this.emailAluno = emailAluno;
    }

    //@Exclude
    public String getSenhaAluno() {
        return senhaAluno;
    }

    public void setSenhaAluno(String senhaAluno) {
        this.senhaAluno = senhaAluno;
    }

    public String getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(String dataInicio) {
        this.dataInicio = dataInicio;
    }

    public String getAcademia() {
        return academia;
    }

    public void setAcademia(String academia) {
        this.academia = academia;
    }
}
