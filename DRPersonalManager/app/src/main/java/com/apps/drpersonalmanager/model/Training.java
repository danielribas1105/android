package com.apps.drpersonalmanager.model;

import java.io.Serializable;

public class Training implements Serializable {

    private String nomeSerie,descSerie;

    public Training() {}

    public String getNomeSerie() {return nomeSerie;}

    public void setNomeSerie(String nomeSerie) {
        this.nomeSerie = nomeSerie;
    }

    public String getDescSerie() {
        return descSerie;
    }

    public void setDescSerie(String descSerie) {
        this.descSerie = descSerie;
    }
}
