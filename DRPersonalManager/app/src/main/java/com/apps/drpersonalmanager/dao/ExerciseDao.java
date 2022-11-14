package com.apps.drpersonalmanager.dao;

import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.CHAVE_DB_EXERCICIOS;
import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.CHAVE_DB_EXERCICIOS_ALUNOS;
import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.CHAVE_DB_IDPERSONAL;

import com.apps.drpersonalmanager.config.ConfigFirebase;
import com.apps.drpersonalmanager.model.Exercise;
import com.apps.drpersonalmanager.model.ExerciseAluno;
import com.google.firebase.database.DatabaseReference;

public class ExerciseDao {

    private DatabaseReference reference = ConfigFirebase.getFirebaseDatabase();

    public void salvarNewExercise(Exercise exercise, String categoria, String idExercise){
        reference.child(CHAVE_DB_EXERCICIOS).child(categoria).child(idExercise).setValue(exercise);
    }

    public void salvarExercAluno(String idAluno, String idserie, ExerciseAluno exerciseAluno){
        reference.child(CHAVE_DB_EXERCICIOS_ALUNOS).child(CHAVE_DB_IDPERSONAL)
                .child(idAluno).child(idserie).push().setValue(exerciseAluno);
    }

}
