package com.apps.drpersonal.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.drpersonal.R;
import com.apps.drpersonal.config.ConfigFirebase;
import com.apps.drpersonal.helper.Base64Custom;
import com.apps.drpersonal.helper.DataCustom;
import com.apps.drpersonal.model.Historico;
import com.apps.drpersonal.ui.adapter.HistoricoAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HistoricoActivity extends AppCompatActivity {

    private TextView prevMonth, monthYear, nextMonth;
    private String currentMonth, idAluno;
    private HistoricoAdapter historicoAdapter;
    private List<Historico> historicos = new ArrayList<>();
    private RecyclerView recyclerHistorical;
    private FirebaseAuth auth = ConfigFirebase.getFirebaseAutenticacao();
    private DatabaseReference referenceHist = ConfigFirebase.getFirebaseDatabase();
    private DatabaseReference histAluno;
    private ValueEventListener valueEventListenerHistoric;
    private static String mesAnoHist = DataCustom.dataMesAno(DataCustom.dataAtual());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico);

        prevMonth = findViewById(R.id.textPrevMonth);
        monthYear = findViewById(R.id.textMonthYear);
        nextMonth = findViewById(R.id.textNextMonth);
        monthYear.setText(DataCustom.mesAnoFormat(DataCustom.dataAtual()));
        recyclerHistorical = findViewById(R.id.recyclerHistorico);

        //Configurar adapter
        loadHistorical();
        historicoAdapter = new HistoricoAdapter(historicos,this);
        //Configurar RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerHistorical.setLayoutManager(layoutManager);
        recyclerHistorical.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        recyclerHistorical.setHasFixedSize(true);
        recyclerHistorical.setAdapter(historicoAdapter);

    }

    public void clickPreviousMonth(View view) {
        currentMonth = monthYear.getText().toString();
        monthYear.setText(DataCustom.previousMonth(currentMonth));
        referenceHist.removeEventListener(valueEventListenerHistoric);
        mesAnoHist = DataCustom.previousMonthDataBase(currentMonth);
        loadHistorical();
    }

    public void clickNextMonth(View view) {
        currentMonth = monthYear.getText().toString();
        monthYear.setText(DataCustom.nextMonth(currentMonth));
        referenceHist.removeEventListener(valueEventListenerHistoric);
        mesAnoHist = DataCustom.nextMonthDataBase(currentMonth);
        loadHistorical();
    }

    public void loadHistorical(){
        idAluno = Base64Custom.codeToBase64(auth.getCurrentUser().getEmail());
        histAluno = referenceHist.child("historico").child(idAluno).child(mesAnoHist);
        valueEventListenerHistoric = histAluno.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                historicos.clear();
                for(DataSnapshot historic: snapshot.getChildren()){
                    Historico historico = historic.getValue(Historico.class);
                    historicos.add(historico);
                }
                historicoAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadHistorical();
    }

    @Override
    protected void onStop() {
        super.onStop();
        histAluno.removeEventListener(valueEventListenerHistoric);
    }
}