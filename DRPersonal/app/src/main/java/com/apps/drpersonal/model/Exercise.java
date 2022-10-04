package com.apps.drpersonal.model;

public class Exercise {

    private int imgExercId;
    private String nomeExerc;

    public Exercise(int imgId, String nome) {
        this.imgExercId = imgId;
        this.nomeExerc = nome;
    }

    public int getImgExercId() {
        return imgExercId;
    }

    public void setImgExercId(int imgExercId) {
        this.imgExercId = imgExercId;
    }

    public String getNomeExerc() {
        return nomeExerc;
    }

    public void setNomeExerc(String nomeExerc) {
        this.nomeExerc = nomeExerc;
    }
}
