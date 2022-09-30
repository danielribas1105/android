package com.apps.mypocket.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.mypocket.R;
import com.apps.mypocket.adapter.AdapterMovimentacao;
import com.apps.mypocket.config.ConfiguracaoFirebase;
import com.apps.mypocket.helper.Base64Custom;
import com.apps.mypocket.helper.DateCustom;
import com.apps.mypocket.model.Movimentacao;
import com.apps.mypocket.model.Usuario;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class PrincipalActivity extends AppCompatActivity {

    private Animation rotateOpen;
    private Animation rotateClose;
    private Animation fromBottom;
    private Animation toBottom;
    private Boolean clicked = false;

    private FloatingActionButton fabAdd;
    private FloatingActionButton fabReceita;
    private FloatingActionButton fabDespesa;

    private FirebaseAuth auth = ConfiguracaoFirebase.getFirebaseAutenticacao();
    private DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebaseDatabase();
    private DatabaseReference movimentacoesRef = ConfiguracaoFirebase.getFirebaseDatabase();
    private DatabaseReference usuarioRef;
    private ValueEventListener valueEventListenerUsuario;
    private ValueEventListener valueEventListenerMovimentacoes;
    private RecyclerView recyclerView;
    private AdapterMovimentacao adapterMovimentacao;
    private List<Movimentacao> movimentacoes = new ArrayList<>();

    private TextView currentMonth, textGreet, textBalance;
    private String mesAtual;
    private Double saldoTotal;

    private AppBarConfiguration appBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        textGreet = findViewById(R.id.textHello);
        textBalance = findViewById(R.id.textSaldo);
        currentMonth = findViewById(R.id.textCurrentMonth);
        currentMonth.setText(DateCustom.mesAnoFormat(DateCustom.dataAtual()));
        recyclerView = findViewById(R.id.recyclerMove);

        rotateOpen = AnimationUtils.loadAnimation(this, R.anim.rotate_open_anim);
        rotateClose = AnimationUtils.loadAnimation(this, R.anim.rotate_close_anim);
        fromBottom = AnimationUtils.loadAnimation(this, R.anim.from_bottom_anim);
        toBottom = AnimationUtils.loadAnimation(this, R.anim.to_bottom_anim);

        fabAdd = findViewById(R.id.fabAdd);
        fabReceita = findViewById(R.id.fabReceita);
        fabDespesa = findViewById(R.id.fabDespesa);

        //Configurar Adapter
        adapterMovimentacao = new AdapterMovimentacao(movimentacoes, this);
        //Configurar RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapterMovimentacao);


        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBtnAddClick();
            }
        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
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

    public void getMovimentacoes(){
        String idUser = Base64Custom.codeToBase64(auth.getCurrentUser().getEmail());
        String mesAno = DateCustom.dataMesAno(DateCustom.dataAtual());
        movimentacoesRef.child("movimentacao").child(idUser).child(mesAno);
        //Log.i("Data","mesAtual: " + mesAno);

        valueEventListenerMovimentacoes = movimentacoesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                movimentacoes.clear();
                for(DataSnapshot dados: snapshot.getChildren()){
                    Movimentacao movimentacao = dados.getValue(Movimentacao.class);
                    Log.i("Dados", "Retorno" + movimentacao.getCategoria());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void getSaldoTotal() {
        String idUser = Base64Custom.codeToBase64(auth.getCurrentUser().getEmail());
        usuarioRef = firebaseRef.child("usuarios").child(idUser);
        valueEventListenerUsuario = usuarioRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Usuario usuario = snapshot.getValue(Usuario.class);
                saldoTotal = usuario.getReceitaTotal() - usuario.getDespesaTotal();
                DecimalFormat decimalFormat = new DecimalFormat("0.00");
                String saldoFormat = decimalFormat.format(saldoTotal);
                textGreet.setText("Olá, " + usuario.getNome());
                textBalance.setText("R$ " + saldoFormat);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void onBtnAddClick() {
        setVisibility(clicked);
        setAnimation(clicked);
        setClickable(clicked);
        clicked = !clicked;
    }

    private void setVisibility(Boolean clicked) {
        if (!clicked) {
            fabReceita.setVisibility(View.VISIBLE);
            fabDespesa.setVisibility(View.VISIBLE);
        } else {
            fabReceita.setVisibility(View.INVISIBLE);
            fabDespesa.setVisibility(View.INVISIBLE);
        }
    }

    private void setAnimation(Boolean clicked) {
        if (!clicked) {
            fabReceita.startAnimation(fromBottom);
            fabDespesa.startAnimation(fromBottom);
            fabAdd.startAnimation(rotateOpen);

        } else {
            fabReceita.startAnimation(toBottom);
            fabDespesa.startAnimation(toBottom);
            fabAdd.startAnimation(rotateClose);
        }
    }

    private void setClickable(Boolean clicked) {
        if (!clicked) {
            fabReceita.setClickable(true);
            fabDespesa.setClickable(true);
        } else {
            fabReceita.setClickable(false);
            fabDespesa.setClickable(false);
        }
    }

    public void addReceita(View view) {
        startActivity(new Intent(this, ReceitaActivity.class));
        Toast.makeText(PrincipalActivity.this, "Adicionar Receita",
                Toast.LENGTH_SHORT).show();
    }

    public void addDespesa(View view) {
        startActivity(new Intent(this, DespesasActivity.class));
        Toast.makeText(PrincipalActivity.this, "Adicionar Despesa",
                Toast.LENGTH_SHORT).show();
    }

    public void clickPreviousMonth(View view) {
        mesAtual = currentMonth.getText().toString();
        currentMonth.setText(DateCustom.previousMonth(mesAtual));
    }

    public void clickNextMonth(View view) {
        mesAtual = currentMonth.getText().toString();
        currentMonth.setText(DateCustom.nextMonth(mesAtual));
    }

    @Override
    protected void onStart() {
        super.onStart();
        getSaldoTotal();
        getMovimentacoes();
    }

    @Override
    protected void onStop() {
        super.onStop();
        usuarioRef.removeEventListener(valueEventListenerUsuario);
        movimentacoesRef.removeEventListener(valueEventListenerMovimentacoes);
    }

}