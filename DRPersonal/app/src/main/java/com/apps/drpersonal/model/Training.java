package com.apps.drpersonal.model;

import java.io.Serializable;

public class Training implements Serializable {

    private String nomeSerie,descSerie, key;

    public Training() {}

    public String getNomeSerie() {
        return nomeSerie;
    }

    public void setNomeSerie(String nomeSerie) {
        this.nomeSerie = nomeSerie;
    }

    public String getDescSerie() {
        return descSerie;
    }

    public void setDescSerie(String descSerie) {
        this.descSerie = descSerie;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
