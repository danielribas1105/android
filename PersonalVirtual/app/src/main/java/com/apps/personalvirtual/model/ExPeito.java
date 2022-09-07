package com.apps.personalvirtual.model;

public class ExPeito {
    private String imgEx;
    private String nomeEx;

    public ExPeito(){}

    public ExPeito(String imgEx, String nomeEx) {
        this.imgEx = imgEx;
        this.nomeEx = nomeEx;
    }

    public String getImgEx() {
        return imgEx;
    }

    public void setImgEx(String imgEx) {
        this.imgEx = imgEx;
    }

    public String getNomeEx() {
        return nomeEx;
    }

    public void setNomeEx(String nomeEx) {
        this.nomeEx = nomeEx;
    }
}
