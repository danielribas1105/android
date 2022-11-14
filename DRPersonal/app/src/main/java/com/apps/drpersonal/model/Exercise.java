package com.apps.drpersonal.model;

import java.io.Serializable;

public class Exercise implements Serializable {

    private String idExerc, nomeExerc, catExerc, descExerc;

    public Exercise() {}

    public String getIdExerc() {return idExerc;}

    public void setIdExerc(String idExerc) {this.idExerc = idExerc;}

    public String getNomeExerc() {
        return nomeExerc;
    }

    public void setNomeExerc(String nomeExerc) {
        this.nomeExerc = nomeExerc;
    }

    public String getCatExerc() {return catExerc;}

    public void setCatExerc(String catExerc) {this.catExerc = catExerc;}

    public String getDescExerc() {
        return descExerc;
    }

    public void setDescExerc(String descExerc) {
        this.descExerc = descExerc;
    }
}
