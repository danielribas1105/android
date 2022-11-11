package com.apps.drpersonalmanager.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.apps.drpersonalmanager.R;

public class MakeTrainingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_training);
        getSupportActionBar().setTitle("Treinos");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}