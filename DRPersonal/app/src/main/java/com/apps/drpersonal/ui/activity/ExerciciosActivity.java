package com.apps.drpersonal.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.drpersonal.R;
import com.apps.drpersonal.config.ConfigFirebase;
import com.apps.drpersonal.helper.Base64Custom;
import com.apps.drpersonal.helper.DataCustom;
import com.apps.drpersonal.helper.RecyclerItemClickListener;
import com.apps.drpersonal.model.Exercise;
import com.apps.drpersonal.model.Historico;
import com.apps.drpersonal.model.Training;
import com.apps.drpersonal.ui.adapter.ExerciciosAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ExerciciosActivity extends AppCompatActivity {

    private long currentDate;
    private TextView campoSerie, campoDesc;
    private SimpleDateFormat simpleDateFormat;
    private RecyclerView recyclerExerc;
    private ExerciciosAdapter adapterExerc;
    private static List<Exercise> exercises = new ArrayList<>();
    private Training trainingSelected;
    private static String date = "", keySerie = "", nameSerie = "", idTreino = "102022";
    private String idAluno = "";
    private FirebaseAuth auth = ConfigFirebase.getFirebaseAutenticacao();
    private DatabaseReference referenceExerc = ConfigFirebase.getFirebaseDatabase();
    private DatabaseReference exercAluno;
    private ValueEventListener valueEventListenerExerc;
    private Historico historico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercicios);

        currentDate = System.currentTimeMillis();
        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        date = simpleDateFormat.format(currentDate);

        trainingSelected = (Training) getIntent().getSerializableExtra("keyTraining");
        if (trainingSelected != null) {
            keySerie = trainingSelected.getNomeSerie();
            nameSerie = trainingSelected.getDescSerie();
        }

        campoSerie = findViewById(R.id.textSerie);
        campoDesc = findViewById(R.id.textDesc);
        campoSerie.setText(keySerie);
        campoDesc.setText(nameSerie);

        recyclerExerc = findViewById(R.id.recyclerExercicios);
        loadExercises(keySerie);

        //Configurar Adapter
        adapterExerc = new ExerciciosAdapter(exercises, this);
        //Configurar RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerExerc.setLayoutManager(layoutManager);
        recyclerExerc.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        recyclerExerc.setHasFixedSize(true);
        recyclerExerc.setAdapter(adapterExerc);

        //Configurar evento de click no RecyclerView
        recyclerExerc.addOnItemTouchListener(new RecyclerItemClickListener(
                getApplicationContext(), recyclerExerc,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Exercise exercSelected = exercises.get(position);
                        Intent intent = new Intent(ExerciciosActivity.this,
                                InfoExercActivity.class);
                        intent.putExtra("ExerciseSelect", exercSelected);
                        startActivity(intent);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }

                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    }
                }));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save_training, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        salvarHistorico();
        Toast.makeText(this, "Treino salvo em: " + date, Toast.LENGTH_SHORT).show();
        return super.onOptionsItemSelected(item);
    }

    private void loadExercises(String keySerie) {
        String idSerieAluno = "serie" + keySerie;
        idAluno = Base64Custom.codeToBase64(auth.getCurrentUser().getEmail());
        exercAluno = referenceExerc.child("exercicios").child(idAluno).child(idTreino)
                .child(idSerieAluno);
        valueEventListenerExerc = exercAluno.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                exercises.clear();
                for (DataSnapshot infoExerc : snapshot.getChildren()) {
                    Exercise exercise = infoExerc.getValue(Exercise.class);
                    exercises.add(exercise);
                }
                adapterExerc.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void salvarHistorico() {
        historico = new Historico();
        historico.setDataSerie(DataCustom.diaAtual(date));
        historico.setNomeSerie(keySerie);
        historico.setDescSerie(nameSerie);
        historico.salvar();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}