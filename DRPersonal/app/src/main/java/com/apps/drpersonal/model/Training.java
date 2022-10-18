package com.apps.drpersonal.model;

import com.apps.drpersonal.config.ConfigFirebase;
import com.apps.drpersonal.helper.Base64Custom;
import com.apps.drpersonal.helper.DataCustom;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.io.Serializable;

public class Training implements Serializable {

    private String idAluno, nomeSerie,descSerie, key;
    private FirebaseAuth auth = ConfigFirebase.getFirebaseAutenticacao();

    public Training() {}

    public String findTreino(){
        idAluno = Base64Custom.codeToBase64(auth.getCurrentUser().getEmail());
        DatabaseReference refTreino = ConfigFirebase.getFirebaseDatabase();
        return refTreino.child("treinos").child(idAluno).getKey();
    }

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
