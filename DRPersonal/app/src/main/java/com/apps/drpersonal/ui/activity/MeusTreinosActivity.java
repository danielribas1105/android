package com.apps.drpersonal.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.apps.drpersonal.R;
import com.apps.drpersonal.config.ConfigFirebase;
import com.apps.drpersonal.helper.Base64Custom;
import com.apps.drpersonal.model.Aluno;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;

public class MeusTreinosActivity extends AppCompatActivity {

    private TextView campoHello;
    private FirebaseAuth auth = ConfigFirebase.getFirebaseAutenticacao();
    private DatabaseReference reference = ConfigFirebase.getFirebaseDatabase();
    private DatabaseReference alunoDB;
    private ValueEventListener valueEventListenerAluno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meus_treinos);

        campoHello = findViewById(R.id.textHello);
        getNomeAluno();
    }

    public void getNomeAluno() {
        String idAluno = Base64Custom.codeToBase64(auth.getCurrentUser().getEmail());
        alunoDB = reference.child("alunos").child(idAluno);
        valueEventListenerAluno = alunoDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Aluno aluno = snapshot.getValue(Aluno.class);
                campoHello.setText("Olá, " + aluno.getNomeAluno() + "!");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }
}