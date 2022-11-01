package com.apps.drpersonal.model;

import java.io.Serializable;

public class Exercise implements Serializable {

    private String idExerc, idImgExerc, nomeExerc, quantExerc, descExerc;

    public Exercise() {}

    public String getIdExerc() {
        return idExerc;
    }

    public void setIdExerc(String idExerc) {
        this.idExerc = idExerc;
    }

    public String getIdImgExerc() {
        return idImgExerc;
    }

    public void setIdImgExerc(String idImgExerc) {
        this.idImgExerc = idImgExerc;
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

    public String getDescExerc() {
        return descExerc;
    }

    public void setDescExerc(String descExerc) {
        this.descExerc = descExerc;
    }
}
