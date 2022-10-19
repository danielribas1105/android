package com.apps.drpersonal.model;

import java.io.Serializable;

public class InfoExercise implements Serializable {

    private String videoExerc, imgExerc, descExerc;

    public InfoExercise() {}

    public String getVideoExerc() {return videoExerc;}

    public void setVideoExerc(String videoExerc) {
        this.videoExerc = videoExerc;
    }

    public String getImgExerc() {
        return imgExerc;
    }

    public void setImgExerc(String imgExerc) {
        this.imgExerc = imgExerc;
    }

    public String getDescExerc() {
        return descExerc;
    }

    public void setDescExerc(String descExerc) {
        this.descExerc = descExerc;
    }
}
