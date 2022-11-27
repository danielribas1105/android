package com.apps.drpersonalmanager.model;

import java.io.Serializable;

public class Aluno implements Serializable {

    private String idAluno;
    private String nomeAluno;
    private String emailAluno;
    private String senhaAluno;
    private String dataNiver;
    private String academia;
    private int diaPagamento;

    public Aluno() {}

    public String getIdAluno() {
        return idAluno;
    }

    public void setIdAluno(String idAluno) {
        this.idAluno = idAluno;
    }

    public String getNomeAluno() {
        return nomeAluno;
    }

    public void setNomeAluno(String nomeAluno) {
        this.nomeAluno = nomeAluno;
    }

    public String getEmailAluno() {
        return emailAluno;
    }

    public void setEmailAluno(String emailAluno) {
        this.emailAluno = emailAluno;
    }

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
