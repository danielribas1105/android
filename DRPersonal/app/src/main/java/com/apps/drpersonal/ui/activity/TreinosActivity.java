package com.apps.drpersonal.ui.activity;

import static com.apps.drpersonal.ui.activity.ConstantesActivities.CHAVE_DB_IDPERSONAL;
import static com.apps.drpersonal.ui.activity.ConstantesActivities.CHAVE_DB_TREINOS;
import static com.apps.drpersonal.ui.activity.ConstantesActivities.CHAVE_TRAINING;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.drpersonal.R;
import com.apps.drpersonal.config.ConfigFirebase;
import com.apps.drpersonal.helper.Base64Custom;
import com.apps.drpersonal.helper.RecyclerItemClickListener;
import com.apps.drpersonal.helper.UsersFirebase;
import com.apps.drpersonal.model.Training;
import com.apps.drpersonal.ui.adapter.TreinosAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TreinosActivity extends AppCompatActivity {

    private String idAluno;
    private FirebaseAuth auth = ConfigFirebase.getFirebaseAutenticacao();
    private DatabaseReference reference = ConfigFirebase.getFirebaseDatabase();
    private DatabaseReference treinoAluno;
    private ValueEventListener valueEventListenerTreino;
    private TreinosAdapter treinosAdapter;
    private List<Training> trainings = new ArrayList<>();
    private RecyclerView recyclerTraining;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treinos);
        getSupportActionBar().setTitle("Meus Treinos");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerTraining = findViewById(R.id.recyclerTreinos);

        //Configurar Adapter
        loadTraining();
        treinosAdapter = new TreinosAdapter(trainings, this);
        //Configurar RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerTraining.setLayoutManager(layoutManager);
        recyclerTraining.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        recyclerTraining.setHasFixedSize(true);
        recyclerTraining.setAdapter(treinosAdapter);

        //Configurar evento de click no Recycler View
        recyclerTraining.addOnItemTouchListener(new RecyclerItemClickListener(
                getApplicationContext(), recyclerTraining,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        //Recuperar treino selecionado
                        Training trainingSelected = trainings.get(position);
                        //Enviar treino selecionado para a próxima tela
                        Intent i = new Intent(TreinosActivity.this,
                                ExerciciosActivity.class);
                        i.putExtra(CHAVE_TRAINING, trainingSelected);
                        startActivity(i);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }

                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    }
                }
        ));

    }

    public void loadTraining() {
        idAluno = UsersFirebase.getIdUserAuth();
        treinoAluno = reference.child(CHAVE_DB_TREINOS).child(CHAVE_DB_IDPERSONAL).child(idAluno);
        valueEventListenerTreino = treinoAluno.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                trainings.clear();
                for (DataSnapshot infoTreinos : snapshot.getChildren()) {
                    Training training = infoTreinos.getValue(Training.class);
                    trainings.add(training);
                }
                treinosAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadTraining();
    }

    @Override
    protected void onStop() {
        super.onStop();
        treinoAluno.removeEventListener(valueEventListenerTreino);
    }
}