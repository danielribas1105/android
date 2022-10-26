package com.apps.whatsup.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.Manifest;
import android.os.Bundle;

import com.apps.whatsup.R;
import com.apps.whatsup.helper.Consent;

public class EditarPerfilActivity extends AppCompatActivity {

    private String[] consent = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);

        //Validar permissões
        Consent.validateConsent(consent, this);

        Toolbar toolbar = findViewById(R.id.toolbarHome);
        toolbar.setTitle("Editar");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
}