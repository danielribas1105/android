package com.apps.drpersonal.model;

import java.io.Serializable;

public class Exercise implements Serializable {

    private String idExerc, idImgExerc, nomeExerc, quantExerc, descExerc;

    public Exercise() {}


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


    public String getDescExerc() {
        return descExerc;
    }

    public void setDescExerc(String descExerc) {
        this.descExerc = descExerc;
    }
}
