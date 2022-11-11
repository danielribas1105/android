package com.apps.drpersonalmanager.ui.activity;

import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.CHAVE_ALUNO_SELECT;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.apps.drpersonalmanager.R;
import com.apps.drpersonalmanager.config.ConfigFirebase;
import com.apps.drpersonalmanager.helper.Base64Custom;
import com.apps.drpersonalmanager.model.Aluno;
import com.apps.drpersonalmanager.model.Training;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import javax.security.auth.login.LoginException;

public class MakeTrainingActivity extends AppCompatActivity {

    private EditText nomeSerie, descSerie;
    private Button btnCriar, btnSalvar;
    private String idUser, emailAluno;
    private Aluno alunoSelect;
    private FirebaseAuth auth = ConfigFirebase.getFirebaseAutenticacao();
    private DatabaseReference reference = ConfigFirebase.getFirebaseDatabase();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_training);
        getSupportActionBar().setTitle("Treinos");
        nomeSerie = findViewById(R.id.editTextNomeSerie);
        descSerie = findViewById(R.id.editTextDescSerie);
        btnCriar = findViewById(R.id.btnCriarSerie);
        btnSalvar = findViewById(R.id.btnSalvarTreino);

        alunoSelect = (Aluno) getIntent().getSerializableExtra(CHAVE_ALUNO_SELECT);
        if (alunoSelect != null) {
            emailAluno = alunoSelect.getEmailAluno();
        }

        btnCriar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Salvar as séries no Firebase
                String serie = nomeSerie.getText().toString();
                String descricao = descSerie.getText().toString();
                if(!serie.isEmpty()){
                    if(!descricao.isEmpty()){
                        salvarTreino();
                    }else {
                        Toast.makeText(MakeTrainingActivity.this, "Preencha a descrição!", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(MakeTrainingActivity.this, "Preencha o nome da série!", Toast.LENGTH_SHORT).show();
                }



                Toast.makeText(MakeTrainingActivity.this, "Série criada!", Toast.LENGTH_SHORT).show();
            }
        });
        
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Finalizar a criação do treino
                Toast.makeText(MakeTrainingActivity.this, "Treino salvo!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void salvarTreino() {
        idUser = Base64Custom.codeToBase64(auth.getCurrentUser().getEmail());
    }
}