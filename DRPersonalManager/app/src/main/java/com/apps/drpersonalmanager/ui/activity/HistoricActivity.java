package com.apps.drpersonalmanager.ui.activity;

import static com.apps.drpersonalmanager.ui.activity.ConstantesActivities.CHAVE_ALUNO_SELECT;

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
import com.apps.drpersonalmanager.helper.DataCustom;
import com.apps.drpersonalmanager.model.Aluno;
import com.apps.drpersonalmanager.model.Historic;
import com.apps.drpersonalmanager.ui.adapter.HistoricAdapter;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class HistoricActivity extends AppCompatActivity {

    private TextView campoNomeAluno, prevMonth, monthYear, nextMonth;
    private String nomeAluno, currentMonth;
    private Aluno alunoSelect;
    private List<Historic> historicList = new ArrayList<>();
    private HistoricAdapter historicAdapter;
    private RecyclerView recyclerView;
    private static String mesAnoHist = DataCustom.dataMesAno(DataCustom.dataAtual());
    private DatabaseReference reference = ConfigFirebase.getFirebaseDatabase();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historic);
        setTitle("Histórico de Atividades");
        campoNomeAluno = findViewById(R.id.textHistAluno);
        recyclerView = findViewById(R.id.recyclerHistAluno);
        alunoSelect = (Aluno) getIntent().getSerializableExtra(CHAVE_ALUNO_SELECT);
        if (alunoSelect != null) {
            nomeAluno = alunoSelect.getNomeAluno();
        }
        campoNomeAluno.setText(nomeAluno);

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
        //configurar método, mas antes acertar DB
    }

    public void clickPreviousMonth(View view) {
        currentMonth = monthYear.getText().toString();
        monthYear.setText(DataCustom.previousMonth(currentMonth));
        //reference.removeEventListener(valueEventListenerHistoric);
        mesAnoHist = DataCustom.previousMonthDataBase(currentMonth);
        //loadHistorical();
    }

    public void clickNextMonth(View view) {
        currentMonth = monthYear.getText().toString();
        monthYear.setText(DataCustom.nextMonth(currentMonth));
        //reference.removeEventListener(valueEventListenerHistoric);
        mesAnoHist = DataCustom.nextMonthDataBase(currentMonth);
        //loadHistorical();
    }

}