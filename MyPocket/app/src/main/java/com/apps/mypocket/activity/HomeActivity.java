package com.apps.mypocket.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.apps.mypocket.R;
import com.apps.mypocket.helper.DateCustom;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HomeActivity extends AppCompatActivity {

    private Animation rotateOpen;
    private Animation rotateClose;
    private Animation fromBottom;
    private Animation toBottom;
    private Boolean clicked = false;

    private FloatingActionButton fabAdd;
    private FloatingActionButton fabReceita;
    private FloatingActionButton fabDespesa;

    private TextView currentMonth;
    private String mesAtual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        currentMonth = findViewById(R.id.textCurrentMonth);
        currentMonth.setText(DateCustom.mesAnoFormat(DateCustom.dataAtual()));

        rotateOpen = AnimationUtils.loadAnimation(this, R.anim.rotate_open_anim);
        rotateClose = AnimationUtils.loadAnimation(this, R.anim.rotate_close_anim);
        fromBottom = AnimationUtils.loadAnimation(this, R.anim.from_bottom_anim);
        toBottom = AnimationUtils.loadAnimation(this, R.anim.to_bottom_anim);

        fabAdd = findViewById(R.id.fabAdd);
        fabReceita = findViewById(R.id.fabReceita);
        fabDespesa = findViewById(R.id.fabDespesa);

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBtnAddClick();
            }
        });
    }

    private void onBtnAddClick() {
        setVisibility(clicked);
        setAnimation(clicked);
        setClickable(clicked);
        clicked = !clicked;
    }

    private void setVisibility(Boolean clicked) {
        if (!clicked) {
            fabReceita.setVisibility(View.VISIBLE);
            fabDespesa.setVisibility(View.VISIBLE);
        } else {
            fabReceita.setVisibility(View.INVISIBLE);
            fabDespesa.setVisibility(View.INVISIBLE);
        }
    }

    private void setAnimation(Boolean clicked) {
        if (!clicked) {
            fabReceita.startAnimation(fromBottom);
            fabDespesa.startAnimation(fromBottom);
            fabAdd.startAnimation(rotateOpen);

        } else {
            fabReceita.startAnimation(toBottom);
            fabDespesa.startAnimation(toBottom);
            fabAdd.startAnimation(rotateClose);
        }
    }

    private void setClickable(Boolean clicked){
        if(!clicked){
            fabReceita.setClickable(true);
            fabDespesa.setClickable(true);
        }else {
            fabReceita.setClickable(false);
            fabDespesa.setClickable(false);
        }
    }

    public void addReceita(View view){
        startActivity(new Intent(this, ReceitaActivity.class));
        Toast.makeText(HomeActivity.this, "Adicionar Receita",
                Toast.LENGTH_SHORT).show();
    }

    public void addDespesa(View view){
        startActivity(new Intent(this, DespesasActivity.class));
        Toast.makeText(HomeActivity.this, "Adicionar Despesa",
                Toast.LENGTH_SHORT).show();
    }

    public void clickPreviousMonth(View view){
        mesAtual = currentMonth.getText().toString();
        currentMonth.setText(DateCustom.previousMonth(mesAtual));
    }

    public void clickNextMonth(View view){
        mesAtual = currentMonth.getText().toString();
        currentMonth.setText(DateCustom.nextMonth(mesAtual));
    }

}