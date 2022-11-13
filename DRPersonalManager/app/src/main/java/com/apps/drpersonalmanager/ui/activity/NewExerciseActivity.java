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
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.apps.drpersonalmanager.R;
import com.apps.drpersonalmanager.dao.ExerciseDao;
import com.apps.drpersonalmanager.helper.DataCustom;
import com.apps.drpersonalmanager.model.Exercise;

import java.util.Locale;

public class NewExerciseActivity extends AppCompatActivity {

    private EditText nomeExercNew, descExercNew;
    private RadioGroup catSelect;
    private ImageButton btnNewImage, btnNewVideo;
    private Exercise exercise;
    private ExerciseDao exerciseDao = new ExerciseDao();
    private static String categoria = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_exercise);
        setTitle("Novo Exercício");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nomeExercNew = findViewById(R.id.editTextNewExerc);
        descExercNew = findViewById(R.id.editTextNewDesc);
        catSelect = findViewById(R.id.rgCategoria);
        btnNewImage = findViewById(R.id.btnInsertImage);
        btnNewVideo = findViewById(R.id.btnInsertVideo);

        selectedCategory();

        btnNewImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(NewExerciseActivity.this, "Função em desenvolvimento!",
                        Toast.LENGTH_SHORT).show();
            }
        });
        btnNewVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(NewExerciseActivity.this, "Função em desenvolvimento!",
                        Toast.LENGTH_SHORT).show();
            }
        });

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
            String idEx = System.currentTimeMillis()+"_"+
                    nome.toLowerCase().replaceAll(" ","_");
            String descricao = descExercNew.getText().toString();
            exercise = new Exercise();
            exercise.setIdExerc(idEx);
            exercise.setNomeExerc(nome);
            exercise.setDescExerc(descricao);
            exercise.setCatExerc(categoria);
            exerciseDao.salvarNewExercise(exercise, categoria);
            Toast.makeText(this, "Novo exercício "+nome+" salvo com sucesso!",
                    Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }


}