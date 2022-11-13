package com.apps.drpersonalmanager.dao;

import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.CHAVE_DB_IDPERSONAL;
import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.CHAVE_DB_TREINOS;

import com.apps.drpersonalmanager.config.ConfigFirebase;
import com.apps.drpersonalmanager.helper.Base64Custom;
import com.apps.drpersonalmanager.model.Training;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class TrainingDao {

    private static List<Training> trainings = new ArrayList<>();
    private DatabaseReference reference = ConfigFirebase.getFirebaseDatabase();

    public void salvarTreino(String idAluno, String data, String idSerie, Training training){
        reference.child(CHAVE_DB_TREINOS).child(CHAVE_DB_IDPERSONAL)
                .child(idAluno).child(data).child(idSerie).setValue(training);
    }
}
