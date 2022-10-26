package com.apps.whatsup.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.apps.whatsup.R;
import com.apps.whatsup.config.ConfigFirebase;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        auth = ConfigFirebase.getFirebaseAutenticacao();

        Toolbar toolbar = findViewById(R.id.toolbarHome);
        toolbar.setTitle("WhatsUp");
        setSupportActionBar(toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuSair:
                deslogarUser();
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void deslogarUser(){
        try {
            auth.signOut();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}