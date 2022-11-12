package com.apps.drpersonalmanager.dao;

import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.CHAVE_DB_EXERCICIOS;

import com.apps.drpersonalmanager.config.ConfigFirebase;
import com.apps.drpersonalmanager.model.Exercise;
import com.google.firebase.database.DatabaseReference;

public class ExerciseDao {

    private DatabaseReference reference = ConfigFirebase.getFirebaseDatabase();

    public void salvar(Exercise exercise, String categoria){
        reference.child(CHAVE_DB_EXERCICIOS).child(categoria).push().setValue(exercise);
    }

    public void loadExercises(String categoria){

    }
}
