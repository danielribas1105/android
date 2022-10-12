package com.apps.drpersonal.model;

import java.io.Serializable;

public class Training implements Serializable {

    private int imageId;
    private String idTreino,descTreino;

    public Training(int imageId, String idTreino, String descTreino) {
        this.imageId = imageId;
        this.idTreino = idTreino;
        this.descTreino = descTreino;
    }

    public String getIdTreino() {
        return idTreino;
    }

    public void setIdTreino(String idTreino) {
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
