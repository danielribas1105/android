package com.apps.drpersonal.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.apps.drpersonal.MainActivity;
import com.apps.drpersonal.R;
import com.apps.drpersonal.config.ConfigFirebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class HomeActivity extends AppCompatActivity {

    private FirebaseAuth auth = ConfigFirebase.getFirebaseAutenticacao();
    private DatabaseReference reference = ConfigFirebase.getFirebaseDatabase();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuSair:
                auth.signOut();
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void goToMeusTreinos(View view){
        startActivity(new Intent(this, TreinosActivity.class));
    }

    public void goToHistorico(View view){
        startActivity(new Intent(this, HistoricoActivity.class));
    }

    public void goToAvaliacao(View view){
        startActivity(new Intent(this, AvaliacoesActivity.class));
    }
}