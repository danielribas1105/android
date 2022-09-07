package com.apps.personalvirtual.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.personalvirtual.R;
import com.apps.personalvirtual.RecyclerItemClickListener;
import com.apps.personalvirtual.model.ExPeito;
import com.apps.personalvirtual.ui.recyclerview.adapter.AdapterExPeito;

import java.util.ArrayList;
import java.util.List;

public class ExerciciosPeito extends AppCompatActivity {

    private RecyclerView exercPeito;
    private List<ExPeito> itensLista = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercicios_peito);

        exercPeito = findViewById(R.id.recyclerPeito);
        AdapterExPeito adapter = new AdapterExPeito(itensLista);

        this.criarExercicio();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        exercPeito.setLayoutManager(layoutManager);
        exercPeito.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        exercPeito.setHasFixedSize(true); //otimiza a utilização do RecyclerView
        exercPeito.setAdapter(adapter);

        exercPeito.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getApplicationContext(),
                        exercPeito, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        ExPeito ex = itensLista.get(position);
                        Toast.makeText(getApplicationContext(),
                                "Clique simples: " + ex.getImgEx(), Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onLongItemClick(View view, int position) {
                        ExPeito ex = itensLista.get(position);
                        Toast.makeText(getApplicationContext(),
                                "Clique longo: " + ex.getImgEx(), Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    }
                })
        );

    }
    public void criarExercicio(){
        ExPeito exercicio = new ExPeito("Imagem1","Supino Plano");
        this.itensLista.add(exercicio);
        exercicio = new ExPeito("Imagem2", "Supino Plano");
        this.itensLista.add(exercicio);
        exercicio = new ExPeito("Imagem3", "Supino Plano");
        this.itensLista.add(exercicio);
        exercicio = new ExPeito("Imagem4", "Supino Plano");
        this.itensLista.add(exercicio);
        exercicio = new ExPeito("Imagem5", "Supino Plano");
        this.itensLista.add(exercicio);
        exercicio = new ExPeito("Imagem6", "Supino Plano");
        this.itensLista.add(exercicio);
    }
}