package com.apps.drpersonal.model;

import com.apps.drpersonal.config.ConfigFirebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

public class Aluno {

    private String idAluno;
    private String nomeAluno;
    private String emailAluno;
    private String senhaAluno;

    public Aluno() {
    }

    public void salvarAluno(){
        DatabaseReference reference = ConfigFirebase.getFirebaseDatabase();
        reference.child("alunos").child(this.idAluno).setValue(this);
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

    @Exclude
    public String getSenhaAluno() {
        return senhaAluno;
    }

    public void setSenhaAluno(String senhaAluno) {
        this.senhaAluno = senhaAluno;
    }

}