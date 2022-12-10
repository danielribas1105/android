package com.apps.drpersonalmanager.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.apps.drpersonalmanager.R;
import com.apps.drpersonalmanager.config.ConfigFirebase;
import com.apps.drpersonalmanager.helper.UsersFirebase;
import com.apps.drpersonalmanager.model.Personal;
import com.google.firebase.auth.FirebaseAuth;

public class SwapPasswordActivity extends AppCompatActivity {

    private EditText campoNovaSenha, campoConfNovaSenha;
    private Button btnSwapPass;
    private FirebaseAuth auth = ConfigFirebase.getFirebaseAutenticacao();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swap_password);
        setTitle("Alterar Senha");
        campoNovaSenha = findViewById(R.id.editTextNovaSenha);
        campoConfNovaSenha = findViewById(R.id.editTextConfNovaSenha);
        btnSwapPass = findViewById(R.id.btnAlterarSenha);

        btnSwapPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newPassword = campoNovaSenha.getText().toString();
                String confNewPassword = campoConfNovaSenha.getText().toString();
                if (!newPassword.isEmpty() && !confNewPassword.isEmpty()) {
                    if (!(newPassword.length() < 6) && !(confNewPassword.length() < 6)) {
                        if (confNewPassword.equals(newPassword)) {
                            salvarNewPassword(confNewPassword);
                            auth.signOut();
                            goToStart();
                        } else {
                            Toast.makeText(SwapPasswordActivity.this,
                                    "Os campos Nova Senha e Confirmar Nova Senha não são iguais!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(SwapPasswordActivity.this,
                                "A nova senha deve conter pelo menos 6 caracteres!",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(SwapPasswordActivity.this,
                            "Os campos não podem estar vazios!", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void goToStart() {
        startActivity(new Intent(SwapPasswordActivity.this, MainActivity.class));
        finish();
    }

    private void salvarNewPassword(String pass) {
        Personal personal = new Personal();
        personal.atualizarSenhaPersonal(pass);
        if (UsersFirebase.changePassWord(pass)) {
            Toast.makeText(SwapPasswordActivity.this, "Senha atualizada com sucesso!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Erro ao atualizar senha!", Toast.LENGTH_SHORT).show();
        }
    }
}