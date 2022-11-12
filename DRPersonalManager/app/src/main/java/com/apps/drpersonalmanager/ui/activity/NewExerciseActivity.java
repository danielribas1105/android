package com.apps.drpersonalmanager.ui.activity;

import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.CAT_ABDOMINAIS;
import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.CAT_AEROBICO;
import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.CAT_MUSC_INFER;
import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.CAT_MUSC_SUPER;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.apps.drpersonalmanager.R;
import com.apps.drpersonalmanager.dao.ExerciseDao;
import com.apps.drpersonalmanager.model.Exercise;

public class NewExerciseActivity extends AppCompatActivity {

    private EditText nomeExercNew, descExercNew;
    private RadioButton catAero, catAbdo, catMuscSuper, catMuscInfer;
    private RadioGroup catSelect;
    private Exercise exercise;
    private ExerciseDao exerciseDao = new ExerciseDao();
    private static String categoria = "";

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
        catSelect = findViewById(R.id.rgCategoria);

        selectedCategory();

    }

    private void selectedCategory() {
        catSelect.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int catId) {
                if(catId == R.id.rbAerobico){
                    categoria = CAT_AEROBICO;
                }else if(catId == R.id.rbAbdominais){
                    categoria = CAT_ABDOMINAIS;
                }else if(catId == R.id.rbMuscSuper){
                    categoria = CAT_MUSC_SUPER;
                }else if(catId == R.id.rbMuscInfer){
                    categoria = CAT_MUSC_INFER;
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_new_exercise, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.btn_menu_salvar_new_exercicio) {
            String nome = nomeExercNew.getText().toString();
            String descricao = descExercNew.getText().toString();
            exercise = new Exercise();
            exercise.setNomeExerc(nome);
            exercise.setDescExerc(descricao);
            exercise.setCatExerc(categoria);
            exerciseDao.salvar(exercise);
        }
        return super.onOptionsItemSelected(item);
    }


}