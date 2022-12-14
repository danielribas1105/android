package com.apps.drpersonaltrainer.model;

public class Aluno {

    private String idAluno;
    private String nomeAluno;
    private String emailAluno;
    private String senhaAluno;
    private String dataInicio;

    public Aluno(String nomeAluno, String dataInicio, String emailAluno) {
        this.nomeAluno = nomeAluno;
        this.dataInicio = dataInicio;
        this.emailAluno = emailAluno;
    }

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

    public String getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(String dataInicio) {
        this.dataInicio = dataInicio;
    }
}
