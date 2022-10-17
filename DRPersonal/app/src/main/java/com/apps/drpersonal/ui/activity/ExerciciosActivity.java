package com.apps.drpersonal.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

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
import com.google.firebase.database.DatabaseReference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ExerciciosActivity extends AppCompatActivity {

    private long currentDate;
    private SimpleDateFormat simpleDateFormat;
    private RecyclerView recyclerExerc;
    private ExerciciosAdapter adapterExerc;
    private static List<Exercise> exercises = new ArrayList<>();
    private Training trainingSelected;
    private static String date="", keySerie="", nameSerie="",idTreino="102022";
    private Historico historico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercicios);

        currentDate = System.currentTimeMillis();
        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        date = simpleDateFormat.format(currentDate);

        trainingSelected = (Training) getIntent().getSerializableExtra("keyTraining");
        if(trainingSelected!=null){
            keySerie = trainingSelected.getNomeSerie();
            nameSerie = trainingSelected.getDescSerie();
        }

        recyclerExerc = findViewById(R.id.recyclerExercicios);
        loadExercises(keySerie);

        //Configurar Adapter
        adapterExerc = new ExerciciosAdapter(exercises,this);
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
                        intent.putExtra("nomeExercicio",exercSelected);
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
        Toast.makeText(this, "Treino salvo em: "+date, Toast.LENGTH_SHORT).show();
        return super.onOptionsItemSelected(item);
    }

    private static void loadExercises(String keySerie) {
        exercises.clear();
        switch (keySerie){
            case "A":
                exercises.add(new Exercise(R.drawable.supino_reto,"Supino Reto","3 x 12/10/8"));
                exercises.add(new Exercise(R.drawable.supino_inclinado,"Supino Inclinado","3 x 10"));
                exercises.add(new Exercise(R.drawable.desenvolvimento,"Desenvolvimento","3 x 10"));
                exercises.add(new Exercise(R.drawable.elevacao_lateral,"Elevação Lateral","3 x 12"));
                exercises.add(new Exercise(R.drawable.barra_fixa,"Barra Fixa","3 x máximo"));
                break;
            case "B":
                exercises.add(new Exercise(R.drawable.supino_reto,"SerieB","3 x 12/10/8"));
                exercises.add(new Exercise(R.drawable.supino_inclinado,"SerieB","3 x 10"));
                exercises.add(new Exercise(R.drawable.desenvolvimento,"SerieB","3 x 10"));
                exercises.add(new Exercise(R.drawable.elevacao_lateral,"SerieB","3 x 12"));
                exercises.add(new Exercise(R.drawable.barra_fixa,"SerieB","3 x máximo"));
                break;
            case "C":
                exercises.add(new Exercise(R.drawable.supino_reto,"Serie C","3 x 12/10/8"));
                exercises.add(new Exercise(R.drawable.supino_inclinado,"Serie C","3 x 10"));
                exercises.add(new Exercise(R.drawable.desenvolvimento,"Serie C","3 x 10"));
                exercises.add(new Exercise(R.drawable.elevacao_lateral,"Serie C","3 x 12"));
                exercises.add(new Exercise(R.drawable.barra_fixa,"Serie C","3 x máximo"));
                break;
            case "D":
                exercises.add(new Exercise(R.drawable.supino_reto,"Serie D","3 x 12/10/8"));
                exercises.add(new Exercise(R.drawable.supino_inclinado,"Serie D","3 x 10"));
                exercises.add(new Exercise(R.drawable.desenvolvimento,"Serie D","3 x 10"));
                exercises.add(new Exercise(R.drawable.elevacao_lateral,"Serie D","3 x 12"));
                exercises.add(new Exercise(R.drawable.barra_fixa,"Serie D","3 x máximo"));
                break;
        }
    }

    public void salvarHistorico(){
        historico = new Historico();
        historico.setDataSerie(DataCustom.diaAtual(date));
        historico.setNomeSerie(keySerie);
        historico.setDescSerie(nameSerie);

        historico.salvar();
    }
}