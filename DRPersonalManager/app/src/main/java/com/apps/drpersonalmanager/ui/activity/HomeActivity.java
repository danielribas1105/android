package com.apps.drpersonalmanager.ui.activity;

import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.CHAVE_ALUNO_SELECT;
import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.CHAVE_DB_ALUNOS;
import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.CHAVE_DB_PERSONAL;
import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.CHAVE_ST_IMAGES;
import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.CHAVE_ST_PROFILE_ALUNOS;
import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.CHAVE_ST_PROFILE_PERSONAL;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.drpersonalmanager.R;
import com.apps.drpersonalmanager.config.ConfigFirebase;
import com.apps.drpersonalmanager.dao.AlunoDao;
import com.apps.drpersonalmanager.helper.RecyclerItemClickListener;
import com.apps.drpersonalmanager.helper.UsersFirebase;
import com.apps.drpersonalmanager.model.Aluno;
import com.apps.drpersonalmanager.model.Personal;
import com.apps.drpersonalmanager.ui.adapter.AlunosAdapter;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity {

    private TextView campoHello;
    private CircleImageView fotoPerfil;
    private String idPersonal;
    private AlunosAdapter alunosAdapter;
    private RecyclerView recyclerViewAlunos;
    private List<Aluno> alunos = new ArrayList<>();
    private FirebaseAuth auth = ConfigFirebase.getFirebaseAutenticacao();
    private DatabaseReference reference = ConfigFirebase.getFirebaseDatabase();
    private DatabaseReference refDbPersonal;
    private DatabaseReference findAlunos;
    private StorageReference storageReference = ConfigFirebase.getStorage();
    private StorageReference fotoPersonal;
    private ValueEventListener valueEventListenerPersonal;
    private ValueEventListener valueEventListenerAlunos;
    private AlunoDao alunoDao = new AlunoDao();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        campoHello = findViewById(R.id.textHomeOla);
        fotoPerfil = findViewById(R.id.imgFotoPersonal);
        recyclerViewAlunos = findViewById(R.id.recyclerMeusAlunos);

        idPersonal = UsersFirebase.getIdUserAuth();

        //Configurar Adapter
        loadAlunos();
        alunosAdapter = new AlunosAdapter(alunos, this);
        //Configurar RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewAlunos.setLayoutManager(layoutManager);
        recyclerViewAlunos.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        recyclerViewAlunos.setHasFixedSize(true);
        recyclerViewAlunos.setAdapter(alunosAdapter);
        //Configurar evento de click no RecyclerView
        recyclerViewAlunos.addOnItemTouchListener(new RecyclerItemClickListener(
                getApplicationContext(), recyclerViewAlunos,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        //Recuperar aluno selecionado
                        Aluno alunoSelected = alunos.get(position);
                        //Enviar aluno para a próxima tela
                        Intent i = new Intent(HomeActivity.this, ManageAlunoActivity.class);
                        i.putExtra(CHAVE_ALUNO_SELECT, alunoSelected);
                        startActivity(i);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        Aluno alunoSelected = alunos.get(position);
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(HomeActivity.this);
                        alertDialog.setTitle("EXCLUIR ALUNO: " + alunoSelected.getNomeAluno());
                        alertDialog.setMessage("A aluno selecionado será excluído definitivamente. Confirmar?");
                        alertDialog.setCancelable(false);
                        alertDialog.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        alunoDao.excluirAluno(alunoSelected.getEmailAluno());
                                        Toast.makeText(HomeActivity.this,
                                                "Aluno excluído com sucesso!", Toast.LENGTH_SHORT).show();
                                    }
                                }).setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                });
                        alertDialog.create();
                        alertDialog.show();
                    }

                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    }
                }));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btn_menu_meu_perfil:
                startActivity(new Intent(this, MyProfileActivity.class));
                break;
            case R.id.btn_menu_alterar_senha:
                startActivity(new Intent(this, SwapPasswordActivity.class));
                break;
            case R.id.btn_menu_novo_aluno:
                startActivity(new Intent(this, NewAlunoActivity.class));
                break;
            case R.id.btn_menu_novo_exercicio:
                startActivity(new Intent(this, NewExerciseActivity.class));
                break;
            case R.id.btn_menu_sair:
                auth.signOut();
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getProfilePersonal() {
        fotoPersonal = storageReference.child(CHAVE_ST_IMAGES).child(CHAVE_ST_PROFILE_PERSONAL)
                .child(idPersonal + ".jpg");
        fotoPersonal.getDownloadUrl().addOnSuccessListener(HomeActivity.this,
                new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(HomeActivity.this).load(uri).into(fotoPerfil);
            }
        }).addOnFailureListener(HomeActivity.this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("foto", "Erro ao carregar a imagem de perfil");
            }
        });
        refDbPersonal = reference.child(CHAVE_DB_PERSONAL).child(idPersonal);
        valueEventListenerPersonal = refDbPersonal.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Personal personal = snapshot.getValue(Personal.class);
                String hello = "Olá, " + personal.getNomePersonal() + "!";
                if(personal.getNomePersonal() != null){
                    campoHello.setText(hello);
                }else{
                    campoHello.setText("Usuário não encontrado!");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void loadAlunos() {
        findAlunos = reference.child(CHAVE_DB_ALUNOS).child(idPersonal);
        valueEventListenerAlunos = findAlunos.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                alunos.clear();
                for (DataSnapshot dadosAlunos : snapshot.getChildren()) {
                    Aluno aluno = dadosAlunos.getValue(Aluno.class);
                    alunos.add(aluno);
                }
                alunosAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        getProfilePersonal();
    }

    @Override
    protected void onStop() {
        super.onStop();
        refDbPersonal.removeEventListener(valueEventListenerPersonal);
    }

}