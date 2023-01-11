package com.apps.drpersonalmanager.dao;

import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.CHAVE_DB_CREF_PERSONAL;
import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.CHAVE_DB_IDPERSONAL;
import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.CHAVE_DB_NOME_PERSONAL;
import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.CHAVE_DB_PERSONAL;

import com.apps.drpersonalmanager.config.ConfigFirebase;
import com.apps.drpersonalmanager.helper.UsersFirebase;
import com.google.firebase.database.DatabaseReference;

public class PersonalDao {

    private DatabaseReference reference = ConfigFirebase.getFirebaseDatabase();
    private String idPersonal = UsersFirebase.getIdUserAuth();

    public void salvarPerfilPersonal(String nome, String cref){
        reference.child(CHAVE_DB_PERSONAL).child(CHAVE_DB_IDPERSONAL)
                .child(CHAVE_DB_NOME_PERSONAL).setValue(nome);
        reference.child(CHAVE_DB_PERSONAL).child(CHAVE_DB_IDPERSONAL)
                .child(CHAVE_DB_CREF_PERSONAL).setValue(cref);
    }

}
