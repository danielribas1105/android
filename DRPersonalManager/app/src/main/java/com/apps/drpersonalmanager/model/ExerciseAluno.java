package com.apps.drpersonalmanager.model;

public class ExerciseAluno {

    private String idExerc, nomeExerc, catExerc, quantExerc, pesoExerc, obsExerc;

    public ExerciseAluno() {}

    public String getIdExerc() {return idExerc;}

    public void setIdExerc(String idExerc) {this.idExerc = idExerc;}

    public String getNomeExerc() {return nomeExerc;}

    public void setNomeExerc(String nomeExerc) {
        this.nomeExerc = nomeExerc;
    }

    public String getCatExerc() {return catExerc;}

    public void setCatExerc(String catExerc) {
        this.catExerc = catExerc;
    }

    public String getQuantExerc() {
        return quantExerc;
    }

    public void setQuantExerc(String quantExerc) {
        this.quantExerc = quantExerc;
    }

    public String getPesoExerc() {return pesoExerc;}

    public void setPesoExerc(String pesoExerc) {this.pesoExerc = pesoExerc;}

    public String getObsExerc() {return obsExerc;}

    public void setObsExerc(String obsExerc) {this.obsExerc = obsExerc;}

}
