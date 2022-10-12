package com.apps.drpersonal.model;

import java.io.Serializable;

public class Exercise implements Serializable {

    private int imgExercId;
    private String nomeExerc, quantExerc;

    public Exercise(int imgId, String nome, String quantExerc) {
        this.imgExercId = imgId;
        this.nomeExerc = nome;
        this.quantExerc = quantExerc;
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

    public String getQuantExerc() {
        return quantExerc;
    }

    public void setQuantExerc(String quantExerc) {
        this.quantExerc = quantExerc;
    }
}
