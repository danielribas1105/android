package com.apps.drpersonal.model;

import com.apps.drpersonal.config.ConfigFirebase;
import com.google.firebase.database.DatabaseReference;

public class Personal {

    private String idPersonal;
    private String nomePersonal;
    private String emailPersonal;
    private String senhaPersonal;

    public Personal() {
    }

    public void salvarPersonal(){
        DatabaseReference reference = ConfigFirebase.getFirebaseDatabase();
        reference.child("personal").child(this.idPersonal).setValue(this);
    }

    public String getIdPersonal() {
        return idPersonal;
    }

    public void setIdPersonal(String idPersonal) {
        this.idPersonal = idPersonal;
    }

    public String getNomePersonal() {
        return nomePersonal;
    }

    public void setNomePersonal(String nomePersonal) {
        this.nomePersonal = nomePersonal;
    }

    public String getEmailPersonal() {
        return emailPersonal;
    }

    public void setEmailPersonal(String emailPersonal) {
        this.emailPersonal = emailPersonal;
    }

    public String getSenhaPersonal() {
        return senhaPersonal;
    }

    public void setSenhaPersonal(String senhaPersonal) {
        this.senhaPersonal = senhaPersonal;
    }
}
