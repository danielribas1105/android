package com.apps.drpersonal.ui.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.apps.drpersonal.R;
import com.apps.drpersonal.databinding.ActivityMeusExerciciosBinding;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;

public class MeusExerciciosActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMeusExerciciosBinding binding;
    private long currentDate;
    private SimpleDateFormat simpleDateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMeusExerciciosBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_meus_exercicios);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        currentDate = System.currentTimeMillis();
        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String date = simpleDateFormat.format(currentDate);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Treino salvo em " + date, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_meus_exercicios);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}