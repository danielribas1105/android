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
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.apps.drpersonalmanager.R;
import com.apps.drpersonalmanager.config.ConfigFirebase;
import com.apps.drpersonalmanager.helper.RecyclerItemClickListener;
import com.apps.drpersonalmanager.model.Exercise;
import com.apps.drpersonalmanager.ui.adapter.AddExerciseAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddExerciseActivity extends AppCompatActivity {

    private TextView serie, objetivo;
    private RadioGroup rgCat1, rgCat2;
    private Button btnAdd;
    private String categoria, cat1 = "", cat2 = "";
    private List<Exercise> exercises = new ArrayList<>();
    private AddExerciseAdapter addExerciseAdapter;
    private RecyclerView recyclerView;
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
        rgCat1 = findViewById(R.id.rgCatGrupo1);
        rgCat2 = findViewById(R.id.rgCatGrupo2);
        btnAdd = findViewById(R.id.btnAddExerc);
        recyclerView = findViewById(R.id.recyclerAddExerc);
        String nomeSerie = (String) getIntent().getSerializableExtra(CHAVE_ID_SERIE);
        String objSerie = (String) getIntent().getSerializableExtra(CHAVE_ID_OBJETIVO);
        serie.setText(nomeSerie);
        objetivo.setText(objSerie);
        selectedCategory();

        addExerciseAdapter = new AddExerciseAdapter(exercises,this);
        //Configurar RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(addExerciseAdapter);

        //Configurar evento de click no RecyclerView Exercícios
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(
                getApplicationContext(), recyclerView,
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
                if(!cat1.isEmpty() && !cat2.isEmpty()){
                    Toast.makeText(AddExerciseActivity.this,
                            "Selecionar apenas 1 categoria por vez!", Toast.LENGTH_SHORT).show();
                    rgCat1.clearCheck();
                    rgCat2.clearCheck();
                    categoria = "";
                }else if(!cat1.isEmpty() && cat2.isEmpty()){
                    rgCat2.clearCheck();
                    categoria = cat1;
                }else if(cat1.isEmpty() && !cat2.isEmpty()){
                    rgCat1.clearCheck();
                    categoria = cat2;
                }
                Log.i("cat", categoria);
                //loadFindExercises();
            }
        });

    }

    private void selectedCategory() {
        rgCat1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int catId1) {
                if (catId1 == R.id.rbAddAero) {
                    cat1 = CAT_AEROBICO;
                } else if (catId1 == R.id.rbAddAbdo) {
                    cat1 = CAT_ABDOMINAIS;
                }
            }
        });
        rgCat2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int catId2) {
                if (catId2 == R.id.rbMemSuper) {
                    cat2 = CAT_MUSC_SUPER;
                } else if (catId2 == R.id.rbMemInfer) {
                    cat2 = CAT_MUSC_INFER;
                }
            }
        });
    }

    private void loadFindExercises() {
        addExercRef = reference.child(CHAVE_DB_EXERCICIOS).child(categoria);
        valueEventListenerAddExerc = addExercRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                exercises.clear();
                for (DataSnapshot infoTreinos : snapshot.getChildren()) {
                    Exercise exercise = infoTreinos.getValue(Exercise.class);
                    exercises.add(exercise);
                }
                addExerciseAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}