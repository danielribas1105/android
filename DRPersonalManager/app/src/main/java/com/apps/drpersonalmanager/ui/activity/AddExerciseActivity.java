package com.apps.drpersonalmanager.ui.activity;

import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.CAT_ABDOMINAIS;
import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.CAT_AEROBICO;
import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.CAT_MUSC_INFER;
import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.CAT_MUSC_SUPER;
import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.CHAVE_DB_EXERCICIOS;
import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.CHAVE_ID_OBJETIVO;
import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.CHAVE_ID_SERIE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.apps.drpersonalmanager.R;
import com.apps.drpersonalmanager.config.ConfigFirebase;
import com.apps.drpersonalmanager.helper.RecyclerItemClickListener;
import com.apps.drpersonalmanager.model.Exercise;
import com.apps.drpersonalmanager.ui.adapter.AddExerciseAdapter;
import com.apps.drpersonalmanager.ui.adapter.FindExercisesAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddExerciseActivity extends AppCompatActivity {

    private TextView serie, objetivo;
    private RadioGroup rgCateg;
    private Button btnAdd;
    private String categoria;
    private List<Exercise> addExercises = new ArrayList<>();
    private AddExerciseAdapter addExerciseAdapter;
    private RecyclerView recyclerViewAddExerc;
    private DatabaseReference reference = ConfigFirebase.getFirebaseDatabase();
    private DatabaseReference addExercRef;
    private ValueEventListener valueEventListenerAddExerc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercise);
        setTitle("Adicionar exercícios");

        serie = findViewById(R.id.serieSelecao_addExerc);
        objetivo = findViewById(R.id.objSerie_addExerc);
        rgCateg = findViewById(R.id.rgCatGrupo);
        btnAdd = findViewById(R.id.btnAddExerc);
        recyclerViewAddExerc = findViewById(R.id.recyclerAddExerc);
        String nomeSerie = (String) getIntent().getSerializableExtra(CHAVE_ID_SERIE);
        String objSerie = (String) getIntent().getSerializableExtra(CHAVE_ID_OBJETIVO);
        serie.setText(nomeSerie);
        objetivo.setText(objSerie);
        selectedCategory();

        addExerciseAdapter = new AddExerciseAdapter(addExercises,this);
        //Configurar RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewAddExerc.setLayoutManager(layoutManager);
        recyclerViewAddExerc.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        recyclerViewAddExerc.setHasFixedSize(true);
        recyclerViewAddExerc.setAdapter(addExerciseAdapter);

        //Configurar evento de click no RecyclerView Exercícios
        recyclerViewAddExerc.addOnItemTouchListener(new RecyclerItemClickListener(
                getApplicationContext(), recyclerViewAddExerc,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }

                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    }
                }));


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFindExercises();
            }
        });

    }

    private void selectedCategory() {
        rgCateg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int catId) {
                if (catId == R.id.rbAddAero) {
                    categoria = CAT_AEROBICO;
                } else if (catId == R.id.rbAddAbdo) {
                    categoria = CAT_ABDOMINAIS;
                } else if (catId == R.id.rbMemSuper) {
                    categoria = CAT_MUSC_SUPER;
                } else if (catId == R.id.rbMemInfer) {
                    categoria = CAT_MUSC_INFER;
                }
            }
        });
    }

    private void loadFindExercises() {
        addExercRef = reference.child(CHAVE_DB_EXERCICIOS).child(categoria);
        valueEventListenerAddExerc = addExercRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                addExercises.clear();
                for (DataSnapshot infoTreinos : snapshot.getChildren()) {
                    Exercise exercise = infoTreinos.getValue(Exercise.class);
                    addExercises.add(exercise);
                }
                addExerciseAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}