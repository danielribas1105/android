package com.apps.drpersonal.model;

public class Training {

    private int imageId;
    private String descTreino;

    public Training(int imageId, String descTreino) {
        this.imageId = imageId;
        this.descTreino = descTreino;
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
