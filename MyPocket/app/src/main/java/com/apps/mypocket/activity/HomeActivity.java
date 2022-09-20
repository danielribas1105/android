package com.apps.mypocket.activity;

import android.content.Intent;
import android.os.Bundle;

import com.apps.mypocket.databinding.ActivityHomeBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.apps.mypocket.R;

public class HomeActivity extends AppCompatActivity {

    private Animation rotateOpen;
    private Animation rotateClose;
    private Animation fromBottom;
    private Animation toBottom;
    private Boolean clicked = false;

    private FloatingActionButton fabAdd;
    private FloatingActionButton fabReceita;
    private FloatingActionButton fabDespesa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

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
        /*
        fabReceita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addReceita(view);
            }
        });
        fabDespesa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addDespesa(view);
            }
        });

         */

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

}