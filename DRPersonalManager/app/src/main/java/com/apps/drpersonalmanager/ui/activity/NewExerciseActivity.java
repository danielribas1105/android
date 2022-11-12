package com.apps.drpersonalmanager.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.apps.drpersonalmanager.R;
import com.apps.drpersonalmanager.model.Exercise;

public class NewExerciseActivity extends AppCompatActivity {

    private EditText nomeExercNew, descExercNew;
    private RadioButton catAero, catAbdo, catMuscSuper, catMuscInfer;
    private String categoria;
    private Exercise exercise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_exercise);
        setTitle("Novo Exercício");

        nomeExercNew = findViewById(R.id.editTextNewExerc);
        descExercNew = findViewById(R.id.editTextNewDesc);
        catAero = findViewById(R.id.rbAerobico);
        catAbdo = findViewById(R.id.rbAbdominais);
        catMuscSuper = findViewById(R.id.rbMuscSuper);
        catMuscInfer = findViewById(R.id.rbMuscInfer);

        selectedCategory();
    }

    private void selectedCategory() {
        if (catAero.isChecked()) {
            categoria = "Aeróbico";
            Log.i("cat", categoria);
        } else if (catAbdo.isChecked()) {
            categoria = "Abdominais";
            Log.i("cat", categoria);
        } else if (catMuscSuper.isChecked()) {
            categoria = "Músculos Superiores";
            Log.i("cat", categoria);
        } else if(catMuscInfer.isChecked()) {
            categoria = "Músculos Inferiores";
            Log.i("cat", categoria);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_new_exercise, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.btn_menu_salvar_new_exercicio) {
            exercise = new Exercise();
        }
        return super.onOptionsItemSelected(item);
    }
}