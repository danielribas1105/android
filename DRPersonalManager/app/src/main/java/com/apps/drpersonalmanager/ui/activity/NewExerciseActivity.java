package com.apps.drpersonalmanager.ui.activity;

import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.CAT_ABDOMINAIS;
import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.CAT_AEROBICO;
import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.CAT_MUSC_INFER;
import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.CAT_MUSC_SUPER;
import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.IMAGE_NOT_FOUND;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.apps.drpersonalmanager.R;
import com.apps.drpersonalmanager.dao.ExerciseDao;
import com.apps.drpersonalmanager.helper.StringCustom;
import com.apps.drpersonalmanager.model.Exercise;

public class NewExerciseActivity extends AppCompatActivity {

    private EditText nomeExercNew, descExercNew;
    private RadioGroup catSelect;
    private Exercise exercise;
    private ExerciseDao exerciseDao = new ExerciseDao();
    private static String categoria;
    private Boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_exercise);
        setTitle("Novo Exercício");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nomeExercNew = findViewById(R.id.editTextNewExerc);
        descExercNew = findViewById(R.id.editTextNewDesc);
        catSelect = findViewById(R.id.rgCategoria);
        selectedCategory();
    }

    private void selectedCategory() {
        catSelect.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int catId) {
                if(catId == R.id.rbAerobico){
                    categoria = CAT_AEROBICO;
                    flag = true;
                }else if(catId == R.id.rbAbdominais){
                    categoria = CAT_ABDOMINAIS;
                    flag = true;
                }else if(catId == R.id.rbMuscSuper){
                    categoria = CAT_MUSC_SUPER;
                    flag = true;
                }else if(catId == R.id.rbMuscInfer){
                    categoria = CAT_MUSC_INFER;
                    flag = true;
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
                    StringCustom.removerAcentos(nomeExercNew.getText().toString())
                            .toLowerCase().replaceAll(" ","_");
            String descricao = descExercNew.getText().toString();
            if(!nome.isEmpty()){
                if(!descricao.isEmpty()){
                    if(flag){
                        exercise = new Exercise();
                        exercise.setIdExerc(idEx);
                        exercise.setNomeExerc(nome);
                        exercise.setDescExerc(descricao);
                        exercise.setIdImgExerc(IMAGE_NOT_FOUND);
                        exerciseDao.salvarNewExercise(exercise, categoria, idEx);
                        nomeExercNew.setText("");
                        descExercNew.setText("");
                        categoria = "";
                        Toast.makeText(this, "Novo exercício "+nome+" salvo com sucesso!",
                                Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(this, "Selecione a categoria do exercício!", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(this, "Insira uma breve descrição sobre o exercício!", Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(this, "Insira o nome do exercício!", Toast.LENGTH_SHORT).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}