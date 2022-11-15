package com.apps.drpersonalmanager.model;

import java.io.Serializable;

public class Exercise implements Serializable {

    private String idExerc, nomeExerc, descExerc;

    public Exercise() {}

    public String getIdExerc() {return idExerc;}

    public void setIdExerc(String idExerc) {this.idExerc = idExerc;}

    public String getNomeExerc() {return nomeExerc;}

    public void setNomeExerc(String nomeExerc) {
        this.nomeExerc = nomeExerc;
    }

    public String getDescExerc() {
        return descExerc;
    }

    public void setDescExerc(String descExerc) {
        this.descExerc = descExerc;
    }
}
