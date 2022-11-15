package com.apps.drpersonal.ui.activity;

import static com.apps.drpersonal.ui.activity.ConstantesActivities.CHAVE_DB_EXERCICIOS;
import static com.apps.drpersonal.ui.activity.ConstantesActivities.CHAVE_DB_EXERCICIOS_ALUNO;
import static com.apps.drpersonal.ui.activity.ConstantesActivities.CHAVE_DB_IDPERSONAL;
import static com.apps.drpersonal.ui.activity.ConstantesActivities.CHAVE_EXERCISE;
import static com.apps.drpersonal.ui.activity.ConstantesActivities.CHAVE_TRAINING;
import static com.apps.drpersonal.ui.activity.ConstantesActivities.STR_SERIE;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.apps.drpersonal.model.ExerciseAluno;
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
    private static List<ExerciseAluno> exercisesAluno = new ArrayList<>();
    private Training trainingSelected;
    private static String date = "", keySerie = "", nameSerie = "";
    private String idAluno = "";
    private FirebaseAuth auth = ConfigFirebase.getFirebaseAutenticacao();
    private DatabaseReference reference = ConfigFirebase.getFirebaseDatabase();
    private DatabaseReference exercAluno;
    private ValueEventListener valueEventListenerExerc;
    private Historico historico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercicios);

        getSupportActionBar().setTitle("Meus Exercícios");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        currentDate = System.currentTimeMillis();
        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        date = simpleDateFormat.format(currentDate);

        trainingSelected = (Training) getIntent().getSerializableExtra(CHAVE_TRAINING);
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
        adapterExerc = new ExerciciosAdapter(exercisesAluno, this);
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
                        ExerciseAluno exercSelected = exercisesAluno.get(position);
                        Intent i = new Intent(ExerciciosActivity.this,
                                InfoExercActivity.class);
                        i.putExtra(CHAVE_EXERCISE, exercSelected);
                        startActivity(i);
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
        getMenuInflater().inflate(R.menu.menu_save_info, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuSaveInfo){
            salvarHistorico();
            Toast.makeText(this, "Treino salvo em: " + date, Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadExercises(String keySerie) {
        String idSerieAluno = STR_SERIE + keySerie;
        idAluno = Base64Custom.codeToBase64(auth.getCurrentUser().getEmail());
        exercAluno = reference.child(CHAVE_DB_EXERCICIOS_ALUNO).child(CHAVE_DB_IDPERSONAL)
                .child(idAluno).child(idSerieAluno);
        valueEventListenerExerc = exercAluno.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                exercisesAluno.clear();
                for (DataSnapshot infoExerc : snapshot.getChildren()) {
                    ExerciseAluno exercAluno = infoExerc.getValue(ExerciseAluno.class);
                    exercisesAluno.add(exercAluno);
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