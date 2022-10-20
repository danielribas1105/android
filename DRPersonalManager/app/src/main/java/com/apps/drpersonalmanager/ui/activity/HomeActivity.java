package com.apps.drpersonalmanager.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.apps.drpersonalmanager.R;
import com.apps.drpersonalmanager.config.ConfigFirebase;
import com.apps.drpersonalmanager.helper.Base64Custom;
import com.apps.drpersonalmanager.model.Personal;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private TextView campoHello;
    private FirebaseAuth auth;
    private DatabaseReference reference, refDbPersonal;
    private String idPersonal;
    private ValueEventListener valueEventListenerPersonal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        campoHello = findViewById(R.id.textHomeOla);

    }

    private void getPersonalLog() {
        auth = ConfigFirebase.getFirebaseAutenticacao();
        idPersonal = Base64Custom.codeToBase64(auth.getCurrentUser().getEmail());
        reference = ConfigFirebase.getFirebaseDatabase();
        refDbPersonal = reference.child("personal").child(idPersonal);
        valueEventListenerPersonal = refDbPersonal.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Personal personal = snapshot.getValue(Personal.class);
                campoHello.setText("Olá, " + personal.getNomePersonal() + "!");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void goToAlunos(View view){
        startActivity(new Intent(this, AlunosActivity.class));
    }

    @Override
    protected void onStart() {
        super.onStart();
        getPersonalLog();
    }

    @Override
    protected void onStop() {
        super.onStop();
        refDbPersonal.removeEventListener(valueEventListenerPersonal);
    }
}