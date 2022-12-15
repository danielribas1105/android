package com.apps.drpersonalmanager.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.apps.drpersonalmanager.R;

public class AddExerciseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercise);
        setTitle("Adicionar exercícios");
    }
}