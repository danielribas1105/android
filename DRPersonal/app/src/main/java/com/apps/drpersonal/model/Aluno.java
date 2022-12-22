package com.apps.drpersonal.model;

import static com.apps.drpersonal.ui.activity.ConstantesActivities.CHAVE_DB_ACADEMIA_ALUNO;
import static com.apps.drpersonal.ui.activity.ConstantesActivities.CHAVE_DB_ALUNOS;
import static com.apps.drpersonal.ui.activity.ConstantesActivities.CHAVE_DB_EMAIL_ALUNO;
import static com.apps.drpersonal.ui.activity.ConstantesActivities.CHAVE_DB_IDPERSONAL;
import static com.apps.drpersonal.ui.activity.ConstantesActivities.CHAVE_DB_IMG_ALUNO;
import static com.apps.drpersonal.ui.activity.ConstantesActivities.CHAVE_DB_NIVER_ALUNO;
import static com.apps.drpersonal.ui.activity.ConstantesActivities.CHAVE_DB_NOME_ALUNO;
import static com.apps.drpersonal.ui.activity.ConstantesActivities.CHAVE_DB_SENHA_ALUNO;

import com.apps.drpersonal.config.ConfigFirebase;
import com.apps.drpersonal.helper.Base64Custom;
import com.apps.drpersonal.helper.UsersFirebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class Aluno implements Serializable {

    private String idAluno;
    private String idImgAluno;
    private String nomeAluno;
    private String emailAluno;
    private String senhaAluno;
    private String dataNiver;
    private String academia;
    private int diaPagamento;

    public Aluno() {}

    @Exclude
    public String getIdAluno() {
        return idAluno;
    }

    public void setIdAluno(String idAluno) {
        this.idAluno = idAluno;
    }

    public String getIdImgAluno() {return idImgAluno;}

    public void setIdImgAluno(String idImgAluno) {this.idImgAluno = idImgAluno;}

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

    public String getDataNiver() {return dataNiver;}

    public void setDataNiver(String dataNiver) {this.dataNiver = dataNiver;}

    public String getAcademia() {
        return academia;
    }

    public void setAcademia(String academia) {
        this.academia = academia;
    }

    public int getDiaPagamento() {return diaPagamento;}

    public void setDiaPagamento(int diaPagamento) {this.diaPagamento = diaPagamento;}
}
