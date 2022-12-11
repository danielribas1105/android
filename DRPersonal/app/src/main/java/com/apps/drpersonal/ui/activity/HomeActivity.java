package com.apps.drpersonal.ui.activity;

import static com.apps.drpersonal.ui.activity.ConstantesActivities.CHAVE_DB_ALUNOS;
import static com.apps.drpersonal.ui.activity.ConstantesActivities.CHAVE_DB_IDPERSONAL;
import static com.apps.drpersonal.ui.activity.ConstantesActivities.CHAVE_ST_IMAGES;
import static com.apps.drpersonal.ui.activity.ConstantesActivities.CHAVE_ST_PROFILE_ALUNOS;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.apps.drpersonal.R;
import com.apps.drpersonal.config.ConfigFirebase;
import com.apps.drpersonal.helper.Base64Custom;
import com.apps.drpersonal.helper.UsersFirebase;
import com.apps.drpersonal.model.Aluno;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity {

    private TextView campoHello;
    private CircleImageView campoHomeFotoAluno;
    private String idAluno;
    private FirebaseAuth auth = ConfigFirebase.getFirebaseAutenticacao();
    private DatabaseReference reference = ConfigFirebase.getFirebaseDatabase();
    private DatabaseReference alunoDB;
    private StorageReference storageReference = ConfigFirebase.getStorageReference();
    private StorageReference fotoAluno;
    private ValueEventListener valueEventListenerAluno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        campoHomeFotoAluno = findViewById(R.id.imgFotoAluno);
        campoHello = findViewById(R.id.textHello);
        getProfileAluno();
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

    public void getProfileAluno() {
        idAluno = UsersFirebase.getIdUserAuth();
        fotoAluno = storageReference.child(CHAVE_ST_IMAGES).child(CHAVE_ST_PROFILE_ALUNOS)
                .child(idAluno + ".jpg");
        fotoAluno.getDownloadUrl().addOnSuccessListener(HomeActivity.this,
                new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(HomeActivity.this).load(uri).into(campoHomeFotoAluno);
            }
        }).addOnFailureListener(HomeActivity.this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("foto", "Erro ao carregar a imagem de perfil");
            }
        });
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
        getProfileAluno();
    }

    @Override
    protected void onStop() {
        super.onStop();
        alunoDB.removeEventListener(valueEventListenerAluno);
    }
}