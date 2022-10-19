package com.apps.drpersonal.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.apps.drpersonal.R;
import com.apps.drpersonal.model.Exercise;
import com.apps.drpersonal.model.InfoExercise;
import com.apps.drpersonal.ui.adapter.InfoExercAdapter;

import java.util.ArrayList;
import java.util.List;

public class InfoExercActivity extends AppCompatActivity {

    private TextView campoNomeExerc;
    private Exercise exerciseSelected;
    private RecyclerView recyclerInfoExerc;
    private InfoExercAdapter adapterInfoExerc;
    private List<InfoExercise> infoExercises = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_exerc);

        campoNomeExerc = findViewById(R.id.infoExercNome);
        exerciseSelected = (Exercise) getIntent().getSerializableExtra("nomeExercicio");
        campoNomeExerc.setText(exerciseSelected.getNomeExerc());

        recyclerInfoExerc = findViewById(R.id.recyclerInfoExerc);

        //Configurar Adapter
        adapterInfoExerc = new InfoExercAdapter(infoExercises, this);
        //Configurar RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerInfoExerc.setLayoutManager(layoutManager);
        recyclerInfoExerc.setAdapter(adapterInfoExerc);

    }
}