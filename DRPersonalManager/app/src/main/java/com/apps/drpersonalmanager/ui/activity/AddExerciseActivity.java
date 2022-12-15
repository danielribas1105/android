package com.apps.drpersonalmanager.ui.activity;

import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.CAT_ABDOMINAIS;
import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.CAT_AEROBICO;
import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.CAT_MUSC_INFER;
import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.CAT_MUSC_SUPER;
import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.CHAVE_ID_OBJETIVO;
import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.CHAVE_ID_SERIE;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.apps.drpersonalmanager.R;

public class AddExerciseActivity extends AppCompatActivity {

    private TextView serie, objetivo;
    private RadioGroup rgCat1, rgCat2;
    private Button btnAdd;
    private String cat1 = "", cat2 = "";

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
        String nomeSerie = (String) getIntent().getSerializableExtra(CHAVE_ID_SERIE);
        String objSerie = (String) getIntent().getSerializableExtra(CHAVE_ID_OBJETIVO);
        serie.setText(nomeSerie);
        objetivo.setText(objSerie);
        selectedCategory();

        Log.i("cat", cat1);
        Log.i("cat", cat2);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!cat1.isEmpty() && !cat2.isEmpty()){
                    Toast.makeText(AddExerciseActivity.this,
                            "Selecionar apenas 1 categoria por vez!", Toast.LENGTH_SHORT).show();
                    rgCat1.clearCheck();
                    rgCat2.clearCheck();
                }
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

}