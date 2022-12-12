package com.apps.drpersonal.model;

import static com.apps.drpersonal.ui.activity.ConstantesActivities.CHAVE_DB_HISTORICO;
import static com.apps.drpersonal.ui.activity.ConstantesActivities.CHAVE_DB_IDPERSONAL;

import com.apps.drpersonal.config.ConfigFirebase;
import com.apps.drpersonal.helper.Base64Custom;
import com.apps.drpersonal.helper.DataCustom;
import com.apps.drpersonal.helper.UsersFirebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class Historico {

    private String idAluno, dataSerie, nomeSerie, descSerie;
    private FirebaseAuth auth = ConfigFirebase.getFirebaseAutenticacao();

    public Historico() {}

    public void salvar(){
        idAluno = UsersFirebase.getIdUserAuth();
        DatabaseReference referenceHist = ConfigFirebase.getFirebaseDatabase();
        referenceHist.child(CHAVE_DB_HISTORICO).child(CHAVE_DB_IDPERSONAL).child(idAluno)
                .child(DataCustom.dataMesAno(DataCustom.dataAtual()))
                .push()
                .setValue(this);
    }

    public String getDataSerie() {
        return dataSerie;
    }

    public void setDataSerie(String dataSerie) {
        this.dataSerie = dataSerie;
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
}
