package com.apps.drpersonal.ui.activity;

import static com.apps.drpersonal.ui.activity.ConstantesActivities.CHAVE_DB_ALUNOS;
import static com.apps.drpersonal.ui.activity.ConstantesActivities.CHAVE_DB_IDPERSONAL;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.apps.drpersonal.R;
import com.apps.drpersonal.config.ConfigFirebase;
import com.apps.drpersonal.helper.Base64Custom;
import com.apps.drpersonal.model.Aluno;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity {

    private TextView campoHello;
    private CircleImageView campoHomeFotoAluno;
    private String idAluno;
    private FirebaseAuth auth = ConfigFirebase.getFirebaseAutenticacao();
    private DatabaseReference reference = ConfigFirebase.getFirebaseDatabase();
    private DatabaseReference alunoDB;
    private ValueEventListener valueEventListenerAluno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        campoHomeFotoAluno = findViewById(R.id.imgFotoAluno);
        campoHello = findViewById(R.id.textHello);
        getNomeAluno();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuEditar:
                startActivity(new Intent(this, EditarPerfilActivity.class));
                break;
            case R.id.menuAlterarSenha:
                startActivity(new Intent(this, SwapPasswordActivity.class));
                break;
            case R.id.menuSair:
                auth.signOut();
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void getNomeAluno() {
        idAluno = Base64Custom.codeToBase64(auth.getCurrentUser().getEmail());
        alunoDB = reference.child(CHAVE_DB_ALUNOS).child(CHAVE_DB_IDPERSONAL).child(idAluno);
        valueEventListenerAluno = alunoDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Aluno aluno = snapshot.getValue(Aluno.class);
                campoHello.setText("Olá, " + aluno.getNomeAluno() + "!");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
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

    @Override
    protected void onStart() {
        super.onStart();
        getNomeAluno();
    }

    @Override
    protected void onStop() {
        super.onStop();
        alunoDB.removeEventListener(valueEventListenerAluno);
    }
}