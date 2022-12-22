package com.apps.drpersonalmanager.model;

import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.CHAVE_DB_IDPERSONAL;
import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.CHAVE_DB_PERSONAL;

import com.apps.drpersonalmanager.config.ConfigFirebase;
import com.google.firebase.database.DatabaseReference;

public class Personal {

    private String idPersonal;
    private String nomePersonal;
    private String emailPersonal;
    private String senhaPersonal;
    private DatabaseReference reference = ConfigFirebase.getFirebaseDatabase();

    public Personal() {}

    public void salvarNewPersonal(String id){
        reference.child(CHAVE_DB_PERSONAL).child(id).setValue(this);
    }

    public void atualizarSenhaPersonal(String senha){
        reference.child(CHAVE_DB_PERSONAL).child(CHAVE_DB_IDPERSONAL)
                .child("senhaPersonal").setValue(senha);
    }

    public String getIdPersonal() {return idPersonal;}

    public void setIdPersonal(String idPersonal) {this.idPersonal = idPersonal;}

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
