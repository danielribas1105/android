package com.apps.drpersonalmanager.ui.activity;

import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.CHAVE_DB_IDPERSONAL;
import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.CHAVE_DB_PERSONAL;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.apps.drpersonalmanager.R;
import com.apps.drpersonalmanager.config.ConfigFirebase;
import com.apps.drpersonalmanager.model.Personal;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class SwapPasswordActivity extends AppCompatActivity {

    private EditText campoSenhaAtual, campoNovaSenha;
    private Button btnSwapPass;
    private FirebaseAuth auth = ConfigFirebase.getFirebaseAutenticacao();
    private DatabaseReference reference = ConfigFirebase.getFirebaseDatabase();
    private DatabaseReference refPassPersonal;
    private ValueEventListener valueEventListenerPersonal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swap_password);
        setTitle("Alterar Senha");
        campoSenhaAtual = findViewById(R.id.editTextSenhaAtual);
        campoNovaSenha = findViewById(R.id.editTextNovaSenha);
        btnSwapPass = findViewById(R.id.btnAlterarSenha);

        loadPasswordActual();

        btnSwapPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newPassword = campoNovaSenha.getText().toString();
                if(!newPassword.isEmpty()){
                    if(!(newPassword.length() < 6)){
                        salvarNewPassword(newPassword);
                        Toast.makeText(SwapPasswordActivity.this, "Senha atualizada com sucesso!", Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        Toast.makeText(SwapPasswordActivity.this, "A nova senha deve conter pelo menos 6 caracteres!", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(SwapPasswordActivity.this, "O campo nova senha não pode estar vazio!", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void salvarNewPassword(String newPassword) {
        Personal personal = new Personal();
        personal.atualizarSenhaPersonal(newPassword);
    }

    private void loadPasswordActual() {
        if(auth.getCurrentUser() != null){
            refPassPersonal = reference.child(CHAVE_DB_PERSONAL).child(CHAVE_DB_IDPERSONAL);
            valueEventListenerPersonal = refPassPersonal.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Personal passPersonal = snapshot.getValue(Personal.class);
                    campoSenhaAtual.setText(passPersonal.getSenhaPersonal());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

    }
}