package com.apps.drpersonal.model;

import java.io.Serializable;

public class Training implements Serializable {

    private int idTreino, imageId;
    private String descTreino;

    public Training(int imageId, String descTreino) {
        this.imageId = imageId;
        this.descTreino = descTreino;
    }

    public int getIdTreino() {
        return idTreino;
    }

    public void setIdTreino(int idTreino) {
        this.idTreino = idTreino;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getDescTreino() {
        return descTreino;
    }

    public void setDescTreino(String descTreino) {
        this.descTreino = descTreino;
    }

}
