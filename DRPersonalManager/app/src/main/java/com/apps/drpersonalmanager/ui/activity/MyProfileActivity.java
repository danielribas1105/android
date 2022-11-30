package com.apps.drpersonalmanager.ui.activity;

import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.CHAVE_DB_IDPERSONAL;
import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.CHAVE_DB_PERSONAL;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.apps.drpersonalmanager.R;
import com.apps.drpersonalmanager.config.ConfigFirebase;
import com.apps.drpersonalmanager.helper.Base64Custom;
import com.apps.drpersonalmanager.helper.Consent;
import com.apps.drpersonalmanager.model.Personal;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class MyProfileActivity extends AppCompatActivity {

    private EditText editNome, editEmail;
    private Button btnAtualizaDados;
    private DatabaseReference reference = ConfigFirebase.getFirebaseDatabase();
    private DatabaseReference refPersonal;
    private ValueEventListener valueEventListenerPersonal;

    private String[] consent = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        setTitle("Editar Perfil");

        //Validar permissões
        Consent.validateConsent(consent, this, 1);

        editNome = findViewById(R.id.editTextNome);
        editEmail = findViewById(R.id.editTextEmail);
        btnAtualizaDados = findViewById(R.id.btnAtualizarDados);

        loadPersonalProfile();

        btnAtualizaDados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nomePersonal = editNome.getText().toString();
                String emailPersonal = editEmail.getText().toString();
                if(!nomePersonal.isEmpty()){
                    if(!emailPersonal.isEmpty()){
                        salvarPersonalInfo(nomePersonal, emailPersonal);
                        Toast.makeText(MyProfileActivity.this, "Perfil atualizado com sucesso!", Toast.LENGTH_SHORT).show();
                        finish();
                    }else {
                        Toast.makeText(MyProfileActivity.this, "Campo e-mail não pode ser vazio!", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(MyProfileActivity.this, "Campo nome não pode estar vazio!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void salvarPersonalInfo(String nomePersonal, String emailPersonal) {
        Personal personal = new Personal();
        personal.salvarPerfilPersonal(nomePersonal,emailPersonal);
    }

    private void loadPersonalProfile() {
        refPersonal = reference.child(CHAVE_DB_PERSONAL).child(CHAVE_DB_IDPERSONAL);
        valueEventListenerPersonal = refPersonal.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Personal personal = snapshot.getValue(Personal.class);
                editNome.setText(personal.getNomePersonal());
                editEmail.setText(personal.getEmailPersonal());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}