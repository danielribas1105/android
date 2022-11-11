package com.apps.drpersonalmanager.dao;

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
    private FirebaseAuth auth = ConfigFirebase.getFirebaseAutenticacao();
    private DatabaseReference reference = ConfigFirebase.getFirebaseDatabase();
    private String idPersonal;

    public void salvar(Training training, String idAluno, String idSerie){
        idPersonal = Base64Custom.codeToBase64(auth.getCurrentUser().getEmail());
        reference.child("treinos").child(idPersonal).child(idAluno).child(idSerie).setValue(training);
    }
}
