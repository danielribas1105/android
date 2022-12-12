package com.apps.drpersonalmanager.ui.activity;

import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.CHAVE_ALUNO_SELECT;
import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.CHAVE_DB_HISTORICO;
import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.CHAVE_DB_IDPERSONAL;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.apps.drpersonalmanager.R;
import com.apps.drpersonalmanager.config.ConfigFirebase;
import com.apps.drpersonalmanager.helper.Base64Custom;
import com.apps.drpersonalmanager.helper.DataCustom;
import com.apps.drpersonalmanager.model.Aluno;
import com.apps.drpersonalmanager.model.Historic;
import com.apps.drpersonalmanager.ui.adapter.HistoricAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HistoricActivity extends AppCompatActivity {

    private TextView campoNomeAluno, prevMonth, monthYear, nextMonth;
    private String nomeAluno, emailAluno, currentMonth;
    private Aluno alunoSelect;
    private List<Historic> historicList = new ArrayList<>();
    private HistoricAdapter historicAdapter;
    private RecyclerView recyclerView;
    private static String mesAnoHist = DataCustom.dataMesAno(DataCustom.dataAtual());
    private DatabaseReference reference = ConfigFirebase.getFirebaseDatabase();
    private DatabaseReference referenceHist;
    private ValueEventListener valueEventListenerHistoric;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historic);
        setTitle("Histórico de Atividades");
        campoNomeAluno = findViewById(R.id.textHistAluno);
        prevMonth = findViewById(R.id.textPrevMonth);
        monthYear = findViewById(R.id.textMonthYear);
        nextMonth = findViewById(R.id.textNextMonth);
        recyclerView = findViewById(R.id.recyclerHistAluno);
        alunoSelect = (Aluno) getIntent().getSerializableExtra(CHAVE_ALUNO_SELECT);
        if (alunoSelect != null) {
            nomeAluno = alunoSelect.getNomeAluno();
            emailAluno = alunoSelect.getEmailAluno();
        }
        campoNomeAluno.setText(nomeAluno);
        monthYear.setText(DataCustom.mesAnoFormat(DataCustom.dataAtual()));

        //Configurar Adapter
        loadHistorical();
        historicAdapter = new HistoricAdapter(historicList, this);
        //Configurar RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(historicAdapter);

    }

    private void loadHistorical() {
        referenceHist = reference.child(CHAVE_DB_HISTORICO).child(CHAVE_DB_IDPERSONAL)
                .child(Base64Custom.codeToBase64(emailAluno)).child(mesAnoHist);
        valueEventListenerHistoric = referenceHist.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                historicList.clear();
                for (DataSnapshot dataSnapshotHist : snapshot.getChildren()) {
                    Historic historic = dataSnapshotHist.getValue(Historic.class);
                    historicList.add(historic);
                }
                historicAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void clickPreviousMonth(View view) {
        currentMonth = monthYear.getText().toString();
        monthYear.setText(DataCustom.previousMonth(currentMonth));
        reference.removeEventListener(valueEventListenerHistoric);
        mesAnoHist = DataCustom.previousMonthDataBase(currentMonth);
        loadHistorical();
    }

    public void clickNextMonth(View view) {
        currentMonth = monthYear.getText().toString();
        monthYear.setText(DataCustom.nextMonth(currentMonth));
        reference.removeEventListener(valueEventListenerHistoric);
        mesAnoHist = DataCustom.nextMonthDataBase(currentMonth);
        loadHistorical();
    }

}