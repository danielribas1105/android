package com.apps.drpersonalmanager.ui.activity;

import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.CHAVE_DB_EXERCICIOS_ALUNOS;
import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.CHAVE_DB_IDPERSONAL;
import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.STR_SERIE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import com.apps.drpersonalmanager.R;
import com.apps.drpersonalmanager.config.ConfigFirebase;
import com.apps.drpersonalmanager.helper.Base64Custom;
import com.apps.drpersonalmanager.model.ExerciseAluno;
import com.apps.drpersonalmanager.model.Training;
import com.apps.drpersonalmanager.ui.adapter.TreinoSelectAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EditTrainingSelectActivity extends AppCompatActivity {

    private String idAluno, emailAluno, serieNomeSelect;
    private Training training;
    private List<ExerciseAluno> exerciseAlunos = new ArrayList<>();
    private RecyclerView recyclerTreinoSelect;
    private TreinoSelectAdapter treinoSelectAdapter;
    private DatabaseReference reference = ConfigFirebase.getFirebaseDatabase();
    private DatabaseReference exercTreinoAluno;
    private ValueEventListener valueEventListenerExercTreino;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_training_select);

        training = (Training) getIntent().getSerializableExtra(STR_SERIE);
        if(training != null){
            serieNomeSelect = STR_SERIE + training.getNomeSerie();
            Log.i("serie",serieNomeSelect);
        }

        //Configurar adapter treino selecionado
        //loadExercTreinoSelect(serieNomeSelect);
        treinoSelectAdapter = new TreinoSelectAdapter(exerciseAlunos,this);
        //Configurar RecyclerView
        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(this);
        recyclerTreinoSelect.setLayoutManager(layoutManager1);
        recyclerTreinoSelect.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        recyclerTreinoSelect.setHasFixedSize(true);
        recyclerTreinoSelect.setAdapter(treinoSelectAdapter);

    }

    public void loadExercTreinoSelect(String serie){
        idAluno = Base64Custom.codeToBase64(emailAluno);
        exercTreinoAluno = reference.child(CHAVE_DB_EXERCICIOS_ALUNOS).child(CHAVE_DB_IDPERSONAL)
                .child(idAluno).child(STR_SERIE+serie);
        valueEventListenerExercTreino = exercTreinoAluno.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                exerciseAlunos.clear();
                for (DataSnapshot infoTreinos : snapshot.getChildren()) {
                    ExerciseAluno exerciseAluno = infoTreinos.getValue(ExerciseAluno.class);
                    exerciseAlunos.add(exerciseAluno);
                }
                treinoSelectAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}